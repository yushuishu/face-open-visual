package com.shuishu.face.openvisual.core.base;

import com.shuishu.face.openvisual.core.domain.FaceInfo;
import com.shuishu.face.openvisual.core.domain.ImageMat;

import java.util.List;
import java.util.Map;


/**
 * 人脸检测接口
 */
public interface FaceDetection {

    /**
     * 获取人脸信息
     *
     * @param image   图像信息
     * @param scoreTh 人脸人数阈值
     * @param iouTh   人脸iou阈值
     * @param params  参数信息
     * @return -
     */
    List<FaceInfo> inference(ImageMat image, float scoreTh, float iouTh, Map<String, Object> params);

}
