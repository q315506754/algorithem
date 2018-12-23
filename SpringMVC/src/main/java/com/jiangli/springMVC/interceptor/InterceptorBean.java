package com.jiangli.springMVC.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jiangli
 * @date 2016/11/11 9:19
 */
public class InterceptorBean extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("preHandle {}", "before");
        boolean b = super.preHandle(request, response, handler);
        logger.debug("preHandle {}", "after");
        return b;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.debug("postHandle {}", "before");
        super.postHandle(request, response, handler, modelAndView);
        logger.debug("postHandle {}", "after");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.debug("afterCompletion {}", "before");
        super.afterCompletion(request, response, handler, ex);
        logger.debug("afterCompletion {}", "after");
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("afterConcurrentHandlingStarted {}", "before");
        super.afterConcurrentHandlingStarted(request, response, handler);
        logger.debug("afterConcurrentHandlingStarted {}", "after");
    }

}
