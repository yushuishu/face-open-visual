package com.shuishu.face.openvisual.server.entity.dto;


import com.shuishu.face.openvisual.server.entity.extend.SearchAlgorithm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-28 19:56
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@ToString
@Schema(description = "人脸数据搜索对象")
public class FaceSearchDto {
    /**
     * 命名空间
     **/
    @Schema(description = "命名空间")
    private String namespace;
    /**
     * 集合名称
     **/
    @Schema(description = "集合名称")
    private String collectionName;
    /**
     * 图像Base64编码值
     **/
    @Schema(description = "图像Base64编码值")
    private String imageBase64;
    /**
     * 人脸质量分数阈值：默认0,范围：[0,100]
     **/
    @Schema(description = "人脸质量分数阈值,范围：[0,100]：默认0。当设置为0时，会默认使用当前模型的默认值，该方法为推荐使用方式")
    private Float faceScoreThreshold = 0f;
    /**
     * 人脸匹配分数阈值：默认0,范围：[-100,100]
     **/
    @Schema(description = "人脸匹配分数阈值，范围：[-100,100]：默认0")
    private Float confidenceThreshold = 0f;
    /**
     * 选择搜索评分的算法，默认余弦相似度(COSINESIMIL)，可选参数：L1、L2、LINF、COSINESIMIL、INNERPRODUCT、HAMMINGBIT
     **/
    @Schema(hidden = true, description = "选择搜索评分的算法，默认是余弦相似度(COSINESIMIL)，可选参数：L1、L2、LINF、COSINESIMIL、INNERPRODUCT、HAMMINGBIT")
    private String algorithm = SearchAlgorithm.COSINESIMIL.name();
    /**
     * 搜索条数：默认10
     **/
    @Schema(description = "最大搜索条数：默认5")
    private Integer limit;
    /**
     * 对输入图像中多少个人脸进行检索比对
     **/
    @Schema(description = "对输入图像中多少个人脸进行检索比对：默认5")
    private Integer maxFaceNum;


    public static FaceSearchDto build(String namespace, String collectionName, String imageBase64) {
        return new FaceSearchDto()
                .setNamespace(namespace)
                .setCollectionName(collectionName)
                .setImageBase64(imageBase64);
    }

    public String getNamespace() {
        return namespace;
    }

    public FaceSearchDto setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public FaceSearchDto setCollectionName(String collectionName) {
        this.collectionName = collectionName;
        return this;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public FaceSearchDto setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
        return this;
    }

    public Float getFaceScoreThreshold() {
        return faceScoreThreshold;
    }

    public FaceSearchDto setFaceScoreThreshold(Float faceScoreThreshold) {
        this.faceScoreThreshold = faceScoreThreshold;
        return this;
    }

    public Float getConfidenceThreshold() {
        return confidenceThreshold;
    }

    public FaceSearchDto setConfidenceThreshold(Float confidenceThreshold) {
        this.confidenceThreshold = confidenceThreshold;
        return this;
    }

    public SearchAlgorithm getAlgorithm() {
        if (algorithm != null && !algorithm.isEmpty()) {
            return SearchAlgorithm.valueOf(this.algorithm);
        } else {
            return SearchAlgorithm.COSINESIMIL;
        }
    }

    public FaceSearchDto setAlgorithm(String algorithm) {
        if (algorithm != null) {
            this.algorithm = algorithm;
        }
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public FaceSearchDto setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Integer getMaxFaceNum() {
        return maxFaceNum;
    }

    public FaceSearchDto setMaxFaceNum(Integer maxFaceNum) {
        this.maxFaceNum = maxFaceNum;
        return this;
    }

}
