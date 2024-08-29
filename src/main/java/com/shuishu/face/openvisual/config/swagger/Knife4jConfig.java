package com.shuishu.face.openvisual.config.swagger;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-24 9:57
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("开放人脸识别搜索系统API")
                        .version("1.0.0")
                        .description("开放人脸识别搜索系统")
                        .termsOfService("http://127.0.0.1/face")
                        .license(new License().name("Apache 2.0")
                                .url("http://127.0.0.1/face")));
    }

}
