package com.jiangli.websocket;

import com.jiangli.common.utils.ClassDescribeUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Jiangli
 * @date 2017/5/15 13:15
 */
@ServerEndpoint("/websocketpath.ws")
public class TomcatServerPoint {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * 打开连接时触发
     * @param relationId
     * @param userCode
     * @param session
     */
    @OnOpen
    public void onOpen(
                       Session session){
        log.info("Websocket Start Connecting: ");
//        session.addMessageHandler(new MessageHandler.Whole<ByteBuffer>() {
//            @Override
//            public void onMessage(ByteBuffer message) {
//                log.info("Websocket onMessage InputStream");
//            }
//        });
    }

    /**
     * 收到客户端消息时触发
     * @param relationId
     * @param userCode
     * @param message
     * @return
     */
    @OnMessage
    public String onMessage(String message, Session session) {
        log.info("onMessage: "+message);
        log.info("handleMessage:"+session);
        try {
            log.info("handleMessage:"+ BeanUtils.describe(session));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Got your message (" + message + ").Thanks !";
    }
//    @OnMessage
//    public ByteBuffer onMessage(ByteBuffer bf, Session session) {
//        log.info("onMessage: "+bf);
//        return bf;
//    }

    /**
     * 异常时触发
     * @param relationId
     * @param userCode
     * @param session
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
     * @param relationId
     * @param userCode
     * @param session
     */
    @OnClose
    public void onClose(
                        Session session) {
        log.info("Websocket Close Connection: ");
    }
}
