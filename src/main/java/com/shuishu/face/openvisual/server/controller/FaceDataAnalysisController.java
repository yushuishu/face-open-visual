package com.shuishu.face.openvisual.server.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shuishu.face.openvisual.config.base.ApiResponse;
import com.shuishu.face.openvisual.server.entity.dto.FaceRecognizeRecordDto;
import com.shuishu.face.openvisual.server.entity.vo.FaceInfoVo;
import com.shuishu.face.openvisual.server.service.FaceDataAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ：谁书-ss
 * @Date ：2024-09-04 20:00
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@Tag(name = "人脸数据分析")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api/face/data/analysis")
public class FaceDataAnalysisController {

    private final FaceDataAnalysisService faceDataAnalysisService;


    @ApiOperationSupport(order = 1)
    @Operation(summary = "人脸识别记录", description = "普通模式，不存在返回空数据；游客模式，不存在直接注册为游客类型的人脸信息")
    @PostMapping("recognize/record/page")
    public ApiResponse<FaceInfoVo> findFaceRecognizeRecordPage(FaceRecognizeRecordDto faceRecognizeRecordDto, Page page) {

        return ApiResponse.success();
    }


}
