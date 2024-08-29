package com.shuishu.face.openvisual.server.service.impl;


import com.shuishu.face.openvisual.server.service.FaceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-27 16:04
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class FaceServiceImpl implements FaceService {

}
