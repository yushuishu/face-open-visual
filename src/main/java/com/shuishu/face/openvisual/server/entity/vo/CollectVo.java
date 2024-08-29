package com.shuishu.face.openvisual.server.entity.vo;


import com.shuishu.face.openvisual.server.entity.extend.FiledColumn;
import com.shuishu.face.openvisual.server.entity.storage.StorageEngine;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-27 20:10
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@ToString
@Schema(description = "集合Vo")
public class CollectVo {

    @Schema(description = "命名空间:最大12个字符,支持小写字母、数字和下划线的组合")
    private String namespace;

    @Schema(description = "集合名称:最大24个字符,支持小写字母、数字和下划线的组合")
    private String collectionName;

    @Schema(description = "集合描述:最大128个字符")
    private String collectionComment;

    @Schema(description = "数据分片中最大的文件个数，默认为0（不限制）,仅对Proxima引擎生效")
    private Long replicasNum;

    @Schema(description = "要创建的集合的分片数，默认为0（即系统默认）,仅对Milvus引擎生效")
    private Integer shardsNum;

    @Schema(description="自定义的样本属性字段")
    private List<FiledColumn> sampleColumns = new ArrayList<>();

    @Schema(description="自定义的人脸属性字段")
    private List<FiledColumn> faceColumns = new ArrayList<>();

    @Schema(description="是否保留图片及人脸信息")
    private Boolean storageFaceInfo;

    @Schema(description="保留图片及人脸信息的存储组件")
    private StorageEngine storageEngine;

    public static CollectVo build(String namespace, String collectionName) {
        return new CollectVo().setNamespace(namespace).setCollectionName(collectionName);
    }

    public String getNamespace() {
        return namespace;
    }

    public CollectVo setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public CollectVo setCollectionName(String collectionName) {
        this.collectionName = collectionName;
        return this;
    }

    public String getCollectionComment() {
        return collectionComment;
    }

    public CollectVo setCollectionComment(String collectionComment) {
        this.collectionComment = collectionComment;
        return this;
    }

    public Long getReplicasNum() {
        return replicasNum;
    }

    public CollectVo setReplicasNum(Long replicasNum) {
        this.replicasNum = replicasNum;
        return this;
    }

    public Integer getShardsNum() {
        return shardsNum;
    }

    public CollectVo setShardsNum(Integer shardsNum) {
        this.shardsNum = shardsNum;
        return this;
    }

    public List<FiledColumn> getSampleColumns() {
        return sampleColumns;
    }

    public CollectVo setSampleColumns(List<FiledColumn> sampleColumns) {
        this.sampleColumns = sampleColumns;
        return this;
    }

    public List<FiledColumn> getFaceColumns() {
        return faceColumns;
    }

    public CollectVo setFaceColumns(List<FiledColumn> faceColumns) {
        this.faceColumns = faceColumns;
        return this;
    }

    public Boolean getStorageFaceInfo() {
        return storageFaceInfo;
    }

    public CollectVo setStorageFaceInfo(Boolean storageFaceInfo) {
        this.storageFaceInfo = storageFaceInfo;
        return this;
    }

    public StorageEngine getStorageEngine() {
        return storageEngine;
    }

    public CollectVo setStorageEngine(StorageEngine storageEngine) {
        this.storageEngine = storageEngine;
        return this;
    }
}
