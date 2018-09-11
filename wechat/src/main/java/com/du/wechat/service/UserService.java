package com.du.wechat.service;

import com.du.wechat.pojo.LoginEntry;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService {
    private final static String KEY = "1122334455";
    public String login(LoginEntry entry) {
        if(KEY.equals(entry.getKey())){
            try {
                WebSocketServer.sendInfoByKey(entry.getTarget(),"##:登陆成功");
                return "成功";
            } catch (IOException e) {
                e.printStackTrace();
                return "失败";
            }
        }else {
            return "失败";
        }
    }
}
