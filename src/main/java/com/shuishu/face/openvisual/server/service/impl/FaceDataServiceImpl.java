package com.shuishu.face.openvisual.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shuishu.face.openvisual.core.domain.ExtParam;
import com.shuishu.face.openvisual.core.domain.FaceImage;
import com.shuishu.face.openvisual.core.domain.FaceInfo;
import com.shuishu.face.openvisual.core.domain.ImageMat;
import com.shuishu.face.openvisual.core.extract.FaceFeatureExtractor;
import com.shuishu.face.openvisual.engine.service.SearchEngineService;
import com.shuishu.face.openvisual.server.entity.extend.SearchAlgorithm;
import com.shuishu.face.openvisual.server.entity.dto.FaceDataAddDto;
import com.shuishu.face.openvisual.server.entity.dto.FaceDataDeleteDto;
import com.shuishu.face.openvisual.server.entity.extend.FieldKeyValue;
import com.shuishu.face.openvisual.server.entity.extend.FieldKeyValues;
import com.shuishu.face.openvisual.server.entity.po.VisualCollection;
import com.shuishu.face.openvisual.server.entity.storage.StorageDataInfo;
import com.shuishu.face.openvisual.server.entity.storage.StorageImageInfo;
import com.shuishu.face.openvisual.server.entity.storage.StorageInfo;
import com.shuishu.face.openvisual.server.entity.vo.FaceDataVo;
import com.shuishu.face.openvisual.server.mapper.FaceDataMapper;
import com.shuishu.face.openvisual.server.mapper.SampleDataMapper;
import com.shuishu.face.openvisual.server.mapper.VisualCollectionMapper;
import com.shuishu.face.openvisual.server.model.ColumnValue;
import com.shuishu.face.openvisual.server.model.FaceData;
import com.shuishu.face.openvisual.server.model.ImageData;
import com.shuishu.face.openvisual.server.service.FaceDataService;
import com.shuishu.face.openvisual.server.service.ImageDataService;
import com.shuishu.face.openvisual.server.service.StorageImageServiceDataBaseService;
import com.shuishu.face.openvisual.server.service.base.BaseService;
import com.shuishu.face.openvisual.utils.CollectionUtil;
import com.shuishu.face.openvisual.utils.JsonUtil;
import com.shuishu.face.openvisual.utils.TableUtils;
import com.shuishu.face.openvisual.utils.VTableCache;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class FaceDataServiceImpl extends BaseService implements FaceDataService {
    @Value("${face.face-mask.face-search:false}")
    private boolean faceMask;
    private final SearchEngineService searchEngineService;
    private final VisualCollectionMapper visualCollectionMapper;
    private final FaceDataMapper faceDataMapper;
    private final SampleDataMapper sampleDataMapper;
    private final ImageDataService imageDataService;
    private final FaceFeatureExtractor faceFeatureExtractor;



    @Override
    public FaceDataVo addFaceData(FaceDataAddDto faceDataAddDto) {
        //人脸ID
        String faceId = this.uuid();
        //查看集合是否存在
        LambdaQueryWrapper<VisualCollection> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(VisualCollection::getNamespace, faceDataAddDto.getNamespace())
                .eq(VisualCollection::getCollectionName, faceDataAddDto.getCollectionName());
        VisualCollection visualCollection = visualCollectionMapper.selectOne(lambdaQueryWrapper);
        if (visualCollection == null) {
            throw new RuntimeException("集合不存在");
        }
        if (visualCollection.getStatue() != 0) {
            throw new RuntimeException("集合没有被使用");
        }
        //查看样本是否存在
        long count = sampleDataMapper.count(visualCollection.getSampleTable(), faceDataAddDto.getSampleId());
        if (count <= 0) {
            throw new RuntimeException("样本不存在");
        }
        //获取特征向量
        ExtParam extParam = ExtParam.build().setMask(faceMask).setScoreTh(faceDataAddDto.getFaceScoreThreshold() / 100).setIouTh(0);
        ImageMat imageMat = null;
        FaceImage faceImage = null;
        try {
            imageMat = ImageMat.fromBase64(faceDataAddDto.getImageBase64());
            faceImage = faceFeatureExtractor.extract(imageMat, extParam, new HashMap<>(10));
        } finally {
            if (null != imageMat) {
                imageMat.release();
            }
        }
        if (null == faceImage) {
            throw new RuntimeException("人脸特征提取失败");
        }
        if (faceImage.faceInfos().size() <= 0) {
            throw new RuntimeException("图片不存在人脸");
        }
        // 获取分数做好的人脸
        FaceInfo faceInfo = faceImage.faceInfos.get(0);
        float[] embeds = faceInfo.embedding.embeds;
        //当前样本的人脸相似度的最小阈值
        if (null != faceDataAddDto.getMinConfidenceThresholdWithThisSample() && faceDataAddDto.getMinConfidenceThresholdWithThisSample() > 0) {
            float minScore = this.searchEngineService.searchMinScoreBySampleId(visualCollection.getVectorTable(), faceDataAddDto.getSampleId(), embeds, SearchAlgorithm.COSINESIMIL.algorithm());
            float confidence = (float) Math.floor(minScore * 10000) / 100;
            if (confidence < faceDataAddDto.getMinConfidenceThresholdWithThisSample()) {
                throw new RuntimeException("this face confidence is less than minConfidenceThresholdWithThisSample,confidence=" + confidence + ",threshold=" + faceDataAddDto.getMinConfidenceThresholdWithThisSample());
            }
        }
        //当前样本与其他样本的人脸相似度的最大阈值
        if (null != faceDataAddDto.getMaxConfidenceThresholdWithOtherSample() && faceDataAddDto.getMaxConfidenceThresholdWithOtherSample() > 0) {
            float minScore = this.searchEngineService.searchMaxScoreBySampleId(visualCollection.getVectorTable(), faceDataAddDto.getSampleId(), embeds, SearchAlgorithm.COSINESIMIL.algorithm());
            float confidence = (float) Math.floor(minScore * 10000) / 100;
            if (confidence > faceDataAddDto.getMaxConfidenceThresholdWithOtherSample()) {
                throw new RuntimeException("this face confidence is gather than maxConfidenceThresholdWithOtherSample,confidence=" + confidence + ",threshold=" + faceDataAddDto.getMaxConfidenceThresholdWithOtherSample());
            }
        }
        //保存图片信息并获取图片存储
        StorageInfo storageInfo = CollectionUtil.getStorageInfo(visualCollection.getSchemaInfo());
        if (storageInfo.isStorageFaceInfo()) {
            //将数据保存到指定的数据引擎中
            StorageImageInfo storageImageInfo = new StorageImageInfo(storageInfo.getStorageEngine(), faceDataAddDto.getImageBase64(), faceInfo.embedding.image, JsonUtil.toString(faceInfo));
            StorageDataInfo storageDataInfo = StorageImageServiceDataBaseService.Factory.create(storageImageInfo);
            //插入数据记录
            ImageData imageData = new ImageData();
            imageData.setSampleId(faceDataAddDto.getSampleId());
            imageData.setFaceId(faceId);
            imageData.setStorageType(storageInfo.getStorageEngine().name());
            imageData.setImageRawInfo(storageDataInfo.getImageRawInfo());
            imageData.setImageEbdInfo(storageDataInfo.getImageEbdInfo());
            imageData.setImageFaceInfo(storageDataInfo.getImageFaceInfo());
            imageData.setCreateTime(new Date());
            imageData.setModifyTime(new Date());
            boolean flag = imageDataService.insert(visualCollection.getImageTable(), imageData);
            if (!flag) {
                throw new RuntimeException("保存人脸图片数据失败");
            }
        }
        //获取数据类型
        Map<String, String> filedTypeMap = TableUtils.getFaceFiledTypeMap(visualCollection.getSchemaInfo());
        //插入数据
        FaceData facePo = new FaceData();
        facePo.setSampleId(faceDataAddDto.getSampleId());
        facePo.setFaceId(faceId);
        facePo.setFaceScore(faceInfo.score * 100);
        facePo.setFaceVector(JsonUtil.toString(embeds));
        FieldKeyValues values = faceDataAddDto.getFaceData();
        List<ColumnValue> columnValues = new ArrayList<>();
        if (null != values && !values.isEmpty()) {
            for (FieldKeyValue value : values) {
                if (filedTypeMap.containsKey(value.getKey())) {
                    columnValues.add(new ColumnValue(value.getKey(), value.getValue(), filedTypeMap.get(value.getKey())));
                }
            }
        }
        facePo.setColumnValues(columnValues);
        facePo.setCreateTime(new Date());
        facePo.setUpdateTime(new Date());
        int flag = faceDataMapper.create(visualCollection.getFaceTable(), facePo, facePo.getColumnValues());
        if (flag <= 0) {
            throw new RuntimeException("create face error");
        }
        //写入数据到人脸向量库
        boolean flag1 = searchEngineService.insertVector(visualCollection.getVectorTable(), faceDataAddDto.getSampleId(), faceId, embeds);
        if (!flag1) {
            throw new RuntimeException("create face vector error");
        }
        //将队列写入到队列中
        VTableCache.put(visualCollection.getVectorTable());
        //构造返回
        FaceDataVo faceDataVo = FaceDataVo.build(faceDataAddDto.getNamespace(), faceDataAddDto.getCollectionName(), faceDataAddDto.getSampleId(), faceId);
        faceDataVo.setFaceScore(faceInfo.score * 100);
        faceDataVo.setFaceData(faceDataAddDto.getFaceData());
        return faceDataVo;
    }

    @Override
    public Boolean deleteFaceData(FaceDataDeleteDto faceDataDeleteDto) {
        //查看集合是否存在
        LambdaQueryWrapper<VisualCollection> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(VisualCollection::getNamespace, faceDataDeleteDto.getNamespace())
                .eq(VisualCollection::getCollectionName, faceDataDeleteDto.getCollectionName());
        VisualCollection visualCollection = visualCollectionMapper.selectOne(lambdaQueryWrapper);
        if (visualCollection == null) {
            throw new RuntimeException("集合不存在");
        }
        if (visualCollection.getStatue() != 0) {
            throw new RuntimeException("集合没有被使用");
        }
        //查看人脸是否存在
        Long keyId = faceDataMapper.getIdByFaceId(visualCollection.getFaceTable(), faceDataDeleteDto.getSampleId(), faceDataDeleteDto.getFaceId());
        if (null == keyId || keyId <= 0) {
            throw new RuntimeException("人脸数据不存在");
        }
        //删除向量
        boolean delete = searchEngineService.deleteVectorByKey(visualCollection.getVectorTable(), faceDataDeleteDto.getFaceId());
        if (!delete) {
            throw new RuntimeException("删除人脸向量失败");
        }
        //将队列写入到队列中
        VTableCache.put(visualCollection.getVectorTable());
        //删除数据
        int flag = faceDataMapper.deleteByFaceId(visualCollection.getFaceTable(), faceDataDeleteDto.getSampleId(), faceDataDeleteDto.getFaceId());
        return flag > 0;
    }

}
