package com.jiangli.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author Jiangli
 * @date 2017/5/15 15:24
 */
@Configuration
//@EnableWebMvc
@EnableWebSocket
public class SpringWebSocket extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    @Autowired
    SpringWebSocketHandler handler;

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //不能加ws后缀不知道为什么
        registry.addHandler(handler, "/springws");
        System.out.println("registerWebSocketHandlers..."+systemWebSocketHandler());
        System.out.println("registerWebSocketHandlers..."+handler);
//        registry.addHandler(handler, "/springws").addInterceptors(new HandShake()).setAllowedOrigins("*");
        registry.addHandler(handler, "/springws/sockjs").withSockJS();
    }

    @Bean
    public SpringWebSocketHandler systemWebSocketHandler(){
        return new SpringWebSocketHandler();
    }

}
