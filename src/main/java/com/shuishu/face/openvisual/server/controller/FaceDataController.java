package com.shuishu.face.openvisual.server.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shuishu.face.openvisual.config.base.ApiResponse;
import com.shuishu.face.openvisual.server.entity.dto.FaceDataAddDto;
import com.shuishu.face.openvisual.server.entity.dto.FaceDataDeleteDto;
import com.shuishu.face.openvisual.server.entity.vo.FaceDataVo;
import com.shuishu.face.openvisual.server.service.FaceDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-28 18:02
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Tag(name = "人脸数据管理")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api/face/basics/face")
public class FaceDataController {

    private final FaceDataService faceDataService;

    @ApiOperationSupport(order = 1)
    @Operation(summary = "添加人脸数据", description = "")
    @PostMapping(value = "add")
    public ApiResponse<FaceDataVo> addFaceData(@RequestBody FaceDataAddDto faceDataAddDto) {
        return ApiResponse.of(faceDataService.addFaceData(faceDataAddDto));
    }

    @ApiOperationSupport(order = 5)
    @Operation(summary = "删除人脸数据", description = "")
    @PostMapping(value = "delete")
    public ApiResponse<Boolean> deleteFaceData(@RequestBody FaceDataDeleteDto faceDataDeleteDto) {
        return ApiResponse.of(faceDataService.deleteFaceData(faceDataDeleteDto));
    }

}
