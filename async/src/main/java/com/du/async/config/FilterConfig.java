package com.du.async.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Title
 * @ClassName FilterConfig
 * @Author jsb_pbk
 * @Date 2018/10/5
 */
//@WebFilter(urlPatterns = "/demo01", asyncSupported = true)
//@Order(1)//指定过滤器的执行顺序,值越大越靠后执行
public class FilterConfig implements Filter {
    Logger logger = LoggerFactory.getLogger(FilterConfig.class);
    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
        logger.info("init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("doFilter");
    }

    @Override
    public void destroy() {
        logger.info("destroy");
    }
}
