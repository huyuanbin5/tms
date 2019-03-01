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
import com.wh.tms.service.IBorrowListService;
import com.wh.tms.util.ResultUtils;
import com.wh.tms.web.controller.BaseController;

/**
 * 借还清单
* @ClassName: BorrowListController 
* @Description: TODO 
* @author Huqk
* @date 2018年3月22日 下午10:26:07
 */
@Controller
@RequestMapping("/borrow/list")
@Domain(desc="借还管理-借还清单")
public class BorrowListController extends BaseController {
	
	@Autowired
	private IBorrowListService borrowListService;
	
	/**
	 * 创建借还清单
	 * @param imprestOrderId
	 * @param imprestListId
	 * @param borrowOrderId
	 * @param toolCode
	 * @param goodsAllo
	 * @param borrowerTime
	 * @param endTime
	 * @param request
	 * @return
	 */
	@Api(author="Jesson",
			createtime="2018-03-23",
			name="创建借还清单",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="imprestOrderId",desc="预借单id"),
					@Rule(name="imprestListId",desc="预借清单id"),
					@Rule(name="borrowOrderId",desc="借还单id"),
					@Rule(name="toolCode",desc="机具编码"),
					@Rule(name="goodsAllo",desc="机具具体货位"),
					@Rule(name="borrowerTime",desc="借出日期"),
					@Rule(name="endTime",desc="截止日期")
			},
			demo = @Demo(param="imprestOrderId=1&imprestListId=1&borrowOrderId=2&toolCode=T00001&goodsAllo=仓库三&borrowerTime=2018-03-23&endTime=2018-06-28",success="{'code':0,'count':1,'data':{'borrowOrderId':2,'borrowerId':1,'borrowerName':'何欢','borrowerStatus':1,'borrowerTime':'2018-03-23 00:00:00',"
					+ "'endTime':'2018-06-28 00:00:00','explains':'','goodsAllo':'仓库三','id':1,"
					+ "'imprestListId':1,'imprestOrderId':1,'insertTime':'2018-03-23 15:06:30',"
					+ "'leaderId':1,'leaderName':'何欢','operUid':1,'operUname':'何欢','partRemark':null,"
					+ "'status':null,'toolCode':'T00001','toolTypeName':'1','toolTypeNum':'TC001',"
					+ "'updateTime':null},'msg':'借还清单创建成功'}"))
	@RequestMapping(value = "/add.json",method = RequestMethod.POST)
	public @ResponseBody Result addBorrowList(@RequestParam(value = "imprestOrderId",required = true) Integer imprestOrderId,
			@RequestParam(value = "imprestListId",required = true) Integer imprestListId,
			@RequestParam(value = "borrowOrderId",required = true) Integer borrowOrderId,
			@RequestParam(value = "toolCode",required = true) String toolCode,
			@RequestParam(value = "goodsAllo",required = true) String goodsAllo,
			@RequestParam(value = "borrowerTime",required = true) String borrowerTime,
			@RequestParam(value = "endTime",required = true) String endTime,
			HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		return borrowListService.addBorrowList(user.getId(), imprestOrderId, imprestListId, borrowOrderId, toolCode, goodsAllo, borrowerTime, endTime);
	}
	
	/**
	 * 更新借还清单
	 * @param borrowListId
	 * @param toolCode
	 * @param goodsAllo
	 * @param borrowerTime
	 * @param endTime
	 * @param request
	 * @return
	 */
	@Api(author="Jesson",
			createtime="2018-03-23",
			name="更新借还清单",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="borrowListId",desc="借还清单id"),
					@Rule(name="toolCode",desc="机具编码"),
					@Rule(name="goodsAllo",desc="机具具体货位"),
					@Rule(name="borrowerTime",desc="借出日期"),
					@Rule(name="endTime",desc="截止日期"),
					@Rule(name="borrowerStatus",desc="借还状态，1:未还,2:已还,3:更换并且维修,4:更换并且入库,5:挂失,6:建议报废,7:已还并且维修")
			},
			demo = @Demo(param="borrowListId=1&toolCode=T00002&goodsAllo=repost&borrowerTime=2018-03-23&endTime=2018-06-28",success="{'code':0,'count':1,'data':{'borrowOrderId':2,'borrowerId':1,'borrowerName':'何欢',"
					+ "'borrowerStatus':1,'borrowerTime':'2018-03-23 00:00:00','endTime':'2018-06-28 00:00:00','explains':'',"
					+ "'goodsAllo':'repost','id':1,'imprestListId':1,'imprestOrderId':1,'insertTime':'2018-03-23 15:06:31','leaderId':1,"
					+ "'leaderName':'何欢','operUid':1,'operUname':'何欢','partRemark':null,'status':0,'toolCode':'T00002',"
					+ "'toolTypeName':'','toolTypeNum':'TC001','updateTime':'2018-03-23 15:28:57'},'msg':'更新成功'}"))
	@RequestMapping(value = "/update.json",method = RequestMethod.POST)
	public @ResponseBody Result updateBorrowList(@RequestParam(value = "borrowListId",required = true) Integer borrowListId,
			@RequestParam(value = "toolCode",required = true) String toolCode,
			@RequestParam(value = "goodsAllo",required = true) String goodsAllo,
			@RequestParam(value = "borrowerTime",required = true) String borrowerTime,
			@RequestParam(value = "endTime",required = true) String endTime,
			@RequestParam(value = "borrowerStatus",required = false) Integer borrowerStatus,
			HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		return borrowListService.updateBorrowList(user.getId(), borrowListId, toolCode, goodsAllo, borrowerTime, endTime,borrowerStatus);
	}

	/**
	 * 删除借还清单
	 * @param borrowListId
	 * @param request
	 * @return
	 */
	@Api(author="Jesson",
			createtime="2018-03-23",
			name="删除借还清单",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="borrowListId",desc="借还清单id")
			},
			demo = @Demo(param="borrowListId=1",success="{'code':0,'count':0,'data':null,'msg':'删除借还清单成功！'}"))
	@RequestMapping(value = "/delete.json",method = RequestMethod.POST)
	public @ResponseBody Result deleteBorrowList(@RequestParam(value = "borrowListId",required = true) Integer borrowListId,
			HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		return borrowListService.deleteBorrowList(user.getId(), borrowListId);
	}
	
	/**
	 * 清单列表
	 * @param borrowOrderId
	 * @param page
	 * @param limit
	 * @param request
	 * @return
	 */
	@Api(author="Jesson",
			createtime="2018-03-23",
			name="清单列表",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="borrowOrderId",desc="借还单id"),
					@Rule(name="page",desc="起始页"),
					@Rule(name="limit",desc="每页显示的条数")
			},
			demo = @Demo(param="borrowOrderId=2",success="{'code':0,'count':1,'data':{'borrowOrderId':2,'borrowerId':1,'borrowerName':'何欢',"
					+ "'borrowerStatus':1,'borrowerTime':'2018-03-23 00:00:00','endTime':'2018-06-28 00:00:00','explains':'',"
					+ "'goodsAllo':'repost','id':1,'imprestListId':1,'imprestOrderId':1,'insertTime':'2018-03-23 15:06:31','leaderId':1,"
					+ "'leaderName':'何欢','operUid':1,'operUname':'何欢','partRemark':null,'status':0,'toolCode':'T00002',"
					+ "'toolTypeName':'','toolTypeNum':'TC001','updateTime':'2018-03-23 15:28:57'},'msg':'成功'}"))
	@RequestMapping(value = "/datas.json",method = RequestMethod.POST)
	public @ResponseBody Result getBorrowList(@RequestParam(value = "borrowOrderId",required = true) Integer borrowOrderId,
			@RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
			@RequestParam(value = "limit",required = false,defaultValue = "15") Integer limit,
			HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		return borrowListService.getBorrowList(user.getId(), borrowOrderId, page, limit);
	}
	
	/**
	 * 借还清单详情
	* @Title: getBorrowListDaetail 
	* @Description: TODO
	* @param @param id
	* @param @param request
	* @param @return 
	* @return Result
	* @throws
	 */
	@Api(author="Jesson",
			createtime="2018-04-09",
			name="借还清单详情",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="id",desc="借还清单id")
			},
			demo = @Demo(param="id=2",success="{'code':0,'count':1,'data':{'borrowOrderId':2,'borrowerId':1,'borrowerName':'何欢',"
					+ "'borrowerStatus':1,'borrowerTime':'2018-03-23 00:00:00','endTime':'2018-06-28 00:00:00','explains':'',"
					+ "'goodsAllo':'repost','id':1,'imprestListId':1,'imprestOrderId':1,'insertTime':'2018-03-23 15:06:31','leaderId':1,"
					+ "'leaderName':'何欢','operUid':1,'operUname':'何欢','partRemark':null,'status':0,'toolCode':'T00002',"
					+ "'toolTypeName':'','toolTypeNum':'TC001','updateTime':'2018-03-23 15:28:57'},'msg':'成功'}"))
	@RequestMapping(value = "/detail.json",method = RequestMethod.POST)
	public Result getBorrowListDaetail(@RequestParam(value = "id",required = true) Integer id,HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		return borrowListService.getBorrowListDetail(user.getId(), id);
	}
	
}
