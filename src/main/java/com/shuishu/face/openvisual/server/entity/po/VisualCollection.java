package com.shuishu.face.openvisual.server.entity.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import java.util.Date;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-27 20:24
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@Setter
@Getter
@ToString
@TableName("visual_collection")
@Comment(value = "集合")
public class VisualCollection {
    /**
     * 自增主键自增
     */
    @TableId(value = "visual_collection_id", type = IdType.ASSIGN_ID)
    private Long visualCollectionId;

    /**
     * 字符唯一键
     */
    @TableField("uuid")
    private String uuid;

    /**
     * 命名空间
     */
    @TableField("namespace")
    private String namespace;

    /**
     * 集合名称
     */
    @TableField("collection_name")
    private String collectionName;

    /**
     * 集合描述
     */
    @TableField("describe")
    private String describe;

    /**
     * 集合状态
     */
    @TableField("statue")
    private Integer statue;

    /**
     * 样本数据表
     */
    @TableField("sample_table")
    private String sampleTable;

    /**
     * 人脸数据表
     */
    @TableField("face_table")
    private String faceTable;

    /**
     * 图片数据表
     */
    @TableField("image_table")
    private String imageTable;

    /**
     * 人脸向量库
     */
    @TableField("vector_table")
    private String vectorTable;

    /**
     * 集合元数据信息
     */
    @TableField("schema_info")
    private String schemaInfo;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableField("deleted")
    private Boolean deleted;

}
