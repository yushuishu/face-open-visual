package com.shuishu.face.openvisual.server.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuishu.face.openvisual.server.entity.dto.*;
import com.shuishu.face.openvisual.server.entity.vo.FaceInfoVo;
import com.shuishu.face.openvisual.server.entity.vo.UserInfoVo;

import java.util.List;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-27 16:04
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
public interface UserFaceService {

    /**
     * 查询人脸列表
     *
     * @param userQueryDto -
     * @return -
     */
    List<UserInfoVo> findUserFaceList(UserQueryDto userQueryDto);

    /**
     * 分页查询人脸信息
     *
     * @param userPageDto -
     * @param page -
     * @return -
     */
    Page<UserInfoVo> page(UserPageDto userPageDto, Page page);

    /**
     * 绑定人脸
     *
     * @param faceAddDto -
     * @return -
     */
    List<FaceInfoVo> bindingFace(FaceAddDto faceAddDto);

    /**
     * 更新人脸
     *
     * @param faceUpdateDto -
     * @return -
     */
    Boolean updateFace(FaceUpdateDto faceUpdateDto);

    /**
     * 人脸识别
     *
     * @param faceRecognizeDto -
     * @return -
     */
    FaceInfoVo findUserByFaceRecognize(FaceRecognizeDto faceRecognizeDto);

    /**
     *
     * 解绑人脸
     * @param faceDeleteDto -
     * @return -
     */
    Boolean deleteFace(FaceDeleteDto faceDeleteDto);

    /**
     * 两张人脸图片比对，返回比对相似度得分
     *
     * @param faceComparisonDto -
     * @return -
     */
    Integer comparisonFace(FaceComparisonDto faceComparisonDto);

}
