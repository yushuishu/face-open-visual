package com.shuishu.face.openvisual.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhenJiaQiu
 * @date 2020/2/17 15:38
 **/
public class ApiUtils {
    public static Map<String, String> convertRequestParamMap(Map<String, String[]> paramMap) {
        Map<String, String> newParamMap = new HashMap<>();
        if (paramMap != null) {
            for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
                String key = entry.getKey();
                String[] values = entry.getValue();
                newParamMap.put(key, values[0]);
            }
        }
        return newParamMap;
    }


    /**
     * 不同厂家相似度（比对得分）转化统一：配置文件 0-1
     */

    public static float getBaiduScore(float score) {
        return score / 100;
    }


}
