package com.shuishu.face.openvisual.server.service.impl;

import com.shuishu.face.openvisual.server.entity.storage.StorageDataInfo;
import com.shuishu.face.openvisual.server.entity.storage.StorageImageInfo;
import com.shuishu.face.openvisual.server.service.StorageImageServiceDataBaseService;
import org.springframework.stereotype.Service;

@Service("visualStorageImageServiceDataBase")
public class StorageImageServiceDataBaseServiceImpl implements StorageImageServiceDataBaseService {

    @Override
    public StorageDataInfo create(StorageImageInfo storageImageInfo) {
        return new StorageDataInfo(
                storageImageInfo.getStorageEngine(),
                storageImageInfo.getImageRawInfo(),
                storageImageInfo.getImageEbdInfo(),
                storageImageInfo.getImageFaceInfo()
        );
    }

}
