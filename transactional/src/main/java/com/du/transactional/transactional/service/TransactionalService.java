package com.du.transactional.transactional.service;

import com.du.transactional.transactional.domain.User;
import com.du.transactional.transactional.domain.UserImg;
import com.du.transactional.transactional.exception.FunctionException;
import com.du.transactional.transactional.repos.UserImgRepos;
import com.du.transactional.transactional.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TransactionalService {
    @Autowired
    UserRepos userRepos;
    @Autowired
    UserImgRepos userImgRepos;

    @Transactional(rollbackFor = {FunctionException.class})
    public void save() throws Exception{
        User user = new User();
        user.setId(1);
        user.setName("11111");
        userRepos.save(user);
        this.saveImg();
    }


    private void saveImg() throws Exception{
        UserImg userImg = new UserImg();
        userImg.setId(1);
        userImg.setMsg("1111111111111");
        userImgRepos.save(userImg);
        this.saveImg2();
        //throw  new FunctionException("服务器异常");
    }


    private void saveImg2() throws Exception{
        User user = new User();
        user.setId(2);
        user.setName("2222");
        userRepos.save(user);
        //throw  new FunctionException("服务器异常");
    }
}
