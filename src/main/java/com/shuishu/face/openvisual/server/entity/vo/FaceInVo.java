package com.shuishu.face.openvisual.server.entity.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.shuishu.face.openvisual.server.entity.po.FaceIn;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.File;


/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-28 22:23
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "人脸信息Vo")
public class FaceInVo extends FaceIn {
    private Float similarity;

    @JsonIgnore
    private File originalFile;

    @JsonIgnore
    private File cropFile;

    public Float getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Float similarity) {
        this.similarity = similarity;
    }

    public File getOriginalFile() {
        return originalFile;
    }

    public void setOriginalFile(File originalFile) {
        this.originalFile = originalFile;
    }

    public File getCropFile() {
        return cropFile;
    }

    public void setCropFile(File cropFile) {
        this.cropFile = cropFile;
    }

    @Override
    public String toString() {
        return "FaceInVO{" +
                "similarity=" + similarity +
                ", originalFile=" + originalFile +
                ", cropFile=" + cropFile +
                '}';
    }

}
