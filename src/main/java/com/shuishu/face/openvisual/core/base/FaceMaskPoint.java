package com.shuishu.face.openvisual.core.base;


import com.shuishu.face.openvisual.core.domain.ImageMat;
import com.shuishu.face.openvisual.core.domain.QualityInfo;

import java.util.Map;

/**
 * 人脸关键点检测
 */
public interface FaceMaskPoint {

    /**
     * 人脸关键点检测
     *
     * @param imageMat 图像数据
     * @param params   参数信息
     * @return -
     */
    QualityInfo.MaskPoints inference(ImageMat imageMat, Map<String, Object> params);

}
