package com.shuishu.face.openvisual.server.entity.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-27 20:14
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@Setter
@Getter
@ToString
@Schema(description = "集合列表对象")
public class CollectListDto {

    @Schema(description = "命名空间")
    private String namespace;

}
