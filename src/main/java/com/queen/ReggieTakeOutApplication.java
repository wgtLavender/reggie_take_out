package com.queen;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @description
 *
 * @author
 * @date 2022/12/7 21:40
 * @param null 
 * @return null      
 */
@SpringBootApplication
@MapperScan("com.queen.dao")
@ServletComponentScan
@Slf4j
public class ReggieTakeOutApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieTakeOutApplication.class, args);

        log.info("项目启动成功...");
    }

}
