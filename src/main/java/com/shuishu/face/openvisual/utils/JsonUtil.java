package com.shuishu.face.openvisual.utils;


import com.alibaba.fastjson2.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtil {

    /**
     * 将Bean转化为json字符串
     *
     * @param obj bean对象
     * @return json
     */
    public static String toString(Object obj) {
        return toString(obj, false, false);
    }

    public static String toSimpleString(Object obj) {
        return toString(obj, false, true);
    }

    /**
     * 将Bean转化为json字符串
     *
     * @param obj          bean对象
     * @param prettyFormat 是否格式化
     * @param noNull       是否排除空值
     * @return json
     */
    public static String toString(Object obj, boolean prettyFormat, boolean noNull) {
        // 创建 JSONWriter.Feature 列表
        List<JSONWriter.Feature> featureList = new ArrayList<>();
        // 判定是否格式化
        if (prettyFormat) {
            featureList.add(JSONWriter.Feature.PrettyFormat);
        }
        // 判定是否排除空值
        if (!noNull) {
            featureList.add(JSONWriter.Feature.WriteMapNullValue);
            featureList.add(JSONWriter.Feature.WriteNullListAsEmpty);
        }
        // 将特性列表转换为数组传递给序列化方法
        JSONWriter.Feature[] features = featureList.toArray(new JSONWriter.Feature[0]);
        // 将对象序列化为 JSON 字符串
        return JSON.toJSONString(obj, features);
    }


    /**
     * 将字符串转换为Entity
     *
     * @param json  数据字符串
     * @param clazz Entity class
     * @return
     */
    public static <T> T toEntity(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 将字符串转换为Entity
     *
     * @param json          数据字符串
     * @param typeReference Entity class
     * @return
     */
    public static <T> T toEntity(String json, TypeReference<T> typeReference) {
        return JSON.parseObject(json, typeReference);
    }

    /**
     * 将字符串转换为Map
     *
     * @param json 数据字符串
     * @return Map
     */
    public static Map<String, Object> toMap(String json) {
        return JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
        });
    }

    /**
     * 将字符串转换为List<T>
     *
     * @param json            数据字符串
     * @param collectionClass 泛型
     * @return list<T>
     */
    public static <T> List<T> toList(String json, Class<T> collectionClass) {
        return JSON.parseArray(json, collectionClass);
    }

    /**
     * 将字符串转换为List<Map<String, Object>>
     *
     * @param json 数据字符串
     * @return list<map>
     */
    public static List<Map<String, Object>> toListMap(String json) {
        return JSON.parseObject(json, new TypeReference<List<Map<String, Object>>>() {
        });
    }

    /**
     * 将字符串转换为Object
     *
     * @param json 数据字符串
     * @return list<map>
     */
    public static JSONObject toJsonObject(String json) {
        return JSON.parseObject(json);
    }

    /**
     * 将字符串转换为Array
     *
     * @param json 数据字符串
     * @return list<map>
     */
    public static JSONArray toJsonArray(String json) {
        return JSON.parseArray(json);
    }

}

