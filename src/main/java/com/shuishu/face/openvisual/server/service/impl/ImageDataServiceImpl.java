package com.shuishu.face.openvisual.server.service.impl;

import com.shuishu.face.openvisual.server.mapper.ImageDataMapper;
import com.shuishu.face.openvisual.server.model.ImageData;
import com.shuishu.face.openvisual.server.service.ImageDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ImageDataServiceImpl implements ImageDataService {

    private final ImageDataMapper imageDataMapper;

    @Override
    public Boolean insert(String table, ImageData record) {
        return imageDataMapper.insert(table, record) > 0;
    }

}
