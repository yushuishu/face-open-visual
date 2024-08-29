package com.shuishu.face.openvisual.server.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shuishu.face.openvisual.config.base.ApiResponse;
import com.shuishu.face.openvisual.server.entity.dto.CollectAddDto;
import com.shuishu.face.openvisual.server.entity.dto.CollectDeleteDto;
import com.shuishu.face.openvisual.server.entity.dto.CollectDetailsDto;
import com.shuishu.face.openvisual.server.entity.dto.CollectListDto;
import com.shuishu.face.openvisual.server.entity.vo.CollectVo;
import com.shuishu.face.openvisual.server.service.CollectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-27 17:09
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Tag(name = "集合(数据库)管理")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api/face/basics/collect")
public class CollectController {

    private final CollectService collectService;

    @ApiOperationSupport(order = 1)
    @Operation(summary = "添加集合(数据库)", description = "")
    @PostMapping(value = "add")
    public ApiResponse<Boolean> addCollect(@RequestBody CollectAddDto collectAddDto) {
        return ApiResponse.of(collectService.addCollect(collectAddDto));
    }

    @ApiOperationSupport(order = 5)
    @Operation(summary = "删除集合", description = "根据命名空间，集合名称删除集合")
    @GetMapping(value = "delete")
    public ApiResponse<Boolean> deleteCollect(CollectDeleteDto collectDeleteDto) {
        return ApiResponse.of(collectService.deleteCollect(collectDeleteDto));
    }

    @ApiOperationSupport(order = 10)
    @Operation(summary = "查询集合详情", description = "根据命名空间，集合名称查看集合信息")
    @GetMapping(value = "details")
    public ApiResponse<CollectVo> findCollectDetails(CollectDetailsDto collectDetailsDto) {
        return ApiResponse.of(collectService.findCollectDetails(collectDetailsDto));
    }

    @ApiOperationSupport(order = 15)
    @Operation(summary = "查询集合列表", description = "根据命名空间查询集合列表")
    @GetMapping(value = "list")
    public ApiResponse<List<CollectVo>> findCollectList(CollectListDto collectListDto) {
        return ApiResponse.of(collectService.findCollectList(collectListDto));
    }


}
