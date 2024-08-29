package com.shuishu.face.openvisual.server.service;


import com.shuishu.face.openvisual.server.entity.storage.StorageDataInfo;
import com.shuishu.face.openvisual.server.entity.storage.StorageEngine;
import com.shuishu.face.openvisual.server.entity.storage.StorageImageInfo;
import com.shuishu.face.openvisual.server.service.impl.StorageImageServiceDataBaseServiceImpl;
import com.shuishu.face.openvisual.utils.SpringUtils;

public interface StorageImageServiceDataBaseService {

    StorageDataInfo create(StorageImageInfo storageImageInfo);

    public static class Factory {
        private static StorageImageServiceDataBaseService get(StorageEngine storageEngine) {
            switch (storageEngine) {
                case CURR_DB:
                    return SpringUtils.getBean(StorageImageServiceDataBaseServiceImpl.class);
                default:
                    return null;
            }
        }

        public static StorageDataInfo create(StorageImageInfo storageImageInfo) {
            return get(storageImageInfo.getStorageEngine()).create(storageImageInfo);
        }

    }
}
