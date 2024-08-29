package com.shuishu.face.openvisual.server.service;


import com.shuishu.face.openvisual.server.entity.dto.FaceCompareDto;
import com.shuishu.face.openvisual.server.entity.vo.FaceCompareVo;

public interface FaceCompareService {

    /**
     * 人脸比对1:1
     *
     * @param faceCompareDto -
     * @return -
     */
    FaceCompareVo faceCompare(FaceCompareDto faceCompareDto);

}
