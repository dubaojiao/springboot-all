package com.du.async.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * @Title
 * @ClassName DemoController
 * @Author jsb_pbk
 * @Date 2018/10/5
 */
@RestController
public class DemoController {

    Logger logger = LoggerFactory.getLogger(DemoController.class);

    @GetMapping(value = "demo01")
    public Callable<String> demo01(){

        Callable<String> callable =  new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(3 * 1000L);
                return "成功";
            }
        };

        return callable;
    }


    @GetMapping(value = "demo02")
    public DeferredResult<String> demo02(){
        logger.info(Thread.currentThread().getName() + "进入executeSlowTask方法");
        return null;
    }

}
