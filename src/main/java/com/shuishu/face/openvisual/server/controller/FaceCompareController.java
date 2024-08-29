package com.shuishu.face.openvisual.server.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shuishu.face.openvisual.config.base.ApiResponse;
import com.shuishu.face.openvisual.server.entity.dto.FaceCompareDto;
import com.shuishu.face.openvisual.server.entity.vo.FaceCompareVo;
import com.shuishu.face.openvisual.server.service.FaceCompareService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "人脸比对")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api/face/basics/compare")
public class FaceCompareController {

    private FaceCompareService faceCompareService;

    @ApiOperationSupport(order = 1)
    @Operation(summary = "人脸比对1:1")
    @PostMapping(value = "one_to_one")
    public ApiResponse<FaceCompareVo> faceCompare(@RequestBody FaceCompareDto faceCompareDto) {
        return ApiResponse.of(faceCompareService.faceCompare(faceCompareDto));
    }
}
