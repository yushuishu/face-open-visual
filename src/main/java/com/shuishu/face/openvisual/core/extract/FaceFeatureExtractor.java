package com.shuishu.face.openvisual.core.extract;


import com.shuishu.face.openvisual.core.domain.ExtParam;
import com.shuishu.face.openvisual.core.domain.FaceImage;
import com.shuishu.face.openvisual.core.domain.ImageMat;

import java.util.Map;

/**
 * 人脸特征提取器
 */
public interface FaceFeatureExtractor {

    /**
     * 人脸特征提取
     *
     * @param image    -
     * @param extParam -
     * @param params   -
     * @return -
     */
    FaceImage extract(ImageMat image, ExtParam extParam, Map<String, Object> params);

}
