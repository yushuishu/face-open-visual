package com.shuishu.face.openvisual.server.entity.extend;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "字段键值对")
public class FieldKeyValue {

    @Schema(description = "字段名，与创建集合时给定的字段名一致")
    private String key;
    @Schema(description = "字段值，与创建集合时给定的类型一致，若为字符串，最大为512个字符")
    private Object value;

    public FieldKeyValue() {
    }

    public FieldKeyValue(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public static FieldKeyValue build(String key, String value) {
        return new FieldKeyValue(key, value);
    }

    public static FieldKeyValue build(String key, Boolean value) {
        return new FieldKeyValue(key, value);
    }

    public static FieldKeyValue build(String key, Integer value) {
        return new FieldKeyValue(key, value);
    }

    public static FieldKeyValue build(String key, Float value) {
        return new FieldKeyValue(key, value);
    }

    public static FieldKeyValue build(String key, Double value) {
        return new FieldKeyValue(key, value);
    }

    public static FieldKeyValue build(String key, Object value) {
        return new FieldKeyValue(key, value);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
