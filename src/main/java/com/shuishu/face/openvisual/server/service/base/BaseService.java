package com.shuishu.face.openvisual.server.service.base;

import org.springframework.util.DigestUtils;

import java.util.UUID;

public abstract class BaseService {
    public static final  String TABLE_PREFIX = "visual";
    public static final  String CHAR_UNDERLINE = "_";

    public String uuid(){
        String uuidStr = UUID.randomUUID().toString() + "|_|" + UUID.randomUUID().toString();
        return DigestUtils.md5DigestAsHex(uuidStr.getBytes());
    }

}
