package com.jiangli.websocket.chatroom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * @author Jiangli
 * @date 2017/5/15 16:16
 */
@ServerEndpoint("/chatroom.ws")
public class ChatRoomServerPoint {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private ChatRoom chatRoom(){
        return ChatRoom.i();
    }

    /**
     * 打开连接时触发
     */
    @OnOpen
    public void onOpen(
            Session session){
        log.info("Websocket Start Connecting: ");

        chatRoom().register(session);


    }

    /**
     * 收到客户端消息时触发
     * @return
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("onMessage: {}",message);

        chatRoom().sendMsgToOthers(session,message);
    }

    /**
     * 异常时触发
     */
    @OnError
    public void onError(
            Throwable throwable,
            Session session) {
        log.info("Websocket Connection Exception: ");
        log.info(throwable.getMessage(), throwable);
    }

    /**
     * 关闭连接时触发
     */
    @OnClose
    public void onClose(Session session) {
        log.info("Websocket Close Connection: ");
        chatRoom().unregister(session);
    }
}
