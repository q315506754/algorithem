package com.jiangli.springboot.controller;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Jiangli
 * @date 2017/2/27 14:06
 */
@Component
//public class MyFilter{


public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("filter before...");

        filterChain.doFilter(servletRequest, servletResponse);

        System.out.println("filter after...");
    }

    @Override
    public void destroy() {

    }


}
