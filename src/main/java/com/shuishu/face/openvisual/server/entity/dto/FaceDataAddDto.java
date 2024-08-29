package com.shuishu.face.openvisual.server.entity.dto;


import com.shuishu.face.openvisual.server.entity.extend.FieldKeyValues;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-28 19:18
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@ToString
@Schema(description = "人脸数据添加对象")
public class FaceDataAddDto {

    @Schema(description = "命名空间:最大12个字符,支持小写字母、数字和下划线的组合")
    private String namespace;

    @Schema(description = "集合名称:最大24个字符,支持小写字母、数字和下划线的组合")
    private String collectionName;

    @Schema(description = "样本ID:最大32个字符,支持小写字母、数字和下划线的组合")
    private String sampleId;

    @Schema(description = "扩展字段")
    private FieldKeyValues faceData;

    @Schema(description = "图像Base64编码值")
    private String imageBase64;

    @Schema(description = "人脸质量分数阈值,范围：[0,100]：默认0。当设置为0时，会默认使用当前模型的默认值，该方法为推荐使用方式")
    private Float faceScoreThreshold = 0f;

    @Schema(description = "当前样本的人脸相似度的最小阈值,范围：[0,100]：默认0。当设置为0时，表示不做类间相似度判断逻辑,开启后对效率有较大影响")
    private Float minConfidenceThresholdWithThisSample = 0f;

    @Schema(description = "当前样本与其他样本的人脸相似度的最大阈值,范围：[0,100]：默认0。当设置为0时，表示不做类间相似度判断逻辑,开启后对效率有较大影响")
    private Float maxConfidenceThresholdWithOtherSample = 0f;


    public static FaceDataAddDto build(String namespace, String collectionName, String sampleId) {
        return new FaceDataAddDto()
                .setNamespace(namespace)
                .setCollectionName(collectionName)
                .setSampleId(sampleId);
    }

    public String getNamespace() {
        return namespace;
    }

    public FaceDataAddDto setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public FaceDataAddDto setCollectionName(String collectionName) {
        this.collectionName = collectionName;
        return this;
    }

    public String getSampleId() {
        return sampleId;
    }

    public FaceDataAddDto setSampleId(String sampleId) {
        this.sampleId = sampleId;
        return this;
    }

    public FieldKeyValues getFaceData() {
        return faceData;
    }

    public FaceDataAddDto setFaceData(FieldKeyValues faceData) {
        this.faceData = faceData;
        return this;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public FaceDataAddDto setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
        return this;
    }

    public Float getFaceScoreThreshold() {
        return faceScoreThreshold;
    }

    public FaceDataAddDto setFaceScoreThreshold(Float faceScoreThreshold) {
        this.faceScoreThreshold = faceScoreThreshold;
        return this;
    }

    public Float getMinConfidenceThresholdWithThisSample() {
        return minConfidenceThresholdWithThisSample;
    }

    public FaceDataAddDto setMinConfidenceThresholdWithThisSample(Float minConfidenceThresholdWithThisSample) {
        this.minConfidenceThresholdWithThisSample = minConfidenceThresholdWithThisSample;
        return this;
    }

    public Float getMaxConfidenceThresholdWithOtherSample() {
        return maxConfidenceThresholdWithOtherSample;
    }

    public FaceDataAddDto setMaxConfidenceThresholdWithOtherSample(Float maxConfidenceThresholdWithOtherSample) {
        this.maxConfidenceThresholdWithOtherSample = maxConfidenceThresholdWithOtherSample;
        return this;
    }
}
