package com.wh.tms.web.controller.auth;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wh.tms.constans.SystemCons;
import com.wh.tms.doc.annotation.Api;
import com.wh.tms.doc.annotation.Auth;
import com.wh.tms.doc.annotation.Demo;
import com.wh.tms.doc.annotation.Domain;
import com.wh.tms.doc.annotation.Rule;
import com.wh.tms.entity.po.LoginUser;
import com.wh.tms.entity.vo.DeptVo;
import com.wh.tms.result.Result;
import com.wh.tms.service.IDeptService;
import com.wh.tms.util.ResultUtils;
import com.wh.tms.web.controller.BaseController;

/**
 * 部门管理
 * @author Jesson
 *
 */
@Controller
@RequestMapping("/system/dept")
@Domain(desc="系统管理-部门管理")
public class DeptManagerController extends BaseController {
	
	private static final Log log = LogFactory.getLog(DeptManagerController.class);
	
	@Autowired
	private IDeptService deptSrvice;
	
	/**
	 * 获取部门列表
	* @Title: getMenuList 
	* @Description: TODO
	* @param @param request
	* @param @return 
	* @return Result
	* @throws
	 */
	@Api(author="Jesson",
			createtime="2018-03-17",
			name="获取部门列表",
			desc="",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="deptId",desc="部门id")
			},
			demo = @Demo(param="",success="{'code': 0,'count': 1,'data': [{'children': ["
					+ "{'children': [{'children': null,'deptName': '结构一队','id': 9,'level': 3,'orderId': 1,'pid': 2,'xpath': '1/2/9'},],"
					+ "'deptName': '结构车间','id': 2,'level': 2,'orderId': 2,'pid': 1,'xpath': '1/2'}],"
					+ "'deptName': '建造公司','id': 1,'level': 1,'orderId': 1,'pid': 0,'xpath': '1/'}],'msg': '成功'}"))
	@RequestMapping(value = "/list.json",method = RequestMethod.POST)
	public @ResponseBody Result getDeptList(@RequestParam(value = "deptId",required = false) Integer deptId,HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		List<DeptVo> list = deptSrvice.getDeptList(deptId);
		return ResultUtils.success(null == list ? 0 : list.size(), list);
	}
	
	@Api(author="Jesson",
			createtime="2018-04-14",
			name="添加部门",
			desc="",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="pid",desc="父节点id,不传默认为0(根节点)"),
					@Rule(name="deptName",desc="部门名称(utf-8编码)")
			},
			demo = @Demo(param="pid=0&deptName=添加一级节点测试",success="{'code':0,'count':1,'data':{'deptName':'添加一级节点测试','id':43,'level':1,'orderId':1,'pid':0,'xpath':'43/'},'msg':'成功'}"))
	@RequestMapping(value = "/add.json",method = RequestMethod.POST)
	public @ResponseBody Result addDept(@RequestParam(value = "pid",required = false,defaultValue = "0") Integer pid,@RequestParam("deptName") String deptName,HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return deptSrvice.addDept(pid, deptName);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("添加部门失败!");
	}
	
	@Api(author="Jesson",
			createtime="2018-04-14",
			name="删除部门",
			desc="",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="deptId",desc="部门id")
			},
			demo = @Demo(param="deptId=43",success="{'code':0,'count':1,'data':'','msg':'删除成功!'}"))
	@RequestMapping(value = "/delete.json",method = RequestMethod.POST)
	public @ResponseBody Result deleteDept(@RequestParam(value = "deptId",required = true) Integer deptId,HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return deptSrvice.deleteDept(deptId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("删除部门失败!");
	}
	
	@Api(author="Jesson",
			createtime="2018-04-14",
			name="更新部门信息",
			desc="",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="deptId",desc="部门id"),
					@Rule(name="deptName",desc="部门名称(utf-8编码)")
			},
			demo = @Demo(param="deptId=43&deptName=更新节点名称测试",success="{'code':0,'count':1,'data':{'deptName':'更新部门名称','id':43,'level':1,'orderId':1,'pid':0,'xpath':'43/'},'msg':'更新成功'}"))
	@RequestMapping(value = "/update.json",method = RequestMethod.POST)
	public @ResponseBody Result updateDept(@RequestParam(value = "deptId",required = true) Integer deptId,@RequestParam(value = "deptName",required = true) String deptName,HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return deptSrvice.updateDept(deptId, deptName);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("更新部门信息失败!");
	}
	

}
