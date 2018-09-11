package com.du.wechat.controller;

import com.du.wechat.pojo.LoginEntry;
import com.du.wechat.service.UserService;
import com.du.wechat.service.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "loging/{key}")
    public Object  sendLogin(@PathVariable("key") String key){
        try {
            WebSocketServer.sendInfoByKey(key,"@@:等待手机登陆确认");
            Map m = new HashMap(1);
            m.put("data","成功");
            return m;
        } catch (IOException e) {
            e.printStackTrace();
            return "失败";
        }
    }

    @PostMapping(value = "login")
    public Object  login(@RequestBody LoginEntry entry){
        Map m = new HashMap(1);
        m.put("data",userService.login(entry));
        return m ;
    }
}
