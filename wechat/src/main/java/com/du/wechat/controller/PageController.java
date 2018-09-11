package com.du.wechat.controller;

import com.du.wechat.util.QRCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping(value = "/")
    public String getLogin(Model model){
        StringBuilder base64 = new StringBuilder("data:image/png;base64,");
        base64.append(QRCodeUtil.zxingCodeCreate("loginC&&A&&&Achttp://192.168.1.105:9901/loging/C&&A&&&Achttp://192.168.1.105:9901/loginC&&A&&&Ac11222",300,300));
        model.addAttribute("zxing", base64);
        model.addAttribute("key", "11222");
        return "login";
    }
}
