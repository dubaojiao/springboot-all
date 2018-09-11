package com.du.swagger2.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(value = "OneController",description = "第一个控制器",basePath="one",tags={"OneController"})
@RestController
@RequestMapping("/one/")
public class OneController {
    private static final Logger LOG = LoggerFactory.getLogger(OneController.class);


    @ApiOperation(value = "第一个接口",notes = "用来显示用户输入的信息")
    @ApiImplicitParam(name = "msg",value = "XXX消息")
    @SuppressWarnings("unchecked")
    @GetMapping(value = "s1")
    public String one(String msg){
        return "您说了"+msg;
    }

    @ApiOperation(value = "第二个接口",notes = "用来显示用户输入的信息222")
    @ApiImplicitParam(name = "msg",value = "22XXX消息")
    @SuppressWarnings("unchecked")
    @GetMapping(value = "s2")
    public String tow(String msg){
        return "您说了"+msg;
    }
}
