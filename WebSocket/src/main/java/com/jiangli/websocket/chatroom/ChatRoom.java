package com.jiangli.websocket.chatroom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiangli
 * @date 2017/5/15 16:20
 */
public class ChatRoom {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static final Map<String,Session> clients = new HashMap<>();
    private static ChatRoom instance = new ChatRoom();
    public static final ChatRoom i(){
        return instance;
    }

    public void register(Session session){
        String id = id(session);
        clients.put(id,session);
        log.info("register sessionid {}:",session);
    }
    public void unregister(Session session){
        String id = id(session);
        clients.remove(id);
        log.info("unregister sessionid {}:",session);
    }

    public void sendMsgToOthers(Session session,String msg){
        String id = id(session);
        for (Map.Entry<String, Session> e : clients.entrySet()) {
            String key = e.getKey();
//            if (key.equals(id)) {
//                continue;
//            }
            Session value = e.getValue();
            String valueId = id(value);
            log.info("msg from {} - >{} :{}",new String[]{id,valueId,msg});

            try {
                value.getBasicRemote().sendText(msg);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        clients.put(id,session);
        log.info("register sessionid {}:",session);
    }

    private String id(Session session) {
        if (session!=null) {
            return session.getId();
        }
        return null;
    }
}
