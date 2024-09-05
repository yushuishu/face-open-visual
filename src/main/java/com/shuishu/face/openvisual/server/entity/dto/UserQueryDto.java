package com.shuishu.face.openvisual.server.entity.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-27 16:16
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@Setter
@Getter
@ToString
@Schema(description = "用户信息查询dto")
public class UserQueryDto {

    @Schema(description = "馆代码")
    private String libraryCode;

    @Schema(description = "条码")
    private String barcode;

    @Schema(description = "添加日期范围：开始日期yyyy-MM-dd")
    private Date startAddDate;

    @Schema(description = "添加日期范围：结束日期yyyy-MM-dd")
    private Date endAddDate;

}
