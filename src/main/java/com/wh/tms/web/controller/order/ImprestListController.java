package com.wh.tms.web.controller.order;

import javax.servlet.http.HttpServletRequest;

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
import com.wh.tms.result.Result;
import com.wh.tms.service.IImprestListService;
import com.wh.tms.util.ResultUtils;
import com.wh.tms.web.controller.BaseController;


@Controller
@RequestMapping("/imprest/list")
@Domain(desc="借还管理-预借清单")
public class ImprestListController extends BaseController {
	
	@Autowired
	private IImprestListService imprestListService;
	
	/**
	 * 添加预借清单
	 * @param orderId
	 * @param toolNum
	 * @param toolName
	 * @param startTime
	 * @param endTime
	 * @param borrId
	 * @param leaderId
	 * @param explain
	 * @param part_remark
	 * @param request
	 * @return
	 */
	@Api(author="Jesson",
			createtime="2018-03-21",
			name="添加预借清单",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="orderId",desc="预借单id"),
					@Rule(name="toolTypeNum",desc="机具种类编号"),
					@Rule(name="toolTypeName",desc="机具种类名称"),
					@Rule(name="startTime",desc="起始时间"),
					@Rule(name="endTime",desc="结束时间"),
					@Rule(name="borrId",desc="借用人id"),
					@Rule(name="leaderId",desc="负责人id"),
					@Rule(name="explain",desc="说明"),
					@Rule(name="part_remark",desc="是否带票,0:否 1:是"),
			},
			demo = @Demo(param="orderId=1&toolNum=TSA001&toolName=砂轮&startTime=2018-03-23&endTime=2018-03-28&borrId=1&leaderId=1&explain=测试",success="{'code':0,'count':1,'data':{'borrowerId':1,'borrowerName':'何欢','createrId':1,"
					+ "'createrName':'何欢','endTime':'2018-03-28 00:00:00','explains':'测试','id':1,'insertTime':'2018-03-23 13:44:09',"
					+ "'leaderId':1,'leaderName':'何欢','orderId':1,'partRemark':null,'startTime':'2018-03-23 00:00:00',"
					+ "'status':null,'toolTypeName':'砂轮','toolTypeNum':'TSA001','updateTime':null},'msg':'预借清单添加成功!'}"))
	@RequestMapping(value = "/add.json",method = RequestMethod.POST)
	public @ResponseBody Result addImprestList(@RequestParam(value = "orderId",required = true) Integer orderId,
			@RequestParam(value = "toolTypeNum",required = true) String toolTypeNum,
			@RequestParam(value = "toolTypeName",required = true) String toolTypeName,
			@RequestParam(value = "startTime",required = true) String startTime,
			@RequestParam(value = "endTime",required = true) String endTime,
			@RequestParam(value = "borrId",required = true) Integer borrId,
			@RequestParam(value = "leaderId",required = true) Integer leaderId,
			@RequestParam(value = "explain",required = false) String explain,
			@RequestParam(value = "part_remark",required = false) Integer part_remark,
			HttpServletRequest request) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		return imprestListService.addImprestList(user.getId(), orderId, toolTypeNum, toolTypeName, startTime, endTime, borrId, leaderId, explain, part_remark);
	}
	/**
	 * 更新预借清单
	 * @param id
	 * @param toolNum
	 * @param toolName
	 * @param startTime
	 * @param endTime
	 * @param borrId
	 * @param leaderId
	 * @param explain
	 * @param part_remark
	 * @param request
	 * @return
	 */
	@Api(author="Jesson",
			createtime="2018-03-21",
			name="更新预借清单信息",
			desc="注意：更新的清单必须为当前登录用户创建的清单，并且预借单状态必须是未提交状态。",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="id",desc="预借清单id"),
					@Rule(name="toolTypeNum",desc="机具种类编号"),
					@Rule(name="toolTypeName",desc="机具种类名称"),
					@Rule(name="startTime",desc="起始时间"),
					@Rule(name="endTime",desc="结束时间"),
					@Rule(name="borrId",desc="借用人id"),
					@Rule(name="leaderId",desc="负责人id"),
					@Rule(name="explain",desc="说明"),
					@Rule(name="part_remark",desc="是否带票,0:否 1:是"),
			},
			demo = @Demo(param="id=1&toolTypeNum=TC001&toolTypeName=砂轮001&startTime=2018-03-23&endTime=2018-03-28&borrId=1&leaderId=1&explain=测试",
			success="{'code':0,'count':1,'data':{'borrowerId':1,'borrowerName':'何欢',"
					+ "'createrId':1,'createrName':'何欢','endTime':'2018-03-28 00:00:00',"
					+ "'explains':'更新','id':1,'insertTime':'2018-03-23 13:44:10',"
					+ "'leaderId':1,'leaderName':'何欢','orderId':1,'partRemark':null,"
					+ "'startTime':'2018-03-23 00:00:00','status':0,'toolTypeName':'砂轮001',"
					+ "'toolTypeNum':'TC001','updateTime':'2018-03-23 14:03:59'},'msg':'预借清单更新成功!'}"))
	@RequestMapping(value = "/update.json",method = RequestMethod.POST)
	public @ResponseBody Result updateImprestList(@RequestParam(value = "id",required = true) Integer id,
			@RequestParam(value = "toolTypeNum",required = true) String toolTypeNum,
			@RequestParam(value = "toolTypeName",required = true) String toolTypeName,
			@RequestParam(value = "startTime",required = true) String startTime,
			@RequestParam(value = "endTime",required = true) String endTime,
			@RequestParam(value = "borrId",required = true) Integer borrId,
			@RequestParam(value = "leaderId",required = true) Integer leaderId,
			@RequestParam(value = "explain",required = false) String explain,
			@RequestParam(value = "part_remark",required = false) Integer part_remark,
			HttpServletRequest request) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		return imprestListService.updateImprestList(user.getId(), id, toolTypeNum, toolTypeName, startTime, endTime, borrId, leaderId, explain, part_remark);
	}
	
	/**
	 * 获取预借单下清单
	 * @param orderId
	 * @param page
	 * @param limit
	 * @param request
	 * @return
	 */
	@Api(author="Jesson",
			createtime="2018-03-21",
			name="清单列表",
		    desc="获取预借单下清单列表",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="orderId",desc="预借单id"),
					@Rule(name="page",desc="页数"),
					@Rule(name="limit",desc="每页取的条数")
			},
			demo = @Demo(param="orderId=1",success="{'code':0,'count':1,'data':{'borrowerId':1,'borrowerName':'何欢','createrId':1,"
					+ "'createrName':'何欢','endTime':'2018-03-28 00:00:00','explains':'更新','id':1,"
					+ "'insertTime':'2018-03-23 13:44:10','leaderId':1,'leaderName':'何欢',"
					+ "'orderId':1,'partRemark':null,'startTime':'2018-03-23 00:00:00','status':0,"
					+ "'toolTypeName':'砂轮001','toolTypeNum':'TC001','updateTime':'2018-03-23 14:03:59'},'msg':'成功!'}"))
	@RequestMapping(value = "/data.json",method = RequestMethod.POST)
	public @ResponseBody Result getImprestList(@RequestParam(value = "orderId",required = true) Integer orderId,
			@RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
			@RequestParam(value = "limit",required = false,defaultValue = "15") Integer limit,
			HttpServletRequest request) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		return imprestListService.getImprestList(orderId, page, limit);
	}
	
	/**
	 * 删除清单
	 * @param id
	 * @param request
	 * @return
	 */
	@Api(author="Jesson",
			createtime="2018-03-21",
			name="删除清单",
		    desc="注意：删除的清单必须为当前登录用户创建的清单，并且预借单状态必须是未提交状态。",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="id",desc="预借清单id")
			},
			demo = @Demo(param="id=1",success="{'code':0,'count':1,'data':'预借清单删除成功!','msg':'成功'}"))
	@RequestMapping(value = "/delete.json",method = RequestMethod.POST)
	public @ResponseBody Result deleteImprestList(@RequestParam(value = "id",required = true) Integer id,
			HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		return imprestListService.deleteImprestList(user.getId(), id);
	}

	/**
	 * 获取预借单下清单
	 * @param orderId
	 * @param page
	 * @param limit
	 * @param request
	 * @return
	 */
	@Api(author="Jesson",
			createtime="2018-04-08",
			name="预借清单详情",
		    desc="",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="id",desc="预借清单id")
			},
			demo = @Demo(param="id=1",success="{'code':0,'count':1,'data':{'borrowerId':1,'borrowerName':'何欢','createrId':1,"
					+ "'createrName':'何欢','endTime':'2018-03-28 00:00:00','explains':'更新','id':1,"
					+ "'insertTime':'2018-03-23 13:44:10','leaderId':1,'leaderName':'何欢',"
					+ "'orderId':1,'partRemark':null,'startTime':'2018-03-23 00:00:00','status':0,"
					+ "'toolTypeName':'砂轮001','toolTypeNum':'TC001','updateTime':'2018-03-23 14:03:59'},'msg':'成功!'}"))
	@RequestMapping(value = "/detail.json",method = RequestMethod.POST)
	public @ResponseBody Result getImprestDetail(@RequestParam(value = "id",required = true) Integer id,
			HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		return imprestListService.getImprestListDetail(id);
	}
}
