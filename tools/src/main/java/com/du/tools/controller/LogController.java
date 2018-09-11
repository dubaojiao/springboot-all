package com.du.tools.controller;


import com.du.tools.domain.SysLog;
import com.du.tools.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LogController {
    @Autowired
    LogService logService;

    @GetMapping(value = "getLogList")
    public List<SysLog> getLogList(){
        return logService.getLogList();
    }
    @GetMapping(value = "getLogById")
    public SysLog getLogById(String id){
        return logService.getLogById(id);
    }
}
