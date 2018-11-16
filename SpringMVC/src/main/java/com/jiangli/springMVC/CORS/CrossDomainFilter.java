//package com.jiangli.springMVC.filter;
//
//import com.wanda.common.constants.AuthenticationConstants;
//import com.wanda.common.gmp.web.filter.VersionDispatchFilter;
//import com.wanda.common.web.config.HttpConfig;
//import lombok.extern.log4j.Log4j2;
//import org.apache.commons.lang.StringUtils;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//import javax.servlet.http.HttpServletResponse;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Enumeration;
//import java.util.Vector;
//
//@Log4j2
//public final class CrossDomainFilter implements Filter {
//	private static final String VAL_ACCESS_CONTROL_ALLOW_ORIGIN = "*";
//	private static final String VAL_ACCESS_CONTROL_ALLOW_HEADERS = new StringBuilder(
//			"Origin,X-Requested-With,Content-Type,Accept")
//			.append("," + AuthenticationConstants.X_AUTH_TOKEN)
//			.append("," + VersionDispatchFilter.HEADER_APP_VERSION)
//			.toString();
//	private static final String VAL_ACCESS_CONTROL_ALLOW_METHODS = "GET,POST,PUT,DELETE,OPTIONS";
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//
//	}
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletResponse httpResponse = (HttpServletResponse) response;
//		HttpServletRequest httpRequest = (HttpServletRequest) request;
//
//		httpResponse.addHeader(HttpConfig.HeaderConfig.ACCESS_CONTROL_ALLOW_ORIGIN, VAL_ACCESS_CONTROL_ALLOW_ORIGIN);
//		httpResponse.addHeader(HttpConfig.HeaderConfig.ACCESS_CONTROL_ALLOW_HEADERS, VAL_ACCESS_CONTROL_ALLOW_HEADERS);
//		httpResponse.addHeader(HttpConfig.HeaderConfig.ACCESS_CONTROL_ALLOW_METHODS, VAL_ACCESS_CONTROL_ALLOW_METHODS);
//
//		if ("application/x-www-form-urlencoded".equals(httpRequest.getHeader("content-type"))) {
//			httpRequest = new CrossRequestWrapper(httpRequest);
//		}
//
//		try {
//			if ("get".equals(httpRequest.getMethod().toLowerCase()) && StringUtils.isNotBlank(httpRequest.getParameter(AuthenticationConstants.X_AUTH_TOKEN))) {
//				httpRequest.setAttribute(AuthenticationConstants.X_AUTH_TOKEN, httpRequest.getParameter(AuthenticationConstants.X_AUTH_TOKEN).trim());
//			}
//		} catch (Throwable e) {
//			//log.error("", e);
//			//}
//			chain.doFilter(httpRequest, response);
//		}
//
//		@Override
//		public void destroy () {
//
//		}
//
//		private class CrossRequestWrapper extends HttpServletRequestWrapper {
//			private CrossRequestWrapper(HttpServletRequest httpRequest) {
//				super(httpRequest);
//			}
//
//			@Override
//			public String getHeader(String name) {
//				if ("content-type".equals(name.toLowerCase())) {
//					return "application/json";
//				}
//				return super.getHeader(name);
//			}
//
//			@Override
//			public Enumeration<String> getHeaders(String name) {
//				if ("content-type".equals(name.toLowerCase())) {
//					return new Vector<String>(Arrays.asList("application/json")).elements();
//				}
//				return super.getHeaders(name);
//			}
//
//
//			public Enumeration<String> getHeaderNames() {
//				return super.getHeaderNames();
//			}
//
//			@Override
//			public String getContentType() {
//				return "application/json";
//			}
//		}
//
//		private class AuthTokenHttpServletRequest extends HttpServletRequestWrapper {
//			private final String requestBody;
//
//			public AuthTokenHttpServletRequest(HttpServletRequest request, String requestBody) {
//				super(request);
//				this.requestBody = requestBody;
//			}
//
//			public ServletInputStream getInputStream() {
//				try {
//					return new ByteServletInputStream(new ByteArrayInputStream(requestBody.getBytes("UTF-8")));
//				} catch (Throwable e) {
//					log.error("", e);
//				}
//				return null;
//			}
//		}
//
//		private class ByteServletInputStream extends ServletInputStream {
//			private ByteArrayInputStream byteInputStream;
//
//			private ByteServletInputStream(ByteArrayInputStream byteInputStream) {
//				this.byteInputStream = byteInputStream;
//			}
//
//			@Override
//			public boolean isFinished() {
//				return byteInputStream.available() <= 0;
//			}
//
//			@Override
//			public boolean isReady() {
//				return true;
//			}
//
//			@Override
//			public void setReadListener(ReadListener readListener) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public int read() throws IOException {
//				return byteInputStream.read();
//			}
//
//		}
//	}