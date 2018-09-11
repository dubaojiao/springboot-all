package com.du.swagger2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/while/")
public class WhileController {

    @GetMapping(value = "test1")
    public String test1(Integer num){
        int x = 0;
        while (num == 2 || x == 10){
            System.out.println("-----------");
            try {
                Thread.sleep(5000);
                x++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "完成";
    }

}
