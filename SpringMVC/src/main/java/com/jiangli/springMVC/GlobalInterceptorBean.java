package com.jiangli.springMVC;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author Jiangli
 * @date 2016/11/11 9:19
 */
public class GlobalInterceptorBean extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ServletContext servletContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("preHandle {}", "before");
        WebUtils.setSessionAttribute(request,"guguday","xiaojixiaoji");
        HttpSession session = request.getSession();
//        request.s
        logger.debug("servletContext {}", servletContext);
        logger.debug("web tempfile {}", servletContext.getAttribute(WebUtils.TEMP_DIR_CONTEXT_ATTRIBUTE));
        logger.debug("web getRealPath {}", WebUtils.getRealPath(servletContext,"rmi"));
        Object getOrCreate = WebUtils.getOrCreateSessionAttribute(session, "getOrCreate", Object.class);
        logger.debug("getOrCreate {}", getOrCreate);
        logger.debug("web getSessionMutex {}", WebUtils.getSessionMutex(session));
        logger.debug("web getSessionMutex {}", WebUtils.getSessionMutex(session));
        logger.debug("web getSessionMutex {}", WebUtils.getSessionMutex(session));
        logger.debug("web isIncludeRequest {}", WebUtils.isIncludeRequest(request));

        Map<String, Object> attrs = new HashedMap();
        attrs.put("aaa", "111");
        attrs.put("bbb", "222");
        WebUtils.exposeRequestAttributes(request,attrs);

        WebUtils.exposeErrorRequestAttributes(request,new Throwable("haha"),"servname" );

//        http://localhost:8080/rmi?javax=1232&javax3=sfdf
//        javax{=1232, 3=sfdf}
        Map<String, Object> javax = WebUtils.getParametersStartingWith(request, "javax");
        logger.debug("web getParametersStartingWith javax:{}", javax);

//      http://localhost:8080/rmi?_pageIndex5
        int pageIndex = WebUtils.getTargetPage(request, "_pageIndex", 1);
        logger.debug("web getTargetPage pageIndex:{}", pageIndex);


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
