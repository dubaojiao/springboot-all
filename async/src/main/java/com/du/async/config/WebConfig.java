package com.du.async.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;
import java.io.File;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    private MyAsyncHandlerInterceptor webHandlerInterceptor = new MyAsyncHandlerInterceptor();
    /**
     * 注入拦截器bean
     */
    @Bean
    MyAsyncHandlerInterceptor webHandlerInterceptor(){
        return  webHandlerInterceptor;
    }
    /**
     * 注册 拦截器
     * .addPathPatterns("/seengene/**") 添加拦截路径
     * .excludePathPatterns("/seengene/login") 添加拦截排除路径
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webHandlerInterceptor).excludePathPatterns();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                .allowCredentials(true).maxAge(3600);
    }

    /**
     * 文件上传临时路径
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String location = "D://data//temp"; //D://test//
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        factory.setLocation(location);
        return factory.createMultipartConfig();
    }
}
