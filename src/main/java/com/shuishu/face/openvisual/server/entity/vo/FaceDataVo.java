package com.shuishu.face.openvisual.server.entity.vo;


import com.shuishu.face.openvisual.server.entity.extend.FieldKeyValues;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-28 18:05
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@ToString
@Schema(description = "人脸数据对象")
public class FaceDataVo {

    @Schema(description = "命名空间:最大12个字符,支持小写字母、数字和下划线的组合")
    private String namespace;

    @Schema(description = "集合名称:最大24个字符,支持小写字母、数字和下划线的组合")
    private String collectionName;

    @Schema(description = "样本ID:最大32个字符,支持小写字母、数字和下划线的组合")
    private String sampleId;

    @Schema(description = "扩展字段")
    private FieldKeyValues faceData;

    @Schema(description="人脸ID")
    private String faceId;

    @Schema(description="人脸人数质量")
    private Float faceScore;

    public static FaceDataVo build(String namespace, String collectionName, String sampleId, String faceId) {
        return new FaceDataVo()
                .setNamespace(namespace)
                .setCollectionName(collectionName)
                .setSampleId(sampleId)
                .setFaceId(faceId);
    }

    public String getNamespace() {
        return namespace;
    }

    public FaceDataVo setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public FaceDataVo setCollectionName(String collectionName) {
        this.collectionName = collectionName;
        return this;
    }

    public String getSampleId() {
        return sampleId;
    }

    public FaceDataVo setSampleId(String sampleId) {
        this.sampleId = sampleId;
        return this;
    }

    public FieldKeyValues getFaceData() {
        return faceData;
    }

    public FaceDataVo setFaceData(FieldKeyValues faceData) {
        this.faceData = faceData;
        return this;
    }

    public String getFaceId() {
        return faceId;
    }

    public FaceDataVo setFaceId(String faceId) {
        this.faceId = faceId;
        return this;
    }

    public Float getFaceScore() {
        return faceScore;
    }

    public FaceDataVo setFaceScore(Float faceScore) {
        this.faceScore = faceScore;
        return this;
    }
}
