package com.shuishu.face.openvisual.server.entity.storage;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;


/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-27 23:31
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Schema(description = "集合添加对象")
public class StorageDataInfo implements Serializable {
    private String imageRawInfo;
    private String imageEbdInfo;
    private String imageFaceInfo;
    private StorageEngine storageEngine;

    public StorageDataInfo(){}

    public StorageDataInfo(StorageEngine storageEngine, String imageRawInfo, String imageEbdInfo, String imageFaceInfo) {
        this.storageEngine = storageEngine;
        this.imageRawInfo = imageRawInfo;
        this.imageEbdInfo = imageEbdInfo;
        this.imageFaceInfo = imageFaceInfo;
    }

    public StorageEngine getStorageEngine() {
        return storageEngine;
    }

    public void setStorageEngine(StorageEngine storageEngine) {
        this.storageEngine = storageEngine;
    }

    public String getImageRawInfo() {
        return imageRawInfo;
    }

    public void setImageRawInfo(String imageRawInfo) {
        this.imageRawInfo = imageRawInfo;
    }

    public String getImageEbdInfo() {
        return imageEbdInfo;
    }

    public void setImageEbdInfo(String imageEbdInfo) {
        this.imageEbdInfo = imageEbdInfo;
    }

    public String getImageFaceInfo() {
        return imageFaceInfo;
    }

    public void setImageFaceInfo(String imageFaceInfo) {
        this.imageFaceInfo = imageFaceInfo;
    }
}
