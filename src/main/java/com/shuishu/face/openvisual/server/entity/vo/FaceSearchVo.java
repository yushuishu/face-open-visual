package com.shuishu.face.openvisual.server.entity.vo;


import com.shuishu.face.openvisual.server.entity.extend.FaceLocation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-28 19:59
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@ToString
@Schema(description = "人脸数据搜索对象")
public class FaceSearchVo {

    /**
     * 人脸位置信息
     **/
    @Schema(description = "人脸位置信息")
    private FaceLocation location;
    /**
     * 人脸质量分数
     **/
    @Schema(description = "人脸分数:[0,100]")
    private Float faceScore;
    /**
     * 匹配的人脸列表
     **/
    @Schema(description = "匹配的人脸列表")
    private List<SampleFaceVo> match = new ArrayList<>();

    public static FaceSearchVo build() {
        return new FaceSearchVo();
    }

    public FaceLocation getLocation() {
        return location;
    }

    public void setLocation(FaceLocation location) {
        this.location = location;
    }

    public Float getFaceScore() {
        return faceScore;
    }

    public void setFaceScore(Float faceScore) {
        this.faceScore = faceScore;
    }

    public List<SampleFaceVo> getMatch() {
        return match;
    }

    public void setMatch(List<SampleFaceVo> match) {
        this.match = match;
    }
}
