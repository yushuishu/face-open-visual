package com.shuishu.face.openvisual.server.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shuishu.face.openvisual.server.entity.base.BasePo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;


/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-28 22:24
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Setter
@Getter
@ToString
@Comment("人脸实体")
@TableName("face_in")
public class FaceIn extends BasePo {

    @TableId(value = "face_id", type = IdType.ASSIGN_ID)
    @Comment("face_id")
    private Long faceInId;

    @TableField("barcode")
    @Comment("条码")
    private String barcode;

    @TableField("age")
    @Comment("年龄")
    private Integer age;

    @TableField("gender")
    @Comment("性别")
    private Integer gender;

    @TableField("feature_size")
    @Comment("特征值大小")
    private Integer featSize;

    @TableField("feature_data")
    @JsonIgnore
    @Comment("特征值")
    private String featsData;

    @TableField("feature_byte")
    @JsonIgnore
    @Comment("特征值")
    private byte[] featureByte;

    @TableField("original_image_url")
    @Comment("原图片url")
    private String originalImageUrl;

    @TableField("crop_image_url")
    @Comment("剪切图片url")
    private String cropImageUrl;

    @TableField("image_quality")
    @Comment("图片质量")
    private Integer imageQuality;

    @TableField("pitch_angle")
    @Comment("俯仰角度")
    private Float pitchAngle;

    @TableField("left_right_angle")
    @Comment("偏左右角度")
    private Float leftRightAngle;

    @TableField("plane_angle")
    @Comment("平面角度")
    private Float planeAngle;

    @TableField("library_code")
    @Comment("馆代码")
    private String libraryCode;

    @TableField("device_serial_number")
    @Comment("设备序列号")
    private String deviceSerialNumber;

    @TableField("register_type")
    @Comment("注册类型，1：正常注册；2：游客注册")
    private Integer registerType;

}
