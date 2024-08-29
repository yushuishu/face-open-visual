package com.shuishu.face.openvisual.server.entity.storage;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-27 23:31
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@Schema(description = "存储引擎类型")
public enum StorageEngine {
    /**
     * 存储引擎类型
     */
    @Schema(description = "当前数据库")
    CURR_DB,
    @Schema(description = "阿里云OSS")
    ALI_OSS,
    @Schema(description = "腾讯云COS")
    TCE_COS,
    @Schema(description = "MinIO")
    MIN_IO;

}
