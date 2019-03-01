package com.wh.tms.constans;

/**
* @ClassName: SystemCons 
* @Description: 系统常量
* @author Huqk
* @date 2018年1月20日 下午12:55:45
 */
public class SystemCons {
	
	/**
	 * 系统登录用户session标识
	 */
	public static final String session_key = "login_user";
	/**
	 * token私钥
	 */
	public static final String sso_key = "!jdQ@n47fgbNvagFe2k";
	/**
	 * token失效时间两小时
	 */
	public static final long token_exp_time = 1000 * 60 * 60 * (24 * 7);
	/**
	 * 成功标识
	 */
	public static final int SUCCESS = 0;
	/**
	 * 失败标识
	 */
	public static final int FAILURE = -1;
	/**
	 * 参数错误标识
	 */
	public static final int ERROR_EMPTY_PARAMS = -2;
	/**
	 * 未授权不允许访问
	 */
	public static final int ERROR_NO_PERMISSION = 98;
	/**
	 * 未登录
	 */
	public static final int ERROR_NO_LOGINING = 99;
	/**
	 * 系统管理员角色标识
	 */
	public static final Integer SYSTEM_MANAGER = 1;

}
