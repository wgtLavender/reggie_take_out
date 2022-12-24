package com.queen.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author
 * @date 2022/11/27/19:03
 */

@Configuration
public class MybatisPlusPagesConfig {


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        PaginationInnerInterceptor interceptor = new PaginationInnerInterceptor();
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(interceptor);
        return mybatisPlusInterceptor;

    }
}
