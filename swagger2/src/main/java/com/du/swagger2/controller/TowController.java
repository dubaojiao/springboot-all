package com.du.swagger2.controller;

import com.du.swagger2.pojo.ApiResult;
import com.du.swagger2.pojo.UserData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Api(value = "TowController",description = "第二个控制器",basePath="/tow/",tags={"TowController"})
@RestController
@RequestMapping("/tow/")
public class TowController {
    private static final Logger LOG = LoggerFactory.getLogger(TowController.class);


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

    @ApiOperation(value = "post接口",notes = "用户信息显示")
    @ApiImplicitParam(name = "obj",  required = true, dataType = "UserData")
    @PostMapping(value = "towPost")
    public ApiResult towPost(@RequestBody UserData data){

        return ApiResult.returnSuccess("成功",data);
    }

}
