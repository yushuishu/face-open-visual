package com.shuishu.face.openvisual.server.entity.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.shuishu.face.openvisual.server.entity.base.BaseVo;
import com.shuishu.face.openvisual.server.entity.po.UserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @Author ：谁书-ss
 * @Date ：2024-09-05 10:14
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "用户信息Vo")
public class UserInfoVo extends BaseVo<UserInfo> {

    @Schema(description = "用户信息id")
    private Long userInfoId;

    @Schema(description = "馆代码")
    private String libraryCode;

    @Schema(description = "条码")
    private String barcode;

    @Schema(description = "年龄")
    private Integer userAge;

    @Schema(description = "性别")
    private Integer userGender;

    @Schema(description = "设备序列号")
    private String deviceSerialNumber;

    @Schema(description = "注册类型，1：正常注册；2：游客注册")
    private Integer registerType;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @Schema(description = "创建用户id")
    private Long createUserId;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    @Schema(description = "更新用户id")
    private Long updateUserId;

    @Schema(description = "人脸信息Vo集合")
    private List<FaceInfoVo> faceInfoVoList;

}
