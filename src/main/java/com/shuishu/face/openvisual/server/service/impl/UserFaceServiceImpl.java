package com.shuishu.face.openvisual.server.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuishu.face.openvisual.server.entity.dto.*;
import com.shuishu.face.openvisual.server.entity.vo.FaceInfoVo;
import com.shuishu.face.openvisual.server.entity.vo.UserInfoVo;
import com.shuishu.face.openvisual.server.mapper.FaceInfoMapper;
import com.shuishu.face.openvisual.server.mapper.UserInfoMapper;
import com.shuishu.face.openvisual.server.service.UserFaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class UserFaceServiceImpl implements UserFaceService {

    private final UserInfoMapper userInfoMapper;
    private final FaceInfoMapper faceInfoMapper;


    @Override
    public List<UserInfoVo> findUserFaceList(UserQueryDto userQueryDto) {
        return userInfoMapper.findUserFaceList(userQueryDto);
    }

    @Override
    public Page<UserInfoVo> page(UserPageDto userPageDto, Page page) {
        return null;
    }

    @Override
    public List<FaceInfoVo> bindingFace(FaceAddDto faceAddDto) {
        return List.of();
    }

    @Override
    public Boolean updateFace(FaceUpdateDto faceUpdateDto) {
        return null;
    }

    @Override
    public FaceInfoVo findUserByFaceRecognize(FaceRecognizeDto faceRecognizeDto) {
        return null;
    }

    @Override
    public Boolean deleteFace(FaceDeleteDto faceDeleteDto) {
        return null;
    }

    @Override
    public Integer comparisonFace(FaceComparisonDto faceComparisonDto) {
        return 0;
    }


}
