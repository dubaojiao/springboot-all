package com.du.ds.service;

import com.du.ds.common.RedisConstant;
import com.du.ds.mapper.one.domain.UserInfo;
import com.du.ds.mapper.one.rep.UserInfoRepos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @Resource(name = "oneUserInfoRepos")
    UserInfoRepos oneUserInfoRepos;

    @Resource(name = "towUserInfoRepos")
    com.du.ds.mapper.tow.rep.UserInfoRepos towUserInfoRepos;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private CountDownLatch latch;

    @Autowired
    public Receiver(CountDownLatch latch) {
        this.latch = latch;
    }

    public void receiveMessage(String message) {
        LOGGER.info("Received <" + message + ">");
        // 使用子线程操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserInfo userInfo = oneUserInfoRepos.findById(Integer.valueOf(message)).get();
                if(userInfo != null){
                    try {
                        towUserInfoRepos.save(userInfo);
                    }catch (Exception ex){
                        //如果同步过程出现异常 重新发生消息
                        ex.printStackTrace();
                        stringRedisTemplate.convertAndSend(RedisConstant.REDIS_MSG_CHANNL,message);
                    }
                }
            }
        }).start();
        latch.countDown();
    }

}
