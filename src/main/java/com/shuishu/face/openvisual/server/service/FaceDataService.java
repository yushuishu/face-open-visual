package com.shuishu.face.openvisual.server.service;


import com.shuishu.face.openvisual.server.entity.dto.FaceDataAddDto;
import com.shuishu.face.openvisual.server.entity.dto.FaceDataDeleteDto;
import com.shuishu.face.openvisual.server.entity.vo.FaceDataVo;

public interface FaceDataService {

    /**
     * 添加人脸数据
     *
     * @param faceDataAddDto -
     * @return -
     */
    FaceDataVo addFaceData(FaceDataAddDto faceDataAddDto);

    /**
     * 删除人脸数据
     *
     * @param faceDataDeleteDto -
     * @return -
     */
    Boolean deleteFaceData(FaceDataDeleteDto faceDataDeleteDto);

}
