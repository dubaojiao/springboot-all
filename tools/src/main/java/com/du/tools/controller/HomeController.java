package com.du.tools.controller;


import com.du.tools.domain.HomeData;
import com.du.tools.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {
    @Autowired
    HomeService homeService;

    @GetMapping(value = "getHomeDataList")
    public List<HomeData> getHomeDataList(){
        return homeService.getHomeDataList();
    }
}
