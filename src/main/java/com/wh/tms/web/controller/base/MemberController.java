package com.wh.tms.web.controller.base;

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
import com.wh.tms.entity.po.Member;
import com.wh.tms.result.Result;
import com.wh.tms.service.IMemberService;
import com.wh.tms.util.ResultUtils;
import com.wh.tms.web.controller.BaseController;


@Controller
@RequestMapping("/base/member")
@Domain(desc="基础数据-员工管理")
public class MemberController extends BaseController {
	
	private static final Log log = LogFactory.getLog(MemberController.class);
	
	@Autowired
	private IMemberService memberService;
	
	@Api(author="Jesson",
			createtime="2018-04-28",
			name="新增员工",
			desc="",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="mName",desc="员工名称(utf-8编码)"),
					@Rule(name="deptId",desc="部门id"),
					@Rule(name="teamId",desc="团队id"),
					@Rule(name="sex",desc="用户性别,1:男 2:女")
			},
			demo = @Demo(param="mName=jesson&deptId=1&sex=1",success="{'code':0,'count':1,'data':{'deptId':1,'deptName':'建造公司','email':null,'id':1977,"
					+ "'insertTime':'2018-04-29 15:19:54','name':'jesson','phone':null,'sex':1,'status':null,'teamId':null,'teamName':null},'msg':'成功'}"))
	@RequestMapping(value = "/add.json",method = RequestMethod.POST)
	public @ResponseBody Result add(@RequestParam(value = "mName",required = true)  String mName,
			@RequestParam(value = "deptId",required = true) Integer deptId,
			@RequestParam(value = "teamId",required = false) Integer teamId,
			@RequestParam(value = "sex",required = true) Integer sex,HttpServletRequest request) {
		
		LoginUser loginUser = getSessionUser(request);
		if (null == loginUser) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录!"); 
		}
		try {
			return memberService.addMember(deptId, teamId, mName, sex);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("添加员工失败!");
	}
	
	@Api(author="Jesson",
			createtime="2018-04-28",
			name="删除员工",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="uid",desc="员工id")
			},
			demo = @Demo(param="uid=1977",success="{'code': 0,'count': 1,'data': '','msg': '删除成功'}"))
	@RequestMapping(value = "/delete.json",method = RequestMethod.POST)
	public @ResponseBody Result deleteLoginUser(@RequestParam(value = "uid",required = true)  Integer uid,
			HttpServletRequest request) {
		LoginUser loginUser = getSessionUser(request);
		if (null == loginUser) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录!"); 
		}
		try {
			return memberService.deleteMember(uid);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("删除失败!");
	}
	
	@Api(author="Jesson",
			createtime="2018-04-28",
			name="更新员工信息",
			desc="",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="uid",desc="员工id"),
					@Rule(name="mName",desc="员工名称(utf-8编码)"),
					@Rule(name="deptId",desc="部门id"),
					@Rule(name="teamId",desc="团队id"),
					@Rule(name="sex",desc="用户性别,1:男 2:女")
			},
			demo = @Demo(param="uid=1977&teamId=9",success="{'code':0,'count':1,'data':{'deptId':1,'deptName':'建造公司','email':null,'id':1977,"
					+ "'insertTime':'2018-04-29 15:19:54','name':'jesson','phone':null,'sex':1,'status':0,'teamId':9,'teamName':'结构一队'},'msg':'成功'}"))
	@RequestMapping(value = "/update.json",method = RequestMethod.POST)
	public @ResponseBody Result update(@RequestParam(value = "uid",required = true) Integer uid,
			@RequestParam(value = "mName",required = false)  String mName,
			@RequestParam(value = "deptId",required = false) Integer deptId,
			@RequestParam(value = "teamId",required = false) Integer teamId,
			@RequestParam(value = "sex",required = false) Integer sex,HttpServletRequest request) {
		
		LoginUser loginUser = getSessionUser(request);
		if (null == loginUser) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录!"); 
		}
		try {
			return memberService.updateMember(uid, deptId, teamId, mName, sex);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("更新员工信息失败!");
	}
	
	@Api(author="Jesson",
			createtime="2018-03-11",
			name="员工列表",
			desc="查询员工信息",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="uid",desc="用户id"),
					@Rule(name="deptId",desc="部门id"),
					@Rule(name="mName",desc="员工名称"),
					@Rule(name="page",desc="起始页"),
					@Rule(name="limit",desc="每页显示的条数")
			},
			demo = @Demo(param="",success="{'code':0,'count':1976,'data':[{'deptId':3,'deptName':'容器车间','email':null,'id':1964,'insertTime':'2018-03-16 02:38:00',"
					+ "'name':'崔志昆','phone':null,'sex':1,'status':0,'teamId':null,'teamName':null},{'deptId':3,'deptName':'容器车间',"
					+ "'email':null,'id':1963,'insertTime':'2018-03-16 02:38:00','name':'石统明','phone':null,'sex':1,'status':0,'teamId':null,"
					+ "'teamName':null},{'deptId':3,'deptName':'容器车间','email':null,'id':1962,'insertTime':'2018-03-16 02:38:00','name':'王涛2',"
					+ "'phone':null,'sex':1,'status':0,'teamId':null,'teamName':null}],'msg':'成功'}"))
	@RequestMapping(value = "/list.json",method = RequestMethod.POST)
	public @ResponseBody Result getUserList(@RequestParam(value = "uid",required = false)  Integer uid,
			@RequestParam(value = "deptId",required = false)  Integer deptId,
			@RequestParam(value = "mName",required = false) String mName,
			@RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
			@RequestParam(value = "limit",required = false,defaultValue = "15") Integer limit,
			HttpServletRequest request) {
		
		LoginUser loginUser = getSessionUser(request);
		if (null == loginUser) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录!"); 
		}
		PageInfo<Member> pageInfo = memberService.getMemberList(uid,deptId, mName, page, limit);
		if (null == pageInfo || pageInfo.getList() == null || pageInfo.getList().size() == 0) {
			return ResultUtils.success(0, "");
		}
		return ResultUtils.success(pageInfo.getTotal(), pageInfo.getList());
	} 

}
