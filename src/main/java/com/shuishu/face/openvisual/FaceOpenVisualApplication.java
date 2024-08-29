package com.shuishu.face.openvisual;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-22 12:38
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@SpringBootApplication
@MapperScan("com.shuishu.face.openvisual.server.mapper")
public class FaceOpenVisualApplication {

    public static void main(String[] args) {
        SpringApplication.run(FaceOpenVisualApplication.class, args);
    }

}
