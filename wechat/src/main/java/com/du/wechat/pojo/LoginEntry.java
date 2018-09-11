package com.du.wechat.pojo;

import java.io.Serializable;

public class LoginEntry implements Serializable {
    private String key;
    private String target;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
