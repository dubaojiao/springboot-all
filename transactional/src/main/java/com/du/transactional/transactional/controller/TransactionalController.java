package com.du.transactional.transactional.controller;

import com.du.transactional.transactional.service.TransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionalController {
    @Autowired
    TransactionalService transactionalService;
    @GetMapping(value = "save")
    public String save(){
        try {
            transactionalService.save();
            return "成功";
        }catch (Exception e){
            return "失败";
        }

    }
}
