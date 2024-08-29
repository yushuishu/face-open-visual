package com.shuishu.face.openvisual.server.entity.vo;


import com.shuishu.face.openvisual.server.entity.extend.FieldKeyValues;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-28 14:59
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@ToString
@Schema(description = "样本Vo")
public class SampleDataVo {

    @Schema(description = "命名空间:最大12个字符,支持小写字母、数字和下划线的组合")
    private String namespace;

    @Schema(description = "集合名称:最大24个字符,支持小写字母、数字和下划线的组合")
    private String collectionName;

    @Schema(description = "样本ID:最大32个字符,支持小写字母、数字和下划线的组合")
    private String sampleId;

    @Schema(description = "扩展字段")
    private FieldKeyValues sampleData;

    @Schema(description="人脸数据")
    private List<SimpleFaceVo> faces = new ArrayList<>();

    public static SampleDataVo build(String namespace, String collectionName) {
        return new SampleDataVo().setNamespace(namespace).setCollectionName(collectionName);
    }

    public String getNamespace() {
        return namespace;
    }

    public SampleDataVo setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public SampleDataVo setCollectionName(String collectionName) {
        this.collectionName = collectionName;
        return this;
    }

    public String getSampleId() {
        return sampleId;
    }

    public SampleDataVo setSampleId(String sampleId) {
        this.sampleId = sampleId;
        return this;
    }

    public FieldKeyValues getSampleData() {
        return sampleData;
    }

    public SampleDataVo setSampleData(FieldKeyValues sampleData) {
        this.sampleData = sampleData;
        return this;
    }

    public List<SimpleFaceVo> getFaces() {
        return faces;
    }

    public SampleDataVo setFaces(List<SimpleFaceVo> faces) {
        this.faces = faces;
        return this;
    }
}
