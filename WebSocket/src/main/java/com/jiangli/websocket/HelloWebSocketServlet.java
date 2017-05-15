package com.jiangli.websocket;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/hellowebsocket.ws")
public class HelloWebSocketServlet extends WebSocketServlet {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private static List<MessageInbound> socketList = new ArrayList<MessageInbound>();

    protected StreamInbound createWebSocketInbound(String subProtocol,HttpServletRequest request){
        return new WebSocketMessageInbound();
    }

    public class WebSocketMessageInbound extends MessageInbound {
        protected void onClose(int status){
            log.info("onClose: "+status);
            super.onClose(status);
            socketList.remove(this);            
        }
        protected void onOpen(WsOutbound outbound){
            log.info("Websocket Start Connecting: ");
            super.onOpen(outbound);
            socketList.add(this);
        }
        @Override
        protected void onBinaryMessage(ByteBuffer message) throws IOException {
            log.info("onBinaryMessage: "+message);
        }

        @Override
        protected void onTextMessage(CharBuffer message) throws IOException {
            log.info("onTextMessage: "+message);
            for(MessageInbound messageInbound : socketList){
                CharBuffer buffer = CharBuffer.wrap(message);
                WsOutbound outbound = messageInbound.getWsOutbound();
                outbound.writeTextMessage(buffer);
                outbound.flush();               
            }
        }
    }
}