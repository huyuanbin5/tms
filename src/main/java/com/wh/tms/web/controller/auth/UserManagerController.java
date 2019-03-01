package com.wh.tms.web.controller.auth;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
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
import com.wh.tms.web.controller.BaseController;

/**
 * 系统管理用户
* @ClassName: UserManagerController 
* @Description: TODO 
* @author Huqk
* @date 2018年3月11日 下午1:40:00
 */
@Controller
@RequestMapping("/system/user")
@Domain(desc="系统管理-系统用户管理")
public class UserManagerController extends BaseController {
	
	private static final Log log = LogFactory.getLog(UserManagerController.class);
	
	@Autowired
	private ILoginUserService userSrvice;
	
	/**
	 * 用户列表
	* @Title: getUserList 
	* @Description: TODO
	* @param @param deptId
	* @param @param uname
	* @param @param page
	* @param @param limit
	* @param @param request
	* @param @return 
	* @return Result
	* @throws
	 */
	@Api(author="Jesson",
			createtime="2018-03-11",
			name="用户列表",
			desc="查询用户信息",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="deptId",desc="部门id"),
					@Rule(name="uname",desc="用户名称"),
					@Rule(name="page",desc="起始页"),
					@Rule(name="limit",desc="每页显示的条数")
			},
			demo = @Demo(param="",success="{'code': 0,'count': 1,'data': [{'dept': '系统管理员','deptId': 1,'id': 1,"
					+ "'insertTime': '2018-03-11 19:38:16','name': 'admin','sex': 1,'status': 0}],'msg': '成功'}"))
	@RequestMapping(value = "/list.json",method = RequestMethod.POST)
	public @ResponseBody Result getUserList(@RequestParam(value = "deptId",required = false)  Integer deptId,
			@RequestParam(value = "uname",required = false) String uname,
			@RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
			@RequestParam(value = "limit",required = false,defaultValue = "15") Integer limit,
			HttpServletRequest request) {
		
		LoginUser loginUser = getSessionUser(request);
		if (null == loginUser) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录!"); 
		}
		PageInfo<LoginUser> pageInfo = userSrvice.getLoginUserList(deptId, uname, page, limit);
		if (null == pageInfo || pageInfo.getList() == null || pageInfo.getList().size() == 0) {
			return ResultUtils.success(0, "");
		}
		return ResultUtils.success(pageInfo.getTotal(), pageInfo.getList());
	}              		
	
	/**
	 * 更新用户信息
	* @Title: updateUser 
	* @Description: TODO
	* @param @param uid
	* @param @param deptId
	* @param @param sex
	* @param @param request
	* @param @return 
	* @return Result
	* @throws
	 */
	@Api(author="Jesson",
			createtime="2018-03-11",
			name="更新用户信息",
			desc="更新用户基本信息",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="uid",desc="用户id"),
					@Rule(name="deptId",desc="部门id"),
					@Rule(name="sex",desc="用户性别,1:男 2:女"),
					@Rule(name="limit",desc="每页显示的条数")
			},
			demo = @Demo(param="uid=35&sex=2",success="{'code': 0,'count': 1,'data': '','msg': '成功'}"))
	@RequestMapping(value = "/update.json",method = RequestMethod.POST)
	public @ResponseBody Result updateUser(@RequestParam(value = "uid",required = true)  Integer uid,
			@RequestParam(value = "deptId",required = false) Integer deptId,
			@RequestParam(value = "sex",required = false) Integer sex,
			HttpServletRequest request) {
		
		LoginUser loginUser = getSessionUser(request);
		if (null == loginUser) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录!"); 
		}
		return userSrvice.updateLoginUser(uid, deptId, sex);
	}
	
	@Api(author="Jesson",
			createtime="2018-04-28",
			name="删除用户",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="uid",desc="用户id")
			},
			demo = @Demo(param="userId=10",success="{'code': 0,'count': 1,'data': '','msg': '成功'}"))
	@RequestMapping(value = "/delete.json",method = RequestMethod.POST)
	public @ResponseBody Result deleteLoginUser(@RequestParam(value = "uid",required = true)  Integer uid,
			HttpServletRequest request) {
		LoginUser loginUser = getSessionUser(request);
		if (null == loginUser) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录!"); 
		}
		
		try {
			return userSrvice.deleteLoginUser(uid);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("用户删除失败!");
	}

	@Api(author="Jesson",
			createtime="2018-04-28",
			name="新增用户",
			desc="新增系统登录用户",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="uName",desc="用户名(utf-8编码)"),
					@Rule(name="account",desc="账号"),
					@Rule(name="password",desc="密码"),
					@Rule(name="deptId",desc="部门id"),
					@Rule(name="officPhone",desc="办公室电话号码，注意：(办公室电话号码和手机号不能同时为空)"),
					@Rule(name="email",desc="邮箱"),
					@Rule(name="mobilePhone",desc="手机号"),
					@Rule(name="sex",desc="用户性别,1:男 2:女")
			},
			demo = @Demo(param="uName=test&account=test&password=123&deptId=1&mobilePhone=13371815029&sex=1",success="{'code':0,'count':1,'data':{'account':'test','deptId':1,'deptName':'建造公司',"
					+ "'id':36,'insertTime':'2018-04-29 11:37:40','mobilePhone':'13371815029','officPhone':null,'password':'123',"
					+ "'sex':1,'status':null,'token':null,'userName':'test'},'msg':'成功'}"))
	@RequestMapping(value = "/add.json",method = RequestMethod.POST)
	public @ResponseBody Result addUser(@RequestParam(value = "uName",required = true)  String uName,
			@RequestParam(value = "account",required = true) String account,
			@RequestParam(value = "password",required = true) String password,
			@RequestParam(value = "deptId",required = true) Integer deptId,
			@RequestParam(value = "officPhone",required = false) Integer officPhone,
			@RequestParam(value = "email",required = false) String email,
			@RequestParam(value = "mobilePhone",required = false) String mobilePhone,
			@RequestParam(value = "sex",required = true) Integer sex,
			HttpServletRequest request) {
		
		LoginUser loginUser = getSessionUser(request);
		if (null == loginUser) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录!"); 
		}
		try {
			return userSrvice.addLoginUser(uName, account, password, deptId, officPhone, email, mobilePhone, sex);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("添加用户失败!");
	}
}
