package com.wh.tms.interceptor;

import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.wh.tms.constans.SystemCons;
import com.wh.tms.entity.po.LoginUser;
import com.wh.tms.service.ILoginUserService;
import com.wh.tms.util.HttpOpUtil;
import com.wh.tms.util.ResultUtils;
import com.wh.tms.util.TokenUtils;

public class TokenVerifyInterceptor implements HandlerInterceptor {
	
	private static final Log log = LogFactory.getLog(TokenVerifyInterceptor.class);
	
	
	@Autowired
	private ILoginUserService loginUserService;

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		
		//从session中获取登录用户
		Object obj = request.getSession().getAttribute(SystemCons.session_key);
		if (null != obj) {
			return true;
		}
		//获取请求
		String requestUri = request.getRequestURI();
		//不过滤login、静态资源、Api文档
		if (StringUtils.indexOf(requestUri, "login.json") > 0 || 
				StringUtils.startsWith(requestUri, "/static") ||
				StringUtils.startsWith(requestUri, "/doc")) {
			return true;
		}
		String token = TokenUtils.getToken(request);
		if (StringUtils.isBlank(token)) {
			HttpOpUtil.doOutputJson(response, JSONObject.toJSONString(ResultUtils.error(SystemCons.ERROR_NO_PERMISSION, "未授权不允许访问！")));
			return false;
		}
		//验证token
		Claims map = null;
		try {
			map = TokenUtils.verifyToken(token);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		if (null == map) {
			HttpOpUtil.doOutputJson(response, JSONObject.toJSONString(ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "登录已过期！")));
			return false;
		}
		Integer uid = (Integer)map.get("uid");
		LoginUser user = loginUserService.getLoginUserById(uid);
		if (null == user) {
			return false;
		}
		request.getSession().setAttribute(SystemCons.session_key, user);
		return true;
	}

}
