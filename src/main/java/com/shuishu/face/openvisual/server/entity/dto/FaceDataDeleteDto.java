package com.shuishu.face.openvisual.server.entity.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-28 19:24
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@ToString
@Schema(description = "人脸数据删除对象")
public class FaceDataDeleteDto {
    @Schema(description = "命名空间:最大12个字符,支持小写字母、数字和下划线的组合")
    private String namespace;

    @Schema(description = "集合名称:最大24个字符,支持小写字母、数字和下划线的组合")
    private String collectionName;

    @Schema(description = "样本ID:最大32个字符,支持小写字母、数字和下划线的组合")
    private String sampleId;

    @Schema(description="人脸ID")
    private String faceId;


    public static FaceDataDeleteDto build(String namespace, String collectionName, String sampleId, String faceId) {
        return new FaceDataDeleteDto()
                .setNamespace(namespace)
                .setCollectionName(collectionName)
                .setSampleId(sampleId)
                .setFaceId(faceId);
    }

    public String getNamespace() {
        return namespace;
    }

    public FaceDataDeleteDto setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public FaceDataDeleteDto setCollectionName(String collectionName) {
        this.collectionName = collectionName;
        return this;
    }

    public String getSampleId() {
        return sampleId;
    }

    public FaceDataDeleteDto setSampleId(String sampleId) {
        this.sampleId = sampleId;
        return this;
    }

    public String getFaceId() {
        return faceId;
    }

    public FaceDataDeleteDto setFaceId(String faceId) {
        this.faceId = faceId;
        return this;
    }
}
