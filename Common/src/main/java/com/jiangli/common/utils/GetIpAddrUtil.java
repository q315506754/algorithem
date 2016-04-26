package com.jiangli.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;


public class GetIpAddrUtil {
	
	private static final Log logger = LogFactory.getLog("track");
	public static String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static String getIp(HttpServletRequest req) {
		String ip = req.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("127.0.0.1") ||"unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("127.0.0.1") || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("127.0.0.1") || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		return ip;
	}
	
	
//	public static void printAddInfo(String orderId,String type,HttpServletRequest request){
//		try {
//			logger.info("------------------------------orderId :"+orderId+"-------------------------------START!!!!!");
//			logger.info("nnnnnnnnnnnnnn--------"+type+" getRemortIP :IP address ============="+getRemortIP(request)+"!orderId:"+orderId);
//			logger.info("nnnnnnnnnnnnnn--------"+type+" getIpAddr :IP address ============="+getIpAddr(request)+"!orderId:"+orderId);
//			logger.info("rotocol: " + request.getProtocol()); 
//			logger.info("Scheme: " + request.getScheme()); 
//			logger.info("Server Name: " + request.getServerName() ); 
//			logger.info("Server Port: " + request.getServerPort()); 
//			logger.info("rotocol: " + request.getProtocol()); 
//			logger.info("Remote Addr: " + request.getRemoteAddr()); 
//			logger.info("Remote Host: " + request.getRemoteHost()); 
//			logger.info("Character Encoding: " + request.getCharacterEncoding()); 
//			logger.info("Content Length: " + request.getContentLength()); 
//			logger.info("Content Type: "+ request.getContentType()); 
//			logger.info("Auth Type: " + request.getAuthType()); 
//			logger.info("HTTP Method: " + request.getMethod()); 
//			logger.info("ath Info: " + request.getPathInfo()); 
//			logger.info("ath Trans: " + request.getPathTranslated()); 
//			logger.info("Query String: " + request.getQueryString()); 
//			logger.info("Remote User: " + request.getRemoteUser()); 
//			logger.info("Session Id: " + request.getRequestedSessionId()); 
//			logger.info("Request URI: " + request.getRequestURI()); 
//			logger.info("Servlet Path: " + request.getServletPath()); 
//			logger.info("Accept: " + request.getHeader("Accept")); 
//			logger.info("Host: " + request.getHeader("Host")); 
//			logger.info("Referer : " + request.getHeader("Referer")); 
//			logger.info("Accept-Language : " + request.getHeader("Accept-Language")); 
//			logger.info("Accept-Encoding : " + request.getHeader("Accept-Encoding")); 
//			logger.info("User-Agent : " + request.getHeader("User-Agent")); 
//			logger.info("Connection : " + request.getHeader("Connection")); 
//			logger.info("Cookie : " + request.getHeader("Cookie")); 
//			logger.info("------------------------------orderId :"+orderId+"-------------------------------END!!!!!");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	} 
	
	public static boolean vailIp(HttpServletRequest request){
		if(getIpAddr(request).trim().equals("58.246.134.82")){
			logger.info(getIpAddr(request)+"-------本地IP执行链接,已通过!");
			return true;
		}
		logger.info(getIpAddr(request)+"---------此Ip在执行action,已被禁用");
		return false;
		
	}

}
