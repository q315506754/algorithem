package com.jiangli.springMVC.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

/**
 * @author Jiangli
 * @date 2018/12/26 15:07
 */
public class BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected ThreadLocal<HttpSession> threadLocalSession=new ThreadLocal<>();

    @ModelAttribute
    public void rejectSession(HttpSession httpSession){
        threadLocalSession.set(httpSession);
        logger.debug("BaseController.rejectSession:{}",threadLocalSession);
    }


}
