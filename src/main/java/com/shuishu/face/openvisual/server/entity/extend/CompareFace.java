package com.shuishu.face.openvisual.server.entity.extend;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "人脸比对Vo")
public class CompareFace {
    /**
     * 人脸质量分数
     **/
    @Schema(description = "A图片人脸分数:[0,100]")
    private Float faceScoreA;
    /**
     * 人脸质量分数
     **/
    @Schema(description = "B图片人脸分数:[0,100]")
    private Float faceScoreB;
    /**
     * 人脸位置信息
     **/
    @Schema(description = "A图片人脸位置信息")
    private FaceLocation locationA;
    /**
     * 人脸位置信息
     **/
    @Schema(description = "B图片人脸位置信息")
    private FaceLocation locationB;


    public Float getFaceScoreA() {
        return faceScoreA;
    }

    public void setFaceScoreA(Float faceScoreA) {
        this.faceScoreA = faceScoreA;
    }

    public FaceLocation getLocationA() {
        return locationA;
    }

    public void setLocationA(FaceLocation locationA) {
        this.locationA = locationA;
    }

    public Float getFaceScoreB() {
        return faceScoreB;
    }

    public void setFaceScoreB(Float faceScoreB) {
        this.faceScoreB = faceScoreB;
    }

    public FaceLocation getLocationB() {
        return locationB;
    }

    public void setLocationB(FaceLocation locationB) {
        this.locationB = locationB;
    }

    @Override
    public String toString() {
        return "CompareFace{" +
                "faceScoreA=" + faceScoreA +
                ", faceScoreB=" + faceScoreB +
                ", locationA=" + locationA +
                ", locationB=" + locationB +
                '}';
    }

}
