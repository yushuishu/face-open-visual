package com.shuishu.face.openvisual.server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shuishu.face.openvisual.config.base.ApiResponse;
import com.shuishu.face.openvisual.server.entity.dto.*;
import com.shuishu.face.openvisual.server.entity.vo.SampleDataVo;
import com.shuishu.face.openvisual.server.service.SampleDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-28 14:22
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Tag(name = "人脸样本管理")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api/face/basics/sample")
public class SampleDataController {

    private final SampleDataService sampleDataService;

    @ApiOperationSupport(order = 1)
    @Operation(summary = "添加样本", description = "")
    @PostMapping(value = "add")
    public ApiResponse<Boolean> addSample(@RequestBody SampleDataAddDto sampleDataAddDto) {
        return ApiResponse.of(sampleDataService.addSample(sampleDataAddDto));
    }

    @ApiOperationSupport(order = 5)
    @Operation(summary = "更新样本", description = "")
    @PostMapping(value = "update")
    public ApiResponse<Boolean> updateSample(@RequestBody SampleDataUpdateDto sampleDataUpdateDto) {
        return ApiResponse.of(sampleDataService.updateSample(sampleDataUpdateDto));
    }

    @ApiOperationSupport(order = 10)
    @Operation(summary = "删除样本", description = "")
    @PostMapping(value = "delete")
    public ApiResponse<Boolean> deleteSample(@RequestBody SampleDataDeleteDto sampleDataDeleteDto) {
        return ApiResponse.of(sampleDataService.deleteSample(sampleDataDeleteDto));
    }

    @ApiOperationSupport(order = 15)
    @Operation(summary = "查询样本详情", description = "")
    @GetMapping(value = "details")
    public ApiResponse<SampleDataVo> findSampleDetails(SampleDataDetailsDto sampleDataDetailsDto) {
        return ApiResponse.of(sampleDataService.findSampleDetails(sampleDataDetailsDto));
    }

    @ApiOperationSupport(order = 20)
    @Operation(summary = "查询样本分页", description = "")
    @GetMapping(value = "page")
    public ApiResponse<Page<SampleDataVo>> findSamplePage(SampleDataPageDto sampleDataPageDto, Page<SampleDataVo> page) {
        return ApiResponse.of(sampleDataService.findSamplePage(sampleDataPageDto, page));
    }

}
