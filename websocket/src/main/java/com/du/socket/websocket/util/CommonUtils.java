package com.du.socket.websocket.util;

import java.util.Map;

public class CommonUtils {
    public static Object  getValue(Map map,String name){
        if(map == null || map.isEmpty()){
            return null;
        }
        return map.get(name);
    }
}
