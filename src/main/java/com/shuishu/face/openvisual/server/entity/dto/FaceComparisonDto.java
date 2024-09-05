package com.shuishu.face.openvisual.server.entity.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-27 16:33
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@Setter
@Getter
@ToString
@Schema(description = "人脸比对对象")
public class FaceComparisonDto {

    @Schema(description = "人脸图片1")
    private MultipartFile faceFileOne;

    @Schema(description = "人脸图片2")
    private MultipartFile faceFileTwo;

}
