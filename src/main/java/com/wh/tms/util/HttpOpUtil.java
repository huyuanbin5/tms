package com.wh.tms.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




public class HttpOpUtil {
	
	private static final Log logger = LogFactory.getLog(HttpOpUtil.class);
	
	
	public static void doOutputJson(HttpServletResponse response, String json) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, TRACE, HEAD, PATCH");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type,x-access-token");
		PrintWriter writer = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			writer = response.getWriter();
			writer.print(json);
			writer.flush();
		} catch (IOException e) {
			logger.error("out put tab json error: " + e);
		} finally {
			try {
				writer.close();
			} catch (Exception e2) {
				logger.error(e2 + "");
			}
		}
	}
	
	/**
	 * 输出html内容
	 * 
	 * @param response
	 * @param html
	 */
	public static void doOutputHtml(HttpServletResponse response, String html) {
		PrintWriter writer = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			writer = response.getWriter();
			writer.print(html);
			writer.flush();
		} catch (IOException e) {
			logger.error("out put tab json error: " + e);
		} finally {
			try {
				writer.close();
			} catch (Exception e2) {
				logger.error(e2 + "");
			}
		}
	}
	
	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}

		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}

		return null;
	}
	
	public static String getDomainHost(HttpServletRequest request){
		String serverName = request.getServerName();
		return getDomainHost(serverName);
	}
	
	public static String getDomainHost(String serverName){
		if(serverName == null){
			return null;
		}
		
		String[] names = serverName.split("\\.");
		int len = names.length;
		if(len < 3){
			return serverName;
		}
		return serverName.substring(serverName.indexOf(names[len-2]));
	}
	
	public static String getHttpUrl(String url){
		if(url.indexOf("http") == -1){
			url = "http://"+url;
		}
		return url;
	}
}
