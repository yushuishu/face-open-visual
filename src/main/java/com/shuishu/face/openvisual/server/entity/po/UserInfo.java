package com.shuishu.face.openvisual.server.entity.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shuishu.face.openvisual.server.entity.base.BasePo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

/**
 * @Author ：谁书-ss
 * @Date ：2024-09-04 20:17
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：用户信息
 * <p></p>
 */
@Setter
@Getter
@ToString
@Comment("用户信息po")
@TableName("user_info")
public class UserInfo extends BasePo {

    @TableId(value = "user_info_id", type = IdType.ASSIGN_ID)
    @Comment("用户信息id")
    private Long userInfoId;

    @TableField("library_code")
    @Comment("馆代码")
    private String libraryCode;

    @TableField("barcode")
    @Comment("条码")
    private String barcode;

    @TableField("user_age")
    @Comment("年龄")
    private Integer userAge;

    @TableField("user_gender")
    @Comment("性别（0:女；1:男）")
    private Integer userGender;

    @TableField("device_serial_number")
    @Comment("设备序列号")
    private String deviceSerialNumber;

    @TableField("register_type")
    @Comment("注册类型，1：正常注册；2：游客注册")
    private Integer registerType;


}
