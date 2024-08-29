package com.shuishu.face.openvisual.server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shuishu.face.openvisual.config.base.ApiResponse;
import com.shuishu.face.openvisual.server.entity.dto.*;
import com.shuishu.face.openvisual.server.entity.po.FaceIn;
import com.shuishu.face.openvisual.server.service.FaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-27 14:07
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Tag(name = "人脸服务")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api/face/server")
public class FaceController {
    
    private final FaceService faceService;


    @ApiOperationSupport(order = 1)
    @Operation(summary = "查询人脸", description = "主要通过读者证号和馆code来查询读者的人脸信息")
    @GetMapping("user/list")
    public ApiResponse<List<FaceIn>> findUserFaceList(FaceQueryDto faceQueryDto) {
        return ApiResponse.success();
    }

    @ApiOperationSupport(order = 5)
    @Operation(summary = "分页", description = "")
    @GetMapping("user/page")
    public ApiResponse<Page<FaceIn>> page(FacePageDto facePageDto, Page page) {
        return ApiResponse.success();
    }

    @ApiOperationSupport(order = 10)
    @Operation(summary = "绑定人脸", description = "注册人脸")
    @PostMapping("user/add")
    public ApiResponse<List<FaceIn>> bindingFace(FaceAddDto faceAddDto) {
        return ApiResponse.success();
    }

    @ApiOperationSupport(order = 15)
    @Operation(summary = "更新人脸", description = "替换旧的人脸，服务处理会根据条码查询旧的人脸信息，更新特征值等信息")
    @PostMapping("update")
    public ApiResponse<String> updateFace(FaceUpdateDto faceUpdateDto) {
        return ApiResponse.success();
    }

    @ApiOperationSupport(order = 20)
    @Operation(summary = "人脸识别", description = "普通模式，不存在返回空数据；游客模式，不存在直接注册为游客类型的人脸信息")
    @PostMapping("recognize")
    public ApiResponse<FaceIn> findUserByFaceRecognize(FaceRecognizeDto faceRecognizeDto) {
        return ApiResponse.success();
    }

    @ApiOperationSupport(order = 25)
    @Operation(summary = "解绑人脸", description = "通过条码，准确无误的解绑人脸信息")
    @PostMapping("delete")
    public ApiResponse<Void> deleteFace(FaceDeleteDto faceDeleteDto) {
        return ApiResponse.success();
    }

    @ApiOperationSupport(order = 30)
    @Operation(summary = "两张人脸图片比对", description = "")
    @PostMapping("comparison")
    public ApiResponse<String> comparisonFace(FaceComparisonDto faceComparisonDto) {
        return ApiResponse.success();
    }

}
