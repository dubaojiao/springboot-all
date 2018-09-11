package com.du.socket.websocket.controller;

import com.du.socket.websocket.service.WebSocketServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class MsgController {

    @GetMapping(value = "index")
    public String index(){
        return "index";
    }


    @RequestMapping(value="/send",method= RequestMethod.GET)
    @ResponseBody
    public  String pushVideoListToWeb(String msg) {
        try {
            WebSocketServer.sendInfo("有新客户呼入,sltAccountId:"+ msg);
            return "成功";
        }catch (IOException e) {
            return "失败";
        }
    }
}
