package com.jiangli.websocket.chatroom;

import net.sf.json.JSONObject;
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

        JSONObject obj = new JSONObject();
        obj.put("userId",id);
        sendMsg(session,obj);

        log.info("register sessionid {}:",session);

        refreshUserList();
    }

    private void refreshUserList() {
        JSONObject userList = new JSONObject();
        userList.put("type", "refresh");
        userList.put("data", clients.keySet());
        sendMsgTo(null,userList,true);
    }

    public void unregister(Session session){
        String id = id(session);
        clients.remove(id);
        log.info("unregister sessionid {}:",session);

        refreshUserList();
    }

    public void sendMsg(Session session,String msg){
        try {
            session.getBasicRemote().sendText(msg);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    public void sendMsg(Session session,JSONObject msg){
        sendMsg(session, msg.toString());
    }

    public void sendMsgTo(Session session,String msg,boolean includeSelf){
        String id = id(session);
        for (Map.Entry<String, Session> e : clients.entrySet()) {
            String key = e.getKey();
            if(!includeSelf){
                if (key.equals(id)) {
                    continue;
                }
            }
            Session value = e.getValue();
            String valueId = id(value);
            log.info("msg from {} - >{} :{}",new String[]{id,valueId,msg});

            sendMsg(value,msg);
        }
    }
    public void sendMsgTo(Session session,JSONObject msg,boolean includeSelf){
        sendMsgTo(session, msg.toString(), includeSelf);
    }

    public void sendMsgToOthers(Session session,String msg){
        sendMsgTo(session, msg, false);
    }
    public void sendMsgToOthers(Session session,JSONObject msg){
        sendMsgTo(session, msg, false);
    }

    private String id(Session session) {
        if (session!=null) {
            return session.getId();
        }
        return null;
    }
}
