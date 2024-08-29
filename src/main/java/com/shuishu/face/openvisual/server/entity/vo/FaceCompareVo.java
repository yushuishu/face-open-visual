package com.shuishu.face.openvisual.server.entity.vo;


import com.shuishu.face.openvisual.server.entity.extend.CompareFace;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-28 21:34
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@Schema(description = "人脸比对Vo")
public class FaceCompareVo {
    /**
     * 向量的距离
     **/
    @Schema(description = "向量欧式距离:>=0")
    private Float distance;
    /**
     * 转换后的置信度
     **/
    @Schema(description = "余弦距离转换后的置信度:[-100,100]，值越大，相似度越高。")
    private Float confidence;
    /**
     * 人脸信息
     **/
    @Schema(description = "人脸信息,参数needFaceInfo=false时，值为null")
    private CompareFace faceInfo;

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Float getConfidence() {
        return confidence;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    public CompareFace getFaceInfo() {
        return faceInfo;
    }

    public void setFaceInfo(CompareFace faceInfo) {
        this.faceInfo = faceInfo;
    }

    @Override
    public String toString() {
        return "FaceCompareVo{" +
                "distance=" + distance +
                ", confidence=" + confidence +
                ", faceInfo=" + faceInfo +
                '}';
    }

}
