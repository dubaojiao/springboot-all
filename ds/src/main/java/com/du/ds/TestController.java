package com.du.ds;

import com.du.ds.common.RedisConstant;
import com.du.ds.mapper.one.domain.UserInfo;
import com.du.ds.mapper.one.rep.UserInfoRepos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    Logger logger   = LoggerFactory.getLogger(TestController.class);

    @Resource(name = "oneUserInfoRepos")
    UserInfoRepos oneUserInfoRepos;

    @Resource(name = "towUserInfoRepos")
    com.du.ds.mapper.tow.rep.UserInfoRepos towUserInfoRepos;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //ds: 新加redis消息机制，one表有修改/添加，通过redis消息触发tow表更新
    @GetMapping(value = "test01")
    public String test01(String name,String phone){
        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfo.setPhone(phone);
        userInfo =  oneUserInfoRepos.save(userInfo);

        if(userInfo.getId()>0){
            stringRedisTemplate.convertAndSend(RedisConstant.REDIS_MSG_CHANNL,userInfo.getId().toString());
        }
       /* UserInfo userInfo1 = oneUserInfoRepos.findById(userInfo.getId()).get();
        userInfo1.setId(null);
        userInfo1 = towUserInfoRepos.save(userInfo1);

        UserInfo userInfo2 = oneUserInfoRepos.findById(userInfo1.getId()).get();

        logger.info(userInfo2.toString());*/

        return userInfo.toString();
    }
}
