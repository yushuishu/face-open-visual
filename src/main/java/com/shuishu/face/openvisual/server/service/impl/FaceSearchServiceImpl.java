package com.shuishu.face.openvisual.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shuishu.face.openvisual.core.domain.ExtParam;
import com.shuishu.face.openvisual.core.domain.FaceImage;
import com.shuishu.face.openvisual.core.domain.FaceInfo;
import com.shuishu.face.openvisual.core.domain.ImageMat;
import com.shuishu.face.openvisual.core.extract.FaceFeatureExtractor;
import com.shuishu.face.openvisual.engine.conf.Constant;
import com.shuishu.face.openvisual.engine.model.SearchDocument;
import com.shuishu.face.openvisual.engine.model.SearchResult;
import com.shuishu.face.openvisual.engine.service.SearchEngineService;
import com.shuishu.face.openvisual.server.entity.extend.SearchAlgorithm;
import com.shuishu.face.openvisual.server.entity.dto.FaceSearchDto;
import com.shuishu.face.openvisual.server.entity.extend.FaceLocation;
import com.shuishu.face.openvisual.server.entity.vo.SampleFaceVo;
import com.shuishu.face.openvisual.server.entity.po.VisualCollection;
import com.shuishu.face.openvisual.server.entity.vo.FaceSearchVo;
import com.shuishu.face.openvisual.server.mapper.FaceDataMapper;
import com.shuishu.face.openvisual.server.mapper.SampleDataMapper;
import com.shuishu.face.openvisual.server.mapper.VisualCollectionMapper;
import com.shuishu.face.openvisual.server.service.FaceSearchService;
import com.shuishu.face.openvisual.server.service.base.BaseService;
import com.shuishu.face.openvisual.utils.Similarity;
import com.shuishu.face.openvisual.utils.ValueUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-28 19:54
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Service
@RequiredArgsConstructor
public class FaceSearchServiceImpl extends BaseService implements FaceSearchService {
    @Value("${face.face-mask.face-search:false}")
    private boolean faceMask;
    private final SearchEngineService searchEngineService;
    private final VisualCollectionMapper visualCollectionMapper;
    private final FaceDataMapper faceDataMapper;
    private final SampleDataMapper sampleDataMapper;
    private final FaceFeatureExtractor faceFeatureExtractor;

    @Override
    public List<FaceSearchVo> findFaceRecognition(FaceSearchDto faceSearchDto) {
        //查看集合是否存在
        LambdaQueryWrapper<VisualCollection> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(VisualCollection::getNamespace, faceSearchDto.getNamespace())
                .eq(VisualCollection::getCollectionName, faceSearchDto.getCollectionName());
        VisualCollection visualCollection = visualCollectionMapper.selectOne(lambdaQueryWrapper);
        if (visualCollection == null) {
            throw new RuntimeException("集合不存在");
        }
        if (visualCollection.getStatue() != 0) {
            throw new RuntimeException("集合没有被使用");
        }
        //获取特征向量
        int maxFaceNum = (null == faceSearchDto.getMaxFaceNum() || faceSearchDto.getMaxFaceNum() <= 0) ? 5 : faceSearchDto.getMaxFaceNum();
        ExtParam extParam = ExtParam.build().setMask(faceMask).setScoreTh(faceSearchDto.getFaceScoreThreshold() / 100).setIouTh(0).setTopK(maxFaceNum);
        ImageMat imageMat = null;
        FaceImage faceImage = null;
        try {
            imageMat = ImageMat.fromBase64(faceSearchDto.getImageBase64());
            faceImage = faceFeatureExtractor.extract(imageMat, extParam, new HashMap<>());
        } finally {
            if (null != imageMat) {
                imageMat.release();
            }
        }
        if (null == faceImage) {
            throw new RuntimeException("人脸特征提取失败");
        }
        List<FaceInfo> faceInfos = faceImage.faceInfos();
        if (faceInfos.size() <= 0) {
            throw new RuntimeException("图片不存在人脸");
        }
        float[][] vectors = new float[faceInfos.size()][];
        for (int i = 0; i < faceInfos.size(); i++) {
            vectors[i] = faceInfos.get(i).embedding.embeds;
        }
        //特征搜索
        int topK = (null == faceSearchDto.getLimit() || faceSearchDto.getLimit() <= 0) ? 5 : faceSearchDto.getLimit();
        SearchResponse<Map<String, Object>> searchResponse = searchEngineService.search(visualCollection.getVectorTable(), vectors, faceSearchDto.getAlgorithm().algorithm(), topK);
        if (!searchResponse.timedOut()) {
            throw new RuntimeException("人脸搜索超时");
        }
        //结果和人数是否一致
        List<Hit<Map<String, Object>>> hitList = searchResponse.hits().hits();
        if (hitList.size() > 0 && hitList.size() != faceInfos.size()) {
            throw new RuntimeException("搜索结果失败");
        }
        //如数据库中没有任何样本的情况下，会出现异常，这里进行单独处理
        if (hitList.size() == 0 && faceInfos.size() > 0) {
            List<FaceSearchVo> vos = new ArrayList<>();
            for (FaceInfo faceInfo : faceInfos) {
                FaceInfo.FaceBox box = faceInfo.box;
                FaceSearchVo vo = FaceSearchVo.build();
                vo.setLocation(FaceLocation.build(box.leftTop.x, box.leftTop.y, box.width(), box.height()));
                vo.setFaceScore((float) Math.floor(faceInfo.score * 1000000) / 10000);
                List<SampleFaceVo> match = new ArrayList<>();
                vo.setMatch(match);
                vos.add(vo);
            }
            return vos;
        }
        //获取关联数据ID
        Set<String> faceIds = new HashSet<>();
        for (Hit<Map<String, Object>> hit : hitList) {
            faceIds.add(hit.id());
        }
        //查询数据
        Map<String, Map<String, Object>> faceMapping = new HashMap<>(10);
        Map<String, Map<String, Object>> sampleMapping = new HashMap<>(10);
        if (faceIds.size() > 0) {
            List<Map<String, Object>> faceList = faceDataMapper.getByFaceIds(visualCollection.getFaceTable(), ValueUtil.getAllFaceColumnNames(visualCollection), new ArrayList<>(faceIds));
            Set<String> sampleIds = faceList.stream().map(item -> MapUtils.getString(item, Constant.ColumnNameSampleId)).collect(Collectors.toSet());
            List<Map<String, Object>> sampleList = sampleDataMapper.getBySampleIds(visualCollection.getSampleTable(), new ArrayList<>(sampleIds));
            faceMapping = ValueUtil.mapping(faceList, Constant.ColumnNameFaceId);
            sampleMapping = ValueUtil.mapping(sampleList, Constant.ColumnNameSampleId);
        }
        //构造返回结果
        List<FaceSearchVo> vos = new ArrayList<>();
        for (FaceInfo faceInfo : faceInfos) {
            FaceInfo.FaceBox box = faceInfo.box;
            FaceSearchVo vo = FaceSearchVo.build();
            vo.setLocation(FaceLocation.build(box.leftTop.x, box.leftTop.y, box.width(), box.height()));
            vo.setFaceScore((float) Math.floor(faceInfo.score * 1000000) / 10000);
            List<SampleFaceVo> match = new ArrayList<>();
            for (Hit<Map<String, Object>> hit : hitList) {
                Map<String, Object> face = faceMapping.get(hit.id());
                if (null != face) {
                    float faceScore = MapUtils.getFloatValue(face, Constant.ColumnNameFaceScore);
                    String sampleId = MapUtils.getString(face, Constant.ColumnNameSampleId);
                    float score = hit.score() == null ? 0 : Double.valueOf(hit.score().toString()).floatValue();
                    float confidence = score;
                    if (SearchAlgorithm.COSINESIMIL == faceSearchDto.getAlgorithm()) {
                        score = Similarity.cosEnhance(score);
                        confidence = (float) Math.floor(score * 1000000) / 10000;
                    }
                    if (null != sampleId && sampleMapping.containsKey(sampleId) && confidence >= faceSearchDto.getConfidenceThreshold()) {
                        Map<String, Object> sample = sampleMapping.get(sampleId);
                        SampleFaceVo faceVo = SampleFaceVo.build();
                        faceVo.setSampleId(sampleId);
                        faceVo.setFaceId(hit.id());
                        faceVo.setFaceScore(faceScore);
                        faceVo.setConfidence(confidence);
                        faceVo.setFaceData(ValueUtil.getFieldKeyValues(face, ValueUtil.getFaceColumns(visualCollection)));
                        faceVo.setSampleData(ValueUtil.getFieldKeyValues(sample, ValueUtil.getSampleColumns(visualCollection)));
                        match.add(faceVo);
                    }
                }
            }
            //排序
            Collections.sort(match);
            vo.setMatch(match);
            vos.add(vo);
        }
        return vos;
    }

}
