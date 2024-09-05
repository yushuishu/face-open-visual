package com.shuishu.face.openvisual.server.entity.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-27 16:14
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@Setter
@Getter
@ToString
@Schema(description = "人脸添加dto")
public class FaceAddDto {

    @Schema(description = "馆代码")
    private String libraryCode;

    @Schema(description = "条码")
    private String barcode;

    @Schema(description = "人脸图片")
    private List<MultipartFile> faceFileList;

}
