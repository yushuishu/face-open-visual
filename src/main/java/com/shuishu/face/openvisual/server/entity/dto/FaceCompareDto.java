package com.shuishu.face.openvisual.server.entity.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-28 21:03
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@ToString
@Schema(description = "人脸比对Dto")
public class FaceCompareDto {

    /**
     * 图像Base64编码值
     **/
    @Schema(description = "图像A的Base64编码值")
    private String imageBase64A;

    /**
     * 图像Base64编码值
     **/
    @Schema(description = "图像B的Base64编码值")
    private String imageBase64B;

    /**
     * 人脸质量分数阈值
     **/
    @Schema(description = "人脸质量分数阈值,范围：[0,100]：默认0。当设置为0时，会默认使用当前模型的默认值，该方法为推荐使用方式")
    private Float faceScoreThreshold = 0f;

    /**
     * 是否需要人脸信息
     **/
    @Schema(description = "是否需要人脸信息,默认为:true")
    private Boolean needFaceInfo = true;

    public String getImageBase64A() {
        return imageBase64A;
    }

    public void setImageBase64A(String imageBase64A) {
        this.imageBase64A = imageBase64A;
    }

    public String getImageBase64B() {
        return imageBase64B;
    }

    public void setImageBase64B(String imageBase64B) {
        this.imageBase64B = imageBase64B;
    }

    public Float getFaceScoreThreshold() {
        return faceScoreThreshold;
    }

    public void setFaceScoreThreshold(Float faceScoreThreshold) {
        if (null != faceScoreThreshold && faceScoreThreshold >= 0 && faceScoreThreshold <= 100) {
            this.faceScoreThreshold = faceScoreThreshold;
        }
    }

    public Boolean getNeedFaceInfo() {
        return needFaceInfo;
    }

    public void setNeedFaceInfo(Boolean needFaceInfo) {
        if (needFaceInfo != null) {
            this.needFaceInfo = needFaceInfo;
        }
    }
}
