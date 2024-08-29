package com.shuishu.face.openvisual.core.base;


import com.shuishu.face.openvisual.core.domain.FaceInfo;
import com.shuishu.face.openvisual.core.domain.ImageMat;

import java.util.Map;

public interface FaceAttribute {

    /**
     * 人脸属性信息
     *
     * @param imageMat 图像数据
     * @param params   参数信息
     * @return -
     */
    FaceInfo.Attribute inference(ImageMat imageMat, Map<String, Object> params);

}
