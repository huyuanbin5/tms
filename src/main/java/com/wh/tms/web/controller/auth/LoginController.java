package com.wh.tms.web.controller.auth;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wh.tms.constans.SystemCons;
import com.wh.tms.doc.annotation.Api;
import com.wh.tms.doc.annotation.Auth;
import com.wh.tms.doc.annotation.Demo;
import com.wh.tms.doc.annotation.Domain;
import com.wh.tms.doc.annotation.Rule;
import com.wh.tms.entity.po.LoginUser;
import com.wh.tms.result.Result;
import com.wh.tms.service.ILoginUserService;
import com.wh.tms.util.ResultUtils;
import com.wh.tms.util.TokenUtils;

@Controller
@Domain(desc="用户登录")
public class LoginController {
	
	@Autowired
	private ILoginUserService loginUserService;
	
	/**
	 * 用户登录
	 * @param name
	 * @param password
	 * @return
	 */
	@Api(author="Jesson",
			createtime="2018-03-06",
			name="用户登录",
			desc="登录系统，登录成功后返回token,token有效时间为7天。请求接口必须带上该token，token设置在请求头信息中标识为：x-access-token。",
			params={
					@Rule(name="uname",desc="用户名"),
					@Rule(name="password",desc="密码")
			},
			auth = {
				@Auth(checkEnc = false)
			},
			demo = @Demo(param="uname=admin&password=123",success="{'code': 0,'count': 1,"
					+ "'data': {'token': 'eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjEsImV4cCI6MTUyMDM1NTk5NX0.RP0y7mMOAXGBPqdyxtPZdo1W-i-Rcl36noTFgZgcBic'},"
					+ "'msg': '成功'}"))
	@RequestMapping(value = "/login.json",method = RequestMethod.POST)
	public @ResponseBody Result login(@RequestParam("uname") String uname,
			@RequestParam("password") String password,
			HttpServletRequest request) {
		
		if (StringUtils.isBlank(uname)) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "用户名不能为空!");
		}
		if (StringUtils.isBlank(password)) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "密码不能为空!");
		}
		LoginUser user = loginUserService.getLoginUser(uname, password);
		if (null == user) {
			return ResultUtils.error(SystemCons.FAILURE, "用户名或密码错误!");
		}
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("uid", user.getId());
		//生成token
		String token = TokenUtils.generateToken(params, SystemCons.token_exp_time);
		if (StringUtils.isBlank(token)) {
			return ResultUtils.error(SystemCons.FAILURE, "生成令牌失败!");
		}
		user.setToken(token);
		int updateCount = loginUserService.update_loginUser(user);
		if (updateCount < 1) {
			return ResultUtils.error(SystemCons.FAILURE, "更新令牌失败!");
		}
		JSONObject object = new JSONObject();
		object.put("token", token);
        return ResultUtils.success(1, object, "成功");
		
	}
	
	
	/**
	 * 退出登录
	* @Title: logout 
	* @Description: TODO
	* @param @param request
	* @param @return 
	* @return String
	* @throws
	 */
	@RequestMapping("logout")
	public String logout(HttpServletRequest request){
        return "redirect:/login.htm";
	}

}
