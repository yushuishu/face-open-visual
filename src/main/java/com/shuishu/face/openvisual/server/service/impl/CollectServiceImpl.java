package com.shuishu.face.openvisual.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shuishu.face.openvisual.core.common.enums.CollectionStatue;
import com.shuishu.face.openvisual.engine.conf.Constant;
import com.shuishu.face.openvisual.engine.model.MapParam;
import com.shuishu.face.openvisual.engine.service.SearchEngineService;
import com.shuishu.face.openvisual.server.entity.dto.CollectAddDto;
import com.shuishu.face.openvisual.server.entity.dto.CollectDeleteDto;
import com.shuishu.face.openvisual.server.entity.dto.CollectDetailsDto;
import com.shuishu.face.openvisual.server.entity.dto.CollectListDto;
import com.shuishu.face.openvisual.server.entity.po.VisualCollection;
import com.shuishu.face.openvisual.server.entity.vo.CollectVo;
import com.shuishu.face.openvisual.server.mapper.VisualCollectionMapper;
import com.shuishu.face.openvisual.server.service.CollectService;
import com.shuishu.face.openvisual.server.service.OperateTableService;
import com.shuishu.face.openvisual.server.service.base.BaseService;
import com.shuishu.face.openvisual.utils.JsonUtil;
import com.shuishu.face.openvisual.utils.NiceThreadPool;
import com.shuishu.face.openvisual.utils.TableUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class CollectServiceImpl extends BaseService implements CollectService {

    private final VisualCollectionMapper visualCollectionMapper;
    private final SearchEngineService searchEngineService;
    private final OperateTableService operateTableService;


    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    @Override
    public Boolean addCollect(CollectAddDto collectAddDto) {
        LambdaQueryWrapper<VisualCollection> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(VisualCollection::getNamespace, collectAddDto.getNamespace())
                .eq(VisualCollection::getCollectionName, collectAddDto.getCollectionName());
        VisualCollection visualCollection = visualCollectionMapper.selectOne(lambdaQueryWrapper);
        if (null != visualCollection) {
            throw new RuntimeException("集合已存在");
        }

        // 获取UUID
        String uuid = this.uuid();
        // 最终的数据库名称
        String namespace = collectAddDto.getNamespace();
        String collection = collectAddDto.getCollectionName();
        String randomStr = RandomStringUtils.randomAlphanumeric(4).toLowerCase();
        String prefixName = StringUtils.join(TABLE_PREFIX, CHAR_UNDERLINE, namespace, CHAR_UNDERLINE, collection, CHAR_UNDERLINE, randomStr);
        // 表名称
        String sampleTableName = StringUtils.join(prefixName, CHAR_UNDERLINE, "sample");
        String faceTableName = StringUtils.join(prefixName, CHAR_UNDERLINE, "face");
        String imageTableName = StringUtils.join(prefixName, CHAR_UNDERLINE, "image");
        String vectorTableName = StringUtils.join(prefixName, CHAR_UNDERLINE, "vector");
        // 判断表是否存在
        if (operateTableService.exist(sampleTableName)) {
            throw new RuntimeException("样本表已存在");
        }
        if (operateTableService.exist(faceTableName)) {
            throw new RuntimeException("人脸表已存在");
        }
        if (operateTableService.exist(imageTableName)) {
            throw new RuntimeException("图片表已存在");
        }
        if (searchEngineService.exist(vectorTableName)) {
            throw new RuntimeException("向量表已存在");
        }
        //创建服务需要的数据表
        try {
            //新建样本表
            boolean createSampleFlag = operateTableService.createSampleTable(sampleTableName, TableUtils.convert(collectAddDto.getSampleColumns()));
            if (!createSampleFlag) {
                throw new RuntimeException("创建样本表失败");
            }
            //新建人脸表
            boolean createFaceFlag = operateTableService.createFaceTable(faceTableName, TableUtils.convert(collectAddDto.getFaceColumns()));
            if (!createFaceFlag) {
                throw new RuntimeException("创建人脸表失败");
            }
            //创建图像数据表
            boolean createImageFlag = operateTableService.createImageTable(imageTableName);
            if (!createImageFlag) {
                throw new RuntimeException("创建图片表失败");
            }
            //创建人脸向量库
            MapParam param = MapParam.build()
                    .put(Constant.IndexShardsNum, collectAddDto.getShardsNum())
                    .put(Constant.IndexReplicasNum, collectAddDto.getReplicasNum());
            boolean createVectorFlag = searchEngineService.createCollection(vectorTableName, param);
            if (!createVectorFlag) {
                throw new RuntimeException("创建向量表失败");
            }
        } catch (Exception e) {
            //删除创建的表
            operateTableService.dropTable(sampleTableName);
            operateTableService.dropTable(faceTableName);
            operateTableService.dropTable(imageTableName);
            searchEngineService.dropCollection(vectorTableName);
            throw new RuntimeException(e);
        }
        //添加回滚事务
        //Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        boolean hasError = false;
        try {
            // 添加平台管理的集合信息
            VisualCollection insertCollection = new VisualCollection();
            insertCollection.setUuid(uuid);
            insertCollection.setNamespace(collectAddDto.getNamespace());
            insertCollection.setCollectionName(collectAddDto.getCollectionName());
            insertCollection.setDescribe(collectAddDto.getCollectionComment());
            insertCollection.setStatue(CollectionStatue.NORMAL.getValue());
            insertCollection.setSampleTable(sampleTableName);
            insertCollection.setFaceTable(faceTableName);
            insertCollection.setImageTable(imageTableName);
            insertCollection.setVectorTable(vectorTableName);
            insertCollection.setSchemaInfo(JsonUtil.toSimpleString(collectAddDto));
            int flag = visualCollectionMapper.insert(insertCollection);
            return flag > 0;
        } catch (Exception e) {
            //事务回滚
            hasError = true;
            //TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            throw new RuntimeException(e);
        } finally {
            // 删除创建的表
            if (hasError) {
                operateTableService.dropTable(sampleTableName);
                operateTableService.dropTable(faceTableName);
                operateTableService.dropTable(imageTableName);
                searchEngineService.dropCollection(vectorTableName);
            }
        }
    }

    @Override
    public Boolean deleteCollect(CollectDeleteDto collectDeleteDto) {
        LambdaQueryWrapper<VisualCollection> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(VisualCollection::getNamespace, collectDeleteDto.getNamespace())
                .eq(VisualCollection::getCollectionName, collectDeleteDto.getCollectionName());
        VisualCollection visualCollection = visualCollectionMapper.selectOne(lambdaQueryWrapper);
        if (null == visualCollection) {
            throw new RuntimeException("集合不存在");
        }
        // 删除数据
        visualCollectionMapper.deleteById(visualCollection);
        // 异步删除数据表
        NiceThreadPool.execute(() -> {
            operateTableService.dropTable(visualCollection.getSampleTable());
            operateTableService.dropTable(visualCollection.getFaceTable());
            if (StringUtils.isNotEmpty(visualCollection.getImageTable())) {
                operateTableService.dropTable(visualCollection.getImageTable());
            }
            searchEngineService.dropCollection(visualCollection.getVectorTable());
        });
        return true;
    }

    @Override
    public CollectVo findCollectDetails(CollectDetailsDto collectDetailsDto) {
        LambdaQueryWrapper<VisualCollection> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(VisualCollection::getNamespace, collectDetailsDto.getNamespace())
                .eq(VisualCollection::getCollectionName, collectDetailsDto.getCollectionName());
        VisualCollection visualCollection = visualCollectionMapper.selectOne(lambdaQueryWrapper);
        if (null == visualCollection) {
            throw new RuntimeException("集合不存在");
        }
        return JsonUtil.toEntity(visualCollection.getSchemaInfo(), CollectVo.class);
    }

    @Override
    public List<CollectVo> findCollectList(CollectListDto collectListDto) {
        LambdaQueryWrapper<VisualCollection> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(VisualCollection::getNamespace, collectListDto.getNamespace());
        List<VisualCollection> visualCollectionList = visualCollectionMapper.selectList(lambdaQueryWrapper);
        List<CollectVo> collectVoList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(visualCollectionList)) {
            for (VisualCollection visualCollection : visualCollectionList) {
                collectVoList.add(JsonUtil.toEntity(visualCollection.getSchemaInfo(), CollectVo.class));
            }
        }
        return collectVoList;
    }

}
