package com.shuishu.face.openvisual.utils;

import com.shuishu.face.openvisual.engine.conf.Constant;
import com.shuishu.face.openvisual.server.entity.extend.FieldKeyValue;
import com.shuishu.face.openvisual.server.entity.extend.FieldKeyValues;
import com.shuishu.face.openvisual.server.entity.extend.FiledColumn;
import com.shuishu.face.openvisual.server.entity.extend.FiledDataType;
import com.shuishu.face.openvisual.server.entity.po.VisualCollection;
import com.shuishu.face.openvisual.server.entity.vo.CollectVo;
import org.apache.commons.collections4.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValueUtil {

    public static List<FiledColumn> getFaceColumns(VisualCollection visualCollection) {
        if (null != visualCollection.getSchemaInfo() && !visualCollection.getSchemaInfo().isEmpty()) {
            CollectVo collectVo = JsonUtil.toEntity(visualCollection.getSchemaInfo(), CollectVo.class);
            if (null != collectVo && null != collectVo.getFaceColumns()) {
                return collectVo.getFaceColumns();
            }
        }
        return new ArrayList<>();
    }


    public static List<FiledColumn> getSampleColumns(VisualCollection visualCollection) {
        if (null != visualCollection.getSchemaInfo() && !visualCollection.getSchemaInfo().isEmpty()) {
            CollectVo collectVo = JsonUtil.toEntity(visualCollection.getSchemaInfo(), CollectVo.class);
            if (null != collectVo && null != collectVo.getSampleColumns()) {
                return collectVo.getSampleColumns();
            }
        }
        return new ArrayList<>();
    }

    public static List<String> getAllFaceColumnNames(VisualCollection collection) {
        List<FiledColumn> columns = getAllFaceColumns(collection);
        return columns.stream().map(FiledColumn::getName).collect(Collectors.toList());
    }

    public static List<FiledColumn> getAllFaceColumns(VisualCollection collection) {
        List<FiledColumn> columns = getFaceColumns(collection);
        columns.add(FiledColumn.build().setName(Constant.ColumnNameSampleId).setComment("样本ID").setDataType(FiledDataType.STRING));
        columns.add(FiledColumn.build().setName(Constant.ColumnNameFaceId).setComment("人脸ID").setDataType(FiledDataType.STRING));
        columns.add(FiledColumn.build().setName(Constant.ColumnNameFaceScore).setComment("人脸分数").setDataType(FiledDataType.FLOAT));
        return columns;
    }

    public static FieldKeyValues getFieldKeyValues(Map<String, Object> map, List<FiledColumn> columns) {
        columns = null != columns ? columns : new ArrayList<>();
        Map<String, String> keyMap = new HashMap<>();
        for (FiledColumn column : columns) {
            for (String dataKey : map.keySet()) {
                if (column.getName().toLowerCase().equalsIgnoreCase(dataKey.toLowerCase())) {
                    keyMap.put(column.getName(), dataKey);
                }
            }
        }
        FieldKeyValues values = FieldKeyValues.build();
        for (FiledColumn column : columns) {
            if (keyMap.containsKey(column.getName())) {
                values.add(FieldKeyValue.build(column.getName(), map.get(keyMap.get(column.getName()))));
            } else {
                values.add(FieldKeyValue.build(column.getName(), (String) null));
            }
        }
        return values;
    }

    public static Map<String, Map<String, Object>> mapping(List<Map<String, Object>> list, String key) {
        Map<String, Map<String, Object>> res = new HashMap<>();
        for (Map<String, Object> item : list) {
            String value = MapUtils.getString(item, key);
            if (null != value) {
                res.put(value, item);
            }
        }
        return res;
    }

    public static Map<Long, String> mapping(List<Map<String, Object>> list, String idKey, String faceKey) {
        Map<Long, String> res = new HashMap<>(10);
        for (Map<String, Object> item : list) {
            Long idValue = MapUtils.getLongValue(item, idKey);
            String faceValue = MapUtils.getString(item, faceKey);
            if (idValue > 0 && null != faceValue && !faceValue.isEmpty()) {
                res.put(idValue, faceValue);
            }
        }
        return res;
    }


    public static float[] convertVector(String faceVector) {
        List<Float> value = JsonUtil.toList(faceVector, Float.class);
        float[] arr = new float[value.size()];
        for (int i = 0; i < value.size(); i++) {
            arr[i] = value.get(i);
        }
        return arr;
    }
}
