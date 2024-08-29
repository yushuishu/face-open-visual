package com.shuishu.face.openvisual.server.entity.vo;

import com.shuishu.face.openvisual.server.entity.extend.FieldKeyValues;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Schema(description = "人脸")
public class SimpleFaceVo implements Serializable {

    @Schema(description="人脸ID")
    private String faceId;

    @Schema(description="人脸扩展的额外数据")
    private FieldKeyValues faceData;

    @Schema(description="人脸分数")
    private Float faceScore;


    public static SimpleFaceVo build(String faceId){
        return new SimpleFaceVo().setFaceId(faceId);
    }

    public String getFaceId() {
        return faceId;
    }

    public SimpleFaceVo setFaceId(String faceId) {
        this.faceId = faceId;
        return this;
    }

    public FieldKeyValues getFaceData() {
        return faceData;
    }

    public SimpleFaceVo setFaceData(FieldKeyValues faceData) {
        this.faceData = faceData;
        return this;
    }

    public Float getFaceScore() {
        return faceScore;
    }

    public SimpleFaceVo setFaceScore(Float faceScore) {
        this.faceScore = faceScore;
        return this;
    }

}
