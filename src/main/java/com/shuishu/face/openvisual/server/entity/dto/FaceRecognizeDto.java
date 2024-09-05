package com.shuishu.face.openvisual.server.entity.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-27 16:24
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@Setter
@Getter
@ToString
@Schema(description = "人脸识别对象")
public class FaceRecognizeDto {

    @Schema(description = "馆代码")
    private String libraryCode;

    @Schema(description = "人脸图片")
    private MultipartFile faceFile;

    @Schema(description = "识别模式：1：正常注册；2：游客注册（如果为2，识别不到存在的信息，则自动注册为游客模式）")
    private Integer mode;

}
