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
 * @Date   ： 2024-08-28 22:24
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Setter
@Getter
@ToString
@Comment("人脸信息po")
@TableName("face_info")
public class FaceInfo extends BasePo {

    @TableId(value = "face_info_id", type = IdType.ASSIGN_ID)
    @Comment("人脸信息id")
    private Long faceInfoId;

    @TableField("user_info_id")
    @Comment("用户信息id")
    private Long userInfoId;

    @TableField("face_age")
    @Comment("年龄")
    private Integer faceAge;

    @TableField("face_gender")
    @Comment("性别")
    private Integer faceGender;

    @TableField("feature_size")
    @Comment("特征值大小")
    private Integer featureSize;

    @TableField("feature_data")
    @Comment("特征值")
    private String featureData;

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


}
