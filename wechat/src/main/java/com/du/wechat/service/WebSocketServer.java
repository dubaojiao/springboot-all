package com.du.wechat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket/{key}")
@Component
public class WebSocketServer {
    static   Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    private static Map<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<String,WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String key;

      /**连接建立成功调用的方法*/
      @OnOpen
     public void onOpen(@PathParam("key") String key, Session session) {
          if(key == null || key == ""){
            throw new RuntimeException("链接异常");
          }
          this.key = key;
          this.session =session;
          webSocketMap.put(key,this);
          addOnlineCount();
          System.out.println("Connected ... " + key);
      }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        webSocketMap.remove(this.key);
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
        if(message.contains("__")){
            String[] msg = message.split("__");
            if(msg.length != 2){
                return;
            }
           WebSocketServer webSocketServer =  webSocketMap.get(msg[1]);
            if(webSocketServer == null){
                return ;
            }
            try {
                String m = "loginC&&A&&&Achttp://192.168.1.105:9901/loginC&&A&&&Ac11222";
                webSocketServer.sendMessage(m);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //群发消息
        /*webSocketMap.forEach((k,v) -> {
            try {
                v.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });*/
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }


   public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     * */
    public static void sendInfoByKey(String key,String message) throws IOException {
        log.info(message);
       WebSocketServer webSocketServer =   webSocketMap.get(key);
       if(webSocketServer == null){
            return ;
       }
        try {
            webSocketServer.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*webSocketMap.forEach((k,v) -> {
            try {
                v.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });*/
    }


    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message) throws IOException {
        log.info(message);
        webSocketMap.forEach((k,v) -> {
            try {
                v.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
