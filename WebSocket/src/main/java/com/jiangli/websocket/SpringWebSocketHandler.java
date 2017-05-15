package com.jiangli.websocket;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author Jiangli
 * @date 2017/5/15 15:24
 */
@Component
public class SpringWebSocketHandler implements WebSocketHandler {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        log.info("afterConnectionEstablished");
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        log.info("handleMessage:"+webSocketSession);
        try {
            log.info("handleMessage:"+ BeanUtils.describe(webSocketSession));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Object payload = webSocketMessage.getPayload();
        log.info("handleMessage:"+ payload);
        log.info("handleMessage:"+ payload.getClass());
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        log.info("handleTransportError");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        log.info("afterConnectionClosed");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
