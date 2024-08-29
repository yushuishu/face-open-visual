package com.shuishu.face.openvisual.server.entity.vo;


import com.shuishu.face.openvisual.server.entity.extend.FieldKeyValues;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Schema(description = "样本人脸对象")
public class SampleFaceVo implements Comparable<SampleFaceVo>, Serializable {

    /**
     * 样本ID
     **/
    @Schema(description = "样本ID")
    private String sampleId;
    /**
     * 人脸ID
     **/
    @Schema(description = "人脸ID")
    private String faceId;
    /**
     * 人脸人数质量
     **/
    @Schema(description = "人脸分数:[0,100]")
    private Float faceScore;
    /**
     * 转换后的置信度
     **/
    @Schema(description = "转换后的置信度:[-100,100]，值越大，相似度越高。")
    private Float confidence;
    /**
     * 样本扩展的额外数据
     **/
    @Schema(description = "样本扩展的额外数据")
    private FieldKeyValues sampleData;
    /**
     * 人脸扩展的额外数据
     **/
    @Schema(description = "人脸扩展的额外数据")
    private FieldKeyValues faceData;



    public static SampleFaceVo build() {
        return new SampleFaceVo();
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public FieldKeyValues getSampleData() {
        return sampleData;
    }

    public void setSampleData(FieldKeyValues sampleData) {
        this.sampleData = sampleData;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public FieldKeyValues getFaceData() {
        return faceData;
    }

    public void setFaceData(FieldKeyValues faceData) {
        this.faceData = faceData;
    }

    public Float getFaceScore() {
        return faceScore;
    }

    public void setFaceScore(Float faceScore) {
        this.faceScore = faceScore;
    }

    public Float getConfidence() {
        return confidence;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    @Override
    public int compareTo(SampleFaceVo that) {
        return Float.compare(that.confidence, this.confidence);
    }
}
