package com.shuishu.face.openvisual.server.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shuishu.face.openvisual.config.base.ApiResponse;
import com.shuishu.face.openvisual.server.entity.dto.FaceSearchDto;
import com.shuishu.face.openvisual.server.entity.vo.FaceSearchVo;
import com.shuishu.face.openvisual.server.service.FaceSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-28 19:53
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Tag(name = "人脸搜索")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api/face/basics/search")
public class FaceSearchController {

    private final FaceSearchService faceSearchService;

    @ApiOperationSupport(order = 1)
    @Operation(summary = "人脸搜索M:N", description = "")
    @PostMapping(value = "face/detail")
    public ApiResponse<List<FaceSearchVo>> findFaceRecognition(@RequestBody FaceSearchDto faceSearchDto) {
        return ApiResponse.of(faceSearchService.findFaceRecognition(faceSearchDto));
    }

}
