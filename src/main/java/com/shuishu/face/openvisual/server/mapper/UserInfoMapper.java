package com.shuishu.face.openvisual.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuishu.face.openvisual.server.entity.dto.UserQueryDto;
import com.shuishu.face.openvisual.server.entity.po.UserInfo;
import com.shuishu.face.openvisual.server.entity.vo.UserInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ：谁书-ss
 * @Date ：2024-09-05 10:53
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {


    List<UserInfoVo> findUserFaceList(@Param("userQueryDto") UserQueryDto userQueryDto);
}
