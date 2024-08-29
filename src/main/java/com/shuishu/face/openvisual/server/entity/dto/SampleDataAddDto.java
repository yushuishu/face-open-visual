package com.shuishu.face.openvisual.server.entity.dto;


import com.shuishu.face.openvisual.server.entity.extend.FieldKeyValues;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-28 14:26
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@Setter
@Getter
@ToString
@Schema(description = "样本添加对象")
public class SampleDataAddDto {

    @Schema(description = "命名空间:最大12个字符,支持小写字母、数字和下划线的组合")
    private String namespace;

    @Schema(description = "集合名称:最大24个字符,支持小写字母、数字和下划线的组合")
    private String collectionName;

    @Schema(description = "样本ID:最大32个字符,支持小写字母、数字和下划线的组合")
    private String sampleId;

    @Schema(description = "扩展字段")
    private FieldKeyValues sampleData;

}
