package com.du.tools.controller;

import com.du.tools.domain.ScanData;
import com.du.tools.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScanController {
    @Autowired
    ScanService scanService;
    @GetMapping(value = "getScanDataList")
    public List<ScanData> getScanDataList(){
        return scanService.getScanDataList();
    }
}
