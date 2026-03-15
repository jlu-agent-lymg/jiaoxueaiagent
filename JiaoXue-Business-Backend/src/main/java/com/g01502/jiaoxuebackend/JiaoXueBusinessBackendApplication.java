package com.g01502.jiaoxuebackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 业务后端独立启动类。
 */
@SpringBootApplication
@MapperScan("com.g01502.jiaoxuebackend.mapper")
public class JiaoXueBusinessBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(JiaoXueBusinessBackendApplication.class, args);
    }
}
