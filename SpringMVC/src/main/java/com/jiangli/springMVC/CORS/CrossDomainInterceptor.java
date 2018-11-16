package com.jiangli.springMVC.CORS;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 跨域请求拦截器，用于解决Ajax请求跨域问题
 * @author	zhanglikun
 * @date	2015年7月28日 下午2:39:58
 */
public class CrossDomainInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
      crossDomain(request ,response) ;
		return super.preHandle(request, response, handler);
	}
	
//	@Override
//	public void postHandle(HttpServletRequest request,
//			HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		crossDomain(request ,response) ;
//		super.postHandle(request, response, handler, modelAndView);
//	}
	
	private void crossDomain(HttpServletRequest request,
			HttpServletResponse response) throws MalformedURLException {
		String referer = request.getHeader("Referer");
		URL u = new URL(referer);
		String host = u.getHost().toLowerCase();
		// 如果域名包含.zhihuishu.com，则允许其跨域
		if(StringUtils.contains(host, ".zhihuishu.com")) {
//			response.setHeader("Access-Control-Allow-Origin", referer);
			response.setHeader("Access-Control-Allow-Origin", "*");
		    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		    response.setHeader("Access-Control-Max-Age", "3600");
		    response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		}
	}
	
}
