package com.shuishu.face.openvisual.server.entity.extend;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;


/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-27 23:14
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Schema(description = "自定义字段")
public class FiledColumn implements Serializable {

    @Schema(description="字段名称,支持小写字母、数字和下划线的组合，最大32个字符")
    private String name;

    @Schema(description="字段描述,最大64个字符")
    private String comment;

    @Schema(description="字段类型，不能为UNDEFINED类型")
    private FiledDataType dataType;


    public static FiledColumn build(){
        return new FiledColumn();
    }

    public String getName() {
        return name;
    }

    public FiledColumn setName(String name) {
        this.name = name;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public FiledColumn setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public FiledDataType getDataType() {
        return dataType;
    }

    public FiledColumn setDataType(FiledDataType dataType) {
        this.dataType = dataType;
        return this;
    }

}
