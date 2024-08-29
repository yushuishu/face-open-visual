package com.shuishu.face.openvisual.core.base;

import com.shuishu.face.openvisual.core.domain.FaceInfo;
import com.shuishu.face.openvisual.core.domain.ImageMat;

import java.util.Map;


/**
 * 对图像进行对齐
 */
public interface FaceAlignment {

    /**
     * 对图像进行对齐
     *
     * @param imageMat 图像信息
     * @param params   参数信息
     * @return -
     * @imagePoint -
     */
    ImageMat inference(ImageMat imageMat, FaceInfo.Points imagePoint, Map<String, Object> params);

}
