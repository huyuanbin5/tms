package com.wh.tms.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.wh.tms.constans.SystemCons;
import com.wh.tms.entity.po.LoginUser;

public class BaseController {
	
	/**
	 * 获取系统路径
	* @Title: getBasePath 
	* @Description: TODO
	* @param @param request
	* @param @return 
	* @return String
	* @throws
	 */
	protected String getBasePath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath() + "/";
    }
	
	/**
	 * 当前登录用户
	* @Title: getSessionUser 
	* @Description: TODO
	* @param @param request
	* @param @return 
	* @return LoginUser
	* @throws
	 */
	protected LoginUser getSessionUser(HttpServletRequest request) {
		if (null != request) {
			Object object = request.getSession().getAttribute(SystemCons.session_key);
			if (null != object) {
				return (LoginUser)object;
			}
		}
		return null;
	}

}
