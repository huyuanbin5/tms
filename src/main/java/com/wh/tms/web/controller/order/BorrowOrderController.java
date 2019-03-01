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
import com.wh.tms.service.IBorrowOrderService;
import com.wh.tms.util.ResultUtils;
import com.wh.tms.web.controller.BaseController;

/**
 * 借还单
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/borrow/order")
@Domain(desc="借还管理-借还单")
public class BorrowOrderController extends BaseController {
	
	private static final Log log = LogFactory.getLog(BorrowOrderController.class);
	@Autowired
	private IBorrowOrderService borrowOrderService;
	
	/**
	 * 添加借还单
	 * @param imprestOrderId
	 * @param request
	 * @return
	 */
	@Api(author="Jesson",
			createtime="2018-03-23",
			name="添加借还单",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="imprestOrderId",desc="预借清单id")
			},
			demo = @Demo(param="imprestOrderId=1",success="{'code':0,'count':1,'data':{'id':2,'imprestOrderId':1,'invalidTime':'2018-03-27 00:00:00','outInst':'更新',"
					+ "'reservationTime':'2018-03-24 00:00:00','status':0,'uid':1,'userName':'何欢','workType':1},'msg':'成功'}"))
	@RequestMapping(value = "/add.json",method = RequestMethod.POST)
	public @ResponseBody Result addBorrowOrder(@RequestParam(value = "imprestOrderId",required = false) Integer imprestOrderId,HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		Result result = null;
		try {
			result = borrowOrderService.addBorrowOrder(imprestOrderId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result == null ?  ResultUtils.error(SystemCons.FAILURE, "添加借还单失败！") : result;
	}
	
	/**
	 * 借还单列表
	 * @param imprestOrderId
	 * @param request
	 * @return
	 */
	@Api(author="Jesson",
			createtime="2018-04-04",
			name="借还单列表",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="borrowOrderStatus",desc="借还单状态0:未完结,1:已完结"),
					@Rule(name="startTime",desc="出库起始日期,格式yyyy-MM-dd"),
					@Rule(name="endTime",desc="出库截止日期,格式yyyy-MM-dd"),
					@Rule(name="page",desc="起始页数,不传默认为第一页。"),
					@Rule(name="limit",desc="每页取得条数,不传默认为15。")
			},
			demo = @Demo(param="",success="{'code':0,'count':2,'data':[{'borrowerOrderStatus':0,'deliveryDate':null,'id':2,"
					+ "'imprestOrderId':1,'invalidTime':'2018-03-27 00:00:00','leader':'何欢','leaderId':1,'outInst':'更新','reservationTime':'2018-03-24 00:00:00',"
					+ "'status':0,'uid':1,'userName':'何欢','workType':1},{'borrowerOrderStatus':0,'deliveryDate':null,'id':3,'imprestOrderId':5,'invalidTime':'2019-03-30 00:00:00',"
					+ "'leader':'何欢','leaderId':1,'outInst':'我借一个螺丝刀',"
					+ "'reservationTime':'2018-03-30 00:00:00','status':0,'uid':1,'userName':'何欢','workType':1}],'msg':'获取借还单数据成功!'}"))
	@RequestMapping(value = "/datas.json",method = RequestMethod.POST)
	public @ResponseBody Result getBorrowOrderList(@RequestParam(value = "borrowOrderStatus",required = false) Integer borrowOrderStatus,
			@RequestParam(value = "startTime",required = false) String startTime,
			@RequestParam(value = "endTime",required = false) String endTime,
			@RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer page,
			@RequestParam(value = "limit",required = false,defaultValue = "15") Integer limit,
			HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return borrowOrderService.getBorrowOrderList(1, borrowOrderStatus, startTime, endTime, page, limit);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.error(SystemCons.FAILURE, "获取借还单列表失败！");
	}
	
	/**
	 * 借还单详情
	 * @param imprestOrderId
	 * @param request
	 * @return
	 */
	@Api(author="Jesson",
			createtime="2018-04-08",
			name="获取借还单详情",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="id",desc="借还单id")
			},
			demo = @Demo(param="id=2",success="{'code':0,'count':1,'data':{'borrowerOrderStatus':0,'deliveryDate':null,'id':2,'imprestOrderId':1,"
					+ "'invalidTime':'2018-03-27 00:00:00','leader':'何欢','leaderId':1,"
					+ "'outInst':'更新','reservationTime':'2018-03-24 00:00:00','status':0,'uid':1,'userName':'何欢','workType':1},'msg':'成功'}"))
	@RequestMapping(value = "/detail.json",method = RequestMethod.POST)
	public @ResponseBody Result getBorrowOrderDetail(@RequestParam(value = "id",required = true) Integer id,HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return borrowOrderService.getBorrowOrderDetail(id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.error(SystemCons.FAILURE, "获取借还单详情失败！");
	}

}
