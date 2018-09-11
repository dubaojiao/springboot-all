package com.du.socket.websocket.pojo;

import java.io.Serializable;
import java.util.List;

public class OnlineUser implements Serializable {
    private Integer userId;
    private List<Integer> flowIds;
    private Integer storeId;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getFlowIds() {
        return flowIds;
    }

    public void setFlowIds(List<Integer> flowIds) {
        this.flowIds = flowIds;
    }
}
