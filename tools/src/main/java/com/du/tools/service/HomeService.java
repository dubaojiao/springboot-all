package com.du.tools.service;

import com.du.tools.domain.HomeData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeService {
    private List<HomeData> homeDataList;

    public List<HomeData> getHomeDataList() {
        System.out.println("getHomeDataList");
        homeDataList = new ArrayList<>();
        HomeData h5 = new HomeData();
        h5.setId(5);
        h5.setTitle("不知道的汽车");
        h5.setDescription("就是生活时尚莎莎看书撒看见睡觉咯撒萨科");
        h5.setNewFlag(1);
        h5.setImg("http://file.youboy.com/d/154/47/70/3/593743.jpg");

        HomeData h1 = new HomeData();
        h1.setId(1);
        h1.setTitle("野马汽车");
        h1.setDescription("就是生活时尚莎莎看书撒看见睡觉咯撒萨科");
        h1.setNewFlag(1);
        h1.setImg("http://5.26923.com/download/pic/000/323/7d46c783cb8575a51eab5e66818d6741.jpg");

        HomeData h2 = new HomeData();
        h2.setId(2);
        h2.setTitle("法拉利");
        h2.setDescription("就是生活时尚莎莎看书撒看见睡觉咯撒萨科");
        h2.setNewFlag(0);
        h2.setImg("http://img.kutoo8.com/upload/image/90176856/20130509023742424_960x540.jpg");


        HomeData h3 = new HomeData();
        h3.setId(3);
        h3.setTitle("保时捷");
        h3.setDescription("就是生活时尚莎莎看书撒看见睡觉咯撒萨科");
        h3.setNewFlag(1);
        h3.setImg("http://www.ckfct.com/upload/img/o9De1YRIkoaw3hfPpivZNZviLtdW6bpBqmU0VX4CEREFFM6DXW56Jw/WcvWYq2a4f6LJREPAhsJmUllszVl3U9lh4XrB4v06HowFT09t/Cm47M1E.jpg");

        HomeData h4 = new HomeData();
        h4.setId(4);
        h4.setTitle("不知道的汽车");
        h4.setDescription("就是生活时尚莎莎看书撒看见睡觉咯撒萨科");
        h4.setNewFlag(1);
        h4.setImg("http://file.youboy.com/d/154/47/70/3/593743.jpg");

        homeDataList.add(h5);
        homeDataList.add(h1);
        homeDataList.add(h2);
        homeDataList.add(h3);
        homeDataList.add(h4);

        return homeDataList;
    }
}
