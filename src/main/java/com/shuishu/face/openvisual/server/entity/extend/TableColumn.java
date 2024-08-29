package com.shuishu.face.openvisual.server.entity.extend;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Schema(description = "表字段对象")
public class TableColumn {

    @Schema(description = "字段名称")
    private String name;

    @Schema(description = "字段描述")
    private String desc;

    @Schema(description = "字段数据类型")
    private String type;

    public TableColumn() {
    }

    public TableColumn(String name, String desc, String type) {
        this.name = name;
        this.desc = desc;
        this.type = type;
    }

}
