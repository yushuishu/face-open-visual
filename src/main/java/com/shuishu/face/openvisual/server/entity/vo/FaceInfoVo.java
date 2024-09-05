package com.shuishu.face.openvisual.server.entity.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.shuishu.face.openvisual.server.entity.base.BaseVo;
import com.shuishu.face.openvisual.server.entity.po.FaceInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-28 22:23
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "人脸信息Vo")
public class FaceInfoVo extends BaseVo<FaceInfo> {

    @Schema(description = "人脸信息id")
    private Long faceInfoId;

    @Schema(description = "用户信息id")
    private Long userInfoId;

    @Schema(description = "馆代码")
    private String libraryCode;

    @Schema(description = "年龄")
    private Integer faceAge;

    @Schema(description = "性别")
    private Integer faceGender;

    @Schema(description = "特征值大小")
    private Integer featureSize;

    @Schema(description = "特征值")
    private String featureData;

    @Schema(description = "原图片url")
    private String originalImageUrl;

    @Schema(description = "剪切图片url")
    private String cropImageUrl;

    @Schema(description = "图片质量")
    private Integer imageQuality;

    @Schema(description = "俯仰角度")
    private Float pitchAngle;

    @Schema(description = "偏左右角度")
    private Float leftRightAngle;

    @Schema(description = "平面角度")
    private Float planeAngle;

    @Schema(description = "相似度")
    private Float similarity;

}
