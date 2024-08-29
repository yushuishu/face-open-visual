package com.shuishu.face.openvisual.server.service;


import com.shuishu.face.openvisual.server.entity.dto.FaceSearchDto;
import com.shuishu.face.openvisual.server.entity.vo.FaceSearchVo;

import java.util.List;


/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-28 19:53
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
public interface FaceSearchService {

    /**
     * 人脸搜索M:N
     *
     * @param faceSearchDto -
     * @return -
     */
    List<FaceSearchVo> findFaceRecognition(FaceSearchDto faceSearchDto);

}
