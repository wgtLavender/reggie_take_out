package com.queen.config;

import com.queen.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author
 * @date 2022/11/26/19:57
 */
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport{
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("拦截资源{}",registry.toString());
        log.info("开始静态资源");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");

    }

    /**
     * @description 扩展springmvc的消息转换器
     *
     * @author
     * @date 2022/12/6 21:18
     * @param converters
     */


    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter httpMessageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转化器 底层使用jackson 将java对象转成json
        httpMessageConverter.setObjectMapper(new JacksonObjectMapper());
        // 将上述转化器追加到原有转化器中
        converters.add(0,httpMessageConverter);
    }
}
