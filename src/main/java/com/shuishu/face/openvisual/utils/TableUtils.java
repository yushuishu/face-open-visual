package com.shuishu.face.openvisual.utils;


import com.shuishu.face.openvisual.server.entity.extend.FiledColumn;
import com.shuishu.face.openvisual.server.entity.extend.TableColumn;
import com.shuishu.face.openvisual.server.entity.vo.CollectVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableUtils {

    /**
     * 验证表名是否合法，只允许字母、数字和下划线
     *
     * @param tableName 表名称
     * @return true：合法 ；false：不合法
     */
    public static Boolean isValidTableName(String tableName) {
        return tableName != null && tableName.matches("^[a-zA-Z0-9_]+$");
    }

    public static List<TableColumn> convert(List<FiledColumn> columns) {
        if (null == columns) {
            return new ArrayList<>();
        }
        List<TableColumn> res = new ArrayList<>();
        for (FiledColumn column : columns) {
            String comment = column.getComment();
            if (null == comment) {
                comment = column.getName();
            }
            res.add(new TableColumn(column.getName(), column.getDataType().name().toLowerCase(), comment));
        }
        return res;
    }

    public static Map<String, String> getFaceFiledTypeMap(String schemaInfo) {
        CollectVo vo = JsonUtil.toEntity(schemaInfo, CollectVo.class);
        return getFiledTypeMap(vo.getFaceColumns());
    }

    public static Map<String, String> getSampleFiledTypeMap(String schemaInfo) {
        CollectVo vo = JsonUtil.toEntity(schemaInfo, CollectVo.class);
        return getFiledTypeMap(vo.getSampleColumns());
    }

    public static Map<String, String> getFiledTypeMap(List<FiledColumn> columns) {
        Map<String, String> map = new HashMap<String, String>(10);
        if (null != columns && !columns.isEmpty()) {
            for (FiledColumn column : columns) {
                map.put(column.getName(), column.getDataType().name().toLowerCase());
            }
        }
        return map;
    }


}
