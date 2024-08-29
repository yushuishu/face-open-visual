package com.shuishu.face.openvisual.server.entity.dto;

import com.shuishu.face.openvisual.server.entity.extend.FiledColumn;
import com.shuishu.face.openvisual.server.entity.storage.StorageEngine;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-27 19:57
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@Setter
@Getter
@ToString
@Schema(description = "集合添加对象")
public class CollectAddDto {

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



    public static CollectAddDto build(String namespace, String collectionName) {
        return new CollectAddDto().setNamespace(namespace).setCollectionName(collectionName);
    }

    public String getNamespace() {
        return namespace;
    }

    public CollectAddDto setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public CollectAddDto setCollectionName(String collectionName) {
        this.collectionName = collectionName;
        return this;
    }

    public String getCollectionComment() {
        return collectionComment;
    }

    public CollectAddDto setCollectionComment(String collectionComment) {
        this.collectionComment = collectionComment;
        return this;
    }

    public Long getReplicasNum() {
        return replicasNum;
    }

    public CollectAddDto setReplicasNum(Long replicasNum) {
        this.replicasNum = replicasNum;
        return this;
    }

    public Integer getShardsNum() {
        return shardsNum;
    }

    public CollectAddDto setShardsNum(Integer shardsNum) {
        this.shardsNum = shardsNum;
        return this;
    }

    public List<FiledColumn> getSampleColumns() {
        return sampleColumns;
    }

    public CollectAddDto setSampleColumns(List<FiledColumn> sampleColumns) {
        if (sampleColumns != null) {
            this.sampleColumns = sampleColumns;
        }
        return this;
    }

    public List<FiledColumn> getFaceColumns() {
        return faceColumns;
    }

    public CollectAddDto setFaceColumns(List<FiledColumn> faceColumns) {
        if (faceColumns != null) {
            this.faceColumns = faceColumns;
        }
        return this;
    }

    public Boolean getStorageFaceInfo() {
        return storageFaceInfo;
    }

    public CollectAddDto setStorageFaceInfo(Boolean storageFaceInfo) {
        if (storageFaceInfo != null) {
            this.storageFaceInfo = storageFaceInfo;
        }
        return this;
    }

    public StorageEngine getStorageEngine() {
        return storageEngine;
    }

    public CollectAddDto setStorageEngine(StorageEngine storageEngine) {
        if (storageEngine != null) {
            this.storageEngine = storageEngine;
        }
        return this;
    }
}
