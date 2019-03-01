package com.wh.tms.web.controller.order;

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
import com.wh.tms.result.Result;
import com.wh.tms.service.IImprestOrderService;
import com.wh.tms.util.ResultUtils;
import com.wh.tms.web.controller.BaseController;

/**
 * 预借单
 * @author Jesson
 *
 */
@Controller
@RequestMapping("/imprest/order")
@Domain(desc="借还管理-预借单")
public class ImprestOrderController extends BaseController {
	
	private static final Log log = LogFactory.getLog(ImprestOrderController.class);
	
	@Autowired
	private IImprestOrderService imprestOrderService;
	
	/**
	 * 预借单列表
	 * @param status
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param limit
	 * @param request
	 * @return
	 */
	@Api(author="Jesson",
			createtime="2018-03-19",
			name="预借单列表",
			desc="获取当前登录人创建的预借单列表",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="orderId",desc="订单id"),
					@Rule(name="orderStatus",desc="预借单状态，0:未提交,1:未处理,2:已处理,3:已失效"),
					@Rule(name="startTime",desc="预约起始时间"),
					@Rule(name="endTime",desc="预约截止时间"),
					@Rule(name="page",desc="起始页"),
					@Rule(name="limit",desc="每页显示的条数")
			},
			demo = @Demo(param="orderStatus=0",success="{'code':0,'count':1,'data':[{'id':1,'imprestStatus':0,'invalidTime':'2018-03-26 00:00:00','outInst':'测试','reservationTime':'2018-03-23 00:00:00',"
					+ "'status':0,'uid':1,'updateTime':null,'updateUid':null,'userName':'何欢','workType':1}],'msg':'成功'}"))
	@RequestMapping(value = "/list.json",method = RequestMethod.POST)
	public @ResponseBody Result getImprestOrderList(@RequestParam(value = "orderId",required = false) Integer orderId,
			@RequestParam(value = "orderStatus",required = false) Integer orderStatus,
			@RequestParam(value = "startTime",required = false) String startTime, 
			@RequestParam(value = "endTime",required = false) String endTime, 
			@RequestParam(value = "page",required = false,defaultValue = "1") Integer page, 
			@RequestParam(value = "limit",required = false,defaultValue = "15") Integer limit,
			HttpServletRequest request) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		return imprestOrderService.getImprestOrderList(user.getId(),orderId, orderStatus, startTime, endTime, page, limit);
	}
	
	@Api(author="Jesson",
			createtime="2018-03-19",
			name="创建预借单",
			desc="",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="resTime",desc="预约时间"),
					@Rule(name="invTime",desc="失效时间"),
					@Rule(name="workType",desc="作业类型,1:本地作业,2:外部作业,3:出海作业"),
					@Rule(name="outInst",desc="借出说明")
			},
			demo = @Demo(param="resTime=2018-03-23&invTime=2018-03-26&workType=1&outInst=测试",success="{'code':0,'count':1,'data':{'id':1,'imprestStatus':0,'invalidTime':'2018-03-26 00:00:00','outInst':'测试',"
					+ "'reservationTime':'2018-03-23 00:00:00','status':0,'uid':1,'updateTime':null,'updateUid':null,"
					+ "'userName':'何欢','workType':1},'msg':'添加预借单成功！'}"))
	@RequestMapping(value = "/add.json",method = RequestMethod.POST)
	public @ResponseBody Result addImprestOrder(@RequestParam(value = "resTime",required = true) String resTime, 
			@RequestParam(value = "invTime",required = true) String invTime, 
			@RequestParam(value = "workType",required = true) Integer workType,
			@RequestParam(value = "outInst",required = false) String outInst,
			HttpServletRequest request) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		Result result = null;
		try {
			result = imprestOrderService.addImprestOrder(user.getId(), resTime, invTime, workType, outInst);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result == null ? ResultUtils.error(SystemCons.FAILURE, "创建预借单失败！") : result;
	}
	
	/**
	 * 更新预借单
	 * @param resTime
	 * @param invTime
	 * @param workType
	 * @param outInst
	 * @param request
	 * @return
	 */
	@Api(author="Jesson",
			createtime="2018-03-19",
			name="更新预借单",
			desc="更新预借单信息，注意：预借单只能在未提交状态下才能更新",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
				@Rule(name="orderId",desc="预订单id"),
				@Rule(name="resTime",desc="预约时间"),
				@Rule(name="invTime",desc="失效时间"),
				@Rule(name="workType",desc="作业类型,1:本地作业,2:外部作业,3:出海作业"),
				@Rule(name="outInst",desc="借出说明")
			},
			demo = @Demo(param="orderId=1&resTime=2018-03-24&invTime=2018-03-27&workType=1&outInst=更新",success="{'code':0,'count':1,'data':{'id':1,'imprestStatus':0,'invalidTime':'2018-03-27 00:00:00','outInst':'更新','reservationTime':'2018-03-24 00:00:00',"
					+ "'status':0,'uid':1,'updateTime':'2018-03-22 23:04:50',"
					+ "'updateUid':1,'userName':'何欢','workType':1},'msg':'预借单更新成功!'}"))
	@RequestMapping(value = "/update.json",method = RequestMethod.POST)
	public @ResponseBody Result updateImprestOrder(@RequestParam(value = "orderId",required = true) Integer orderId, 
			@RequestParam(value = "resTime",required = true) String resTime, 
			@RequestParam(value = "invTime",required = true) String invTime, 
			@RequestParam(value = "workType",required = true) Integer workType,
			@RequestParam(value = "outInst",required = false) String outInst,
			HttpServletRequest request) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		Result result = null;
		try {
			result = imprestOrderService.updateImprestOrder(orderId, user.getId(), resTime, invTime, workType, outInst);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result == null ? ResultUtils.error(SystemCons.FAILURE, "更新预借单失败！") : result;
	}
	
	/**
	 * 删除预借单
	 * @param orderId
	 * @param request
	 * @return
	 */
	@Api(author="Jesson",
			createtime="2018-03-19",
			name="删除预借单",
			desc="删除预借单信息",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="orderId",desc="预借单id")
			},
			demo = @Demo(param="orderId=1",success="{'code':0,'count':1,'data':'','msg':'预借单删除成功!'}"))
	@RequestMapping(value = "/delete.json",method = RequestMethod.POST)
	public @ResponseBody Result deleteImprestOrder(@RequestParam(value = "orderId",required = true) Integer orderId,
			HttpServletRequest request) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		Result result = null;
		try {
			result = imprestOrderService.deleteImprestOrder(orderId, user.getId());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result == null ? ResultUtils.error(SystemCons.FAILURE, "删除失败！") : result;
	}
}
