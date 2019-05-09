package com.jiangli.springMVC.filter;

import com.alibaba.fastjson.JSON;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * http://localhost:80/mv/ABC?a=123
 * http://localhost:80/mv/ABC?a=123&_path_
 *
 * @author Jiangli
 * @date 2019/1/10 19:12
 */
@WebFilter(urlPatterns = "/*")
public class FindPathFilter implements Filter {
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        //servletContext.getAttribute(FrameworkServlet.class.getName() + ".CONTEXT.dispatcher");
        System.out.println("FindPathFilter init...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {

            HttpServletRequest req = (HttpServletRequest) request;
            if (req.getParameter("_path_")!=null) {
                ServletContext servletContext = request.getServletContext();
                //WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                WebApplicationContext context = (WebApplicationContext)servletContext.getAttribute(FrameworkServlet.class.getName() + ".CONTEXT.dispatcher");
                requestMappingHandlerMapping = context.getBean(RequestMappingHandlerMapping.class);

                System.out.println("test:"+req.getRequestURI());
                HandlerExecutionChain exeChain = requestMappingHandlerMapping.getHandler(req);
                if (exeChain!=null) {
                    Object hnd = exeChain.getHandler();
                    //System.out.println(hnd);

                    if (hnd instanceof HandlerMethod) {
                        HandlerMethod handlerMethod = (HandlerMethod) hnd;
                        Class<?> controllerCls = handlerMethod.getBeanType();
                        Method method = handlerMethod.getMethod();

                        Map map = new HashMap();
                        map.put("cls",controllerCls.getCanonicalName());
                        map.put("method",method.getName());

                        HttpServletResponse rep = (HttpServletResponse) response;
                        rep.setContentType("application/json");
                        rep.setCharacterEncoding("utf8");
                        rep.getWriter().write(JSON.toJSON(map).toString());
                        rep.getWriter().flush();

                        return;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
