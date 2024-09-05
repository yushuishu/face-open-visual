package com.shuishu.face.openvisual.server.entity.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @Author ：谁书-ss
 * @Date ：2024-09-04 19:59
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@Setter
@Getter
@ToString
@Schema(description = "人脸识别记录dto")
public class FaceRecognizeRecordDto {

    @Schema(description = "馆代码")
    private String libraryCode;

    @Schema(description = "识别模式：1：正常注册；2：游客注册（如果为2，识别不到存在的信息，则自动注册为游客模式）")
    private Integer mode;

    private Date date;

}
