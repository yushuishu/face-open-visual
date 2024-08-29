package com.shuishu.face.openvisual.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuishu.face.openvisual.engine.conf.Constant;
import com.shuishu.face.openvisual.engine.service.SearchEngineService;
import com.shuishu.face.openvisual.server.entity.dto.*;
import com.shuishu.face.openvisual.server.entity.extend.FieldKeyValue;
import com.shuishu.face.openvisual.server.entity.extend.FieldKeyValues;
import com.shuishu.face.openvisual.server.entity.po.SampleData;
import com.shuishu.face.openvisual.server.entity.po.VisualCollection;
import com.shuishu.face.openvisual.server.entity.vo.SampleDataVo;
import com.shuishu.face.openvisual.server.entity.vo.SimpleFaceVo;
import com.shuishu.face.openvisual.server.mapper.FaceDataMapper;
import com.shuishu.face.openvisual.server.mapper.SampleDataMapper;
import com.shuishu.face.openvisual.server.mapper.VisualCollectionMapper;
import com.shuishu.face.openvisual.server.model.ColumnValue;
import com.shuishu.face.openvisual.server.service.SampleDataService;
import com.shuishu.face.openvisual.server.service.base.BaseService;
import com.shuishu.face.openvisual.utils.TableUtils;
import com.shuishu.face.openvisual.utils.ValueUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class SampleDataServiceImpl extends BaseService implements SampleDataService {

    private final SearchEngineService searchEngineService;
    private final VisualCollectionMapper visualCollectionMapper;
    private final FaceDataMapper faceDataMapper;
    private final SampleDataMapper sampleDataMapper;


    @Override
    public Boolean addSample(SampleDataAddDto sampleDataAddDto) {
        LambdaQueryWrapper<VisualCollection> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(VisualCollection::getNamespace, sampleDataAddDto.getNamespace())
                .eq(VisualCollection::getCollectionName, sampleDataAddDto.getCollectionName());
        VisualCollection visualCollection = visualCollectionMapper.selectOne(lambdaQueryWrapper);
        if (visualCollection == null) {
            throw new RuntimeException("集合不存在");
        }
        if (visualCollection.getStatue() != 0) {
            throw new RuntimeException("集合没有被使用");
        }
        // 查看样本是否存在
        long count = sampleDataMapper.count(visualCollection.getSampleTable(), sampleDataAddDto.getSampleId());
        if(count > 0){
            throw new RuntimeException("样本已经存在");
        }
        //获取数据类型
        Map<String, String> filedTypeMap = TableUtils.getSampleFiledTypeMap(visualCollection.getSchemaInfo());
        // 插入数据
        SampleData samplePo = new SampleData();
        samplePo.setSampleId(sampleDataAddDto.getSampleId());
        FieldKeyValues values = sampleDataAddDto.getSampleData();
        List<ColumnValue> columnValues = new ArrayList<>();
        if(null != values && !values.isEmpty()){
            for(FieldKeyValue value : values){
                if(filedTypeMap.containsKey(value.getKey())){
                    columnValues.add(new ColumnValue(value.getKey(), value.getValue(), filedTypeMap.get(value.getKey())));
                }
            }
        }
        samplePo.setColumnValues(columnValues);
        samplePo.setCreateTime(new Date());
        samplePo.setUpdateTime(new Date());
        int flag = sampleDataMapper.create(visualCollection.getSampleTable(), samplePo, samplePo.getColumnValues());
        return flag > 0;
    }

    @Override
    public Boolean updateSample(SampleDataUpdateDto sampleDataUpdateDto) {
        //获取待更新的数据
        FieldKeyValues sampleData = sampleDataUpdateDto.getSampleData();
        if(sampleData == null || sampleData.isEmpty()){
            throw new RuntimeException("样本数据不能为空");
        }
        //查看集合是否存在
        LambdaQueryWrapper<VisualCollection> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(VisualCollection::getNamespace, sampleDataUpdateDto.getNamespace())
                .eq(VisualCollection::getCollectionName, sampleDataUpdateDto.getCollectionName());
        VisualCollection visualCollection = visualCollectionMapper.selectOne(lambdaQueryWrapper);
        if (visualCollection == null) {
            throw new RuntimeException("集合不存在");
        }
        if (visualCollection.getStatue() != 0) {
            throw new RuntimeException("集合没有被使用");
        }
        //查看样本是否存在
        long count = sampleDataMapper.count(visualCollection.getSampleTable(), sampleDataUpdateDto.getSampleId());
        if(count <= 0){
            throw new RuntimeException("样本数据不存在");
        }
        //获取数据类型
        Map<String, String> filedTypeMap = TableUtils.getSampleFiledTypeMap(visualCollection.getSchemaInfo());
        List<ColumnValue> columnValues = new ArrayList<>();
        if(!sampleData.isEmpty()){
            for(FieldKeyValue value : sampleData){
                if(filedTypeMap.containsKey(value.getKey())){
                    columnValues.add(new ColumnValue(value.getKey(), value.getValue(), filedTypeMap.get(value.getKey())));
                }
            }
        }
        //更新数据
        int flag = sampleDataMapper.update(visualCollection.getSampleTable(), sampleDataUpdateDto.getSampleId(), columnValues);
        return flag > 0;
    }

    @Override
    public Boolean deleteSample(SampleDataDeleteDto sampleDataDeleteDto) {
        //查看集合是否存在
        LambdaQueryWrapper<VisualCollection> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(VisualCollection::getNamespace, sampleDataDeleteDto.getNamespace())
                .eq(VisualCollection::getCollectionName, sampleDataDeleteDto.getCollectionName());
        VisualCollection visualCollection = visualCollectionMapper.selectOne(lambdaQueryWrapper);
        if (visualCollection == null) {
            throw new RuntimeException("集合不存在");
        }
        if (visualCollection.getStatue() != 0) {
            throw new RuntimeException("集合没有被使用");
        }
        //查看样本是否存在
        long count = sampleDataMapper.count(visualCollection.getSampleTable(), sampleDataDeleteDto.getSampleId());
        if(count <= 0){
            throw new RuntimeException("样本不存在");
        }
        //删除向量数据
        List<String> faceIds = faceDataMapper.getFaceIdBySampleId(visualCollection.getFaceTable(), sampleDataDeleteDto.getSampleId());
        searchEngineService.deleteVectorByKey(visualCollection.getVectorTable(), faceIds);
        //删除人脸数据
        faceDataMapper.deleteBySampleId(visualCollection.getFaceTable(), sampleDataDeleteDto.getSampleId());
        //删除样本数据
        int flag = sampleDataMapper.delete(visualCollection.getSampleTable(), sampleDataDeleteDto.getSampleId());
        return flag > 0;
    }

    @Override
    public SampleDataVo findSampleDetails(SampleDataDetailsDto sampleDataDetailsDto) {
        //查看集合是否存在
        LambdaQueryWrapper<VisualCollection> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(VisualCollection::getNamespace, sampleDataDetailsDto.getNamespace())
                .eq(VisualCollection::getCollectionName, sampleDataDetailsDto.getCollectionName());
        VisualCollection visualCollection = visualCollectionMapper.selectOne(lambdaQueryWrapper);
        if (visualCollection == null) {
            throw new RuntimeException("集合不存在");
        }
        if (visualCollection.getStatue() != 0) {
            throw new RuntimeException("集合没有被使用");
        }
        //查询数据
        Map<String, Object> map = sampleDataMapper.getBySampleId(visualCollection.getSampleTable(), sampleDataDetailsDto.getSampleId());
        if(map == null || map.isEmpty()){
            return null;
        }
        //查询人脸数据
        List<Map<String, Object>> facesMap = faceDataMapper.getBySampleId(visualCollection.getFaceTable(), sampleDataDetailsDto.getSampleId());
        //组织数据
        SampleDataVo vo = new SampleDataVo();
        vo.setNamespace(sampleDataDetailsDto.getNamespace());
        vo.setCollectionName(sampleDataDetailsDto.getCollectionName());
        vo.setSampleId(sampleDataDetailsDto.getSampleId());
        vo.setSampleData(ValueUtil.getFieldKeyValues(map, ValueUtil.getSampleColumns(visualCollection)));
        List<SimpleFaceVo> faces = new ArrayList<>();
        for(Map<String, Object> faceMap : facesMap){
            String faceId = MapUtils.getString(faceMap, Constant.ColumnNameFaceId);
            Float faceScore = MapUtils.getFloat(faceMap, Constant.ColumnNameFaceScore);
            SimpleFaceVo simpleFace = SimpleFaceVo.build(faceId).setFaceScore(faceScore);
            simpleFace.setFaceData(ValueUtil.getFieldKeyValues(faceMap, ValueUtil.getFaceColumns(visualCollection)));
            faces.add(simpleFace);
        }
        vo.setFaces(faces);
        return vo;
    }

    @Override
    public Page<SampleDataVo> findSamplePage(SampleDataPageDto sampleDataPageDto, Page<SampleDataVo> page) {
        //查看集合是否存在
        LambdaQueryWrapper<VisualCollection> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(VisualCollection::getNamespace, sampleDataPageDto.getNamespace())
                .eq(VisualCollection::getCollectionName, sampleDataPageDto.getCollectionName());
        VisualCollection visualCollection = visualCollectionMapper.selectOne(lambdaQueryWrapper);
        if (visualCollection == null) {
            throw new RuntimeException("集合不存在");
        }
        if (visualCollection.getStatue() != 0) {
            throw new RuntimeException("集合没有被使用");
        }
        Page<Map<String, Object>> tempPage = new Page<>();
        tempPage.setCurrent(page.getCurrent());
        tempPage.setSize(page.getSize());
        IPage<Map<String, Object>> samplePage = sampleDataMapper.findSamplePage(tempPage, visualCollection.getSampleTable());
        List<String> sampleIds = samplePage.getRecords().stream().map(item -> MapUtils.getString(item, Constant.ColumnNameSampleId)).collect(Collectors.toList());
        List<Map<String, Object>> faceList = faceDataMapper.getBySampleIds(visualCollection.getFaceTable(), sampleIds);
        Map<String, List<Map<String, Object>>> faceMapping = new HashMap<>(100);
        for(Map<String, Object> face : faceList){
            String sampleId = MapUtils.getString(face, Constant.ColumnNameSampleId);
            if(!faceMapping.containsKey(sampleId)){
                faceMapping.put(sampleId, new ArrayList<>());
            }
            faceMapping.get(sampleId).add(face);
        }
        //组织数据
        List<SampleDataVo> result = new ArrayList<>();
        for(Map<String, Object> sample : samplePage.getRecords()){
            String sampleId = MapUtils.getString(sample, Constant.ColumnNameSampleId);
            SampleDataVo vo = new SampleDataVo();
            vo.setNamespace(sampleDataPageDto.getNamespace());
            vo.setCollectionName(sampleDataPageDto.getCollectionName());
            vo.setSampleId(sampleId);
            vo.setSampleData(ValueUtil.getFieldKeyValues(sample, ValueUtil.getSampleColumns(visualCollection)));
            List<SimpleFaceVo> facesVo = new ArrayList<>();
            if(faceMapping.containsKey(sampleId)){
                List<Map<String, Object>> facesMap = faceMapping.get(sampleId);
                for(Map<String, Object> faceMap : facesMap){
                    String faceId = MapUtils.getString(faceMap, Constant.ColumnNameFaceId);
                    Float faceScore = MapUtils.getFloat(faceMap, Constant.ColumnNameFaceScore);
                    SimpleFaceVo simpleFace = SimpleFaceVo.build(faceId).setFaceScore(faceScore);
                    simpleFace.setFaceData(ValueUtil.getFieldKeyValues(faceMap, ValueUtil.getFaceColumns(visualCollection)));
                    facesVo.add(simpleFace);
                }
            }
            vo.setFaces(facesVo);
            result.add(vo);
        }
        page.setRecords(result);
        page.setTotal(samplePage.getTotal());
        page.setPages(samplePage.getPages());
        return page;
    }


}
