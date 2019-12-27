package com.jiangli.springMVC.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 *
 * 401
 * http认证
 */
@Component
public class AuthLoginInterceptor extends HandlerInterceptorAdapter {
    private String username="root";
    private String password="java1234";

    @Override
	public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse resp, Object handler) throws Exception {
        String authValue = req.getHeader("Authorization");

        String[] ss= null;
        if(authValue!=null){
            int sepeIndex = authValue.toUpperCase().indexOf("BASIC ");
            String b64UserAndPwd = authValue.substring(sepeIndex + "BASIC ".length());
            ss = new String(Base64.getDecoder().decode(b64UserAndPwd)).split(":");
        }

        if (ss != null &&ss.length>1 ) {
            String name=ss[0];
            String word=ss[1];
            if(username.equals(name)&&password.equals(word)){
                return true;
            }
        }

        resp.setStatus(401);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
        //每分钟认证一次
        resp.addHeader("WWW-Authenticate", "Basic realm=liveapi"+sdf.format(new Date()));
        String errMsg = "<center><font size=2><b>wrong ticket</b></font></center>";
        resp.getWriter().println(errMsg);

        return false;
		//return super.preHandle(request, response, handler);
	}



}
