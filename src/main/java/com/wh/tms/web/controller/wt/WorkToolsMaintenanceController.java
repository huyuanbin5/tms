package com.wh.tms.web.controller.wt;

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
import com.wh.tms.service.IWorkToolsMaintenanceService;
import com.wh.tms.util.ResultUtils;
import com.wh.tms.web.controller.BaseController;

/**
 * 机具维修
* @ClassName: WorkToolsMaintenanceController 
* @Description: TODO 
* @author Huqk
* @date 2018年5月5日 上午11:55:10
 */
@Controller
@RequestMapping("/work/tools/maintenance")
@Domain(desc="机具维修管理")
public class WorkToolsMaintenanceController extends BaseController {
	
	private static final Log log = LogFactory.getLog(WorkToolsMaintenanceController.class);
	
	@Autowired
	private IWorkToolsMaintenanceService workToolsMaintenanceService;
	
	
	@Api(author="Jesson",
			createtime="2018-05-03",
			name="添加机具维修单",
			desc="注意：toolsId和toolsCode不能同时为空,机具维修类别、机具维系内容相关id通过字典接口获取",
			auth = {
					@Auth(checkEnc = true)
			},
			params={
					@Rule(name="toolsId",desc="机具id"),
					@Rule(name="toolsCode",desc="机具编码"),
					@Rule(name="managerCategoryId",desc="机具管理类别id"),
					@Rule(name="categoryId",desc="机具分类id"),
					@Rule(name="borrowerUid",desc="机具借用人id"),
					@Rule(name="mUid",desc="机具维修人员id"),
					@Rule(name="sCategoryId",desc="机具维修类别id"),
					@Rule(name="sConId",desc="机具维修内容id")
			},
			demo = @Demo(param="toolsId=2&managerCategoryrId=46&categoryId=3&borrowerUid=1&mUid=2&sCategoryId=58&sConId=73",success="{'code':0,'count':1,'data':{'aAssess':null,'aAssessId':null,'aCon':null,"
					+ "'aConId':null,'aTime':null,'borrowerName':'张森','borrowerUid':1,'categoryId':3,'categoryName':'磁力切割机[规格:空]','categoryNumber':'Z001','checkUid':null,'checkUsername':null,'id':1,'insertTime':'2018-05-05 12:20:41','mPolicyId':null,'mPolicyName':null,'mTime':null,'mUid':2,'mUname':'张淼','managerCategoryId':46,'managerCategoryName':'专用类 (Z)','planDate':'2018-05-05 12:20:41','sCategoryId':58,'sCategoryName':'电工',"
					+ "'sCon':'碳刷','sConId':73,'status':null,'toolsCode':'T09001363','toolsId':2},'msg':'添加维修单成功!'}"))
	@RequestMapping(value = "/add.json",method = RequestMethod.POST)
	public @ResponseBody Result addMaintenance(@RequestParam(value = "toolsId",required = false) Integer toolsId,
			@RequestParam(value = "toolsCode",required = false) String toolsCode,
			@RequestParam(value = "managerCategoryId",required = true) Integer managerCategoryId,
			@RequestParam(value = "categoryId",required = true) Integer categoryId,
			@RequestParam(value = "borrowerUid",required = true) Integer borrowerUid,
			@RequestParam(value = "mUid",required = true) Integer mUid,
			@RequestParam(value = "sCategoryId",required = true) Integer sCategoryId,
			@RequestParam(value = "sConId",required = true) Integer sConId,
			HttpServletRequest request
			) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return workToolsMaintenanceService.addWorkToolsMaintenance(toolsId, toolsCode, managerCategoryId, categoryId, borrowerUid, mUid, sCategoryId, sConId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("添加失败!");
	}
	
	@Api(author="Jesson",
			createtime="2018-05-05",
			name="更新机具维修单",
			desc="注意：机具维修策略、验收内容等相关id通过字典接口获取。<br/>流程：维修单更新分为两部分，1：维修人填写维修意见 status参数的值传1或者-1，2：审核人确认维修结果",
			auth = {
					@Auth(checkEnc = true)
			},
			params={
					@Rule(name="orderId",desc="维修单id"),
					@Rule(name="mUid",desc="机具维修人员id"),
					@Rule(name="mPolicyId",desc="维修策略id"),
					@Rule(name="checkUid",desc="验收人uid"),
					@Rule(name="aConId",desc="验收内容id"),
					@Rule(name="aAssessId",desc="验收评价id"),
					@Rule(name="status",desc="维修状态标识,0:待处理,1:维修完毕待确认,2:维修结果确认完毕并重新入库,-1:建议报废"),
			},
			demo = @Demo(param="orderId=1&checkUid=4&aConId=83&status=2&aAssessId=84",success="{'code':0,'count':1,'data':{'aAssess':'合格',"
					+ "'aAssessId':84,'aCon':'试车三分钟','aConId':83,'aTime':null,'borrowerName':'张森','borrowerUid':1,'categoryId':3,'categoryName':'磁力切割机[规格:空]',"
					+ "'categoryNumber':'Z001','checkUid':4,'checkUsername':'黄健玲','id':1,'insertTime':'2018-05-05 12:20:42','mPolicyId':76,'mPolicyName':'零件更换','mTime':null,"
					+ "'mUid':2,'mUname':'张淼','managerCategoryId':46,'managerCategoryName':'专用类 (Z)','planDate':'2018-05-05 00:00:00',"
					+ "'sCategoryId':58,'sCategoryName':'电工','sCon':'碳刷','sConId':73,'status':2,'toolsCode':'T09001363','toolsId':2},'msg':'更新成功!'}"))
	@RequestMapping(value = "/update.json",method = RequestMethod.POST)
	public @ResponseBody Result updateMaintenance(@RequestParam(value = "orderId",required = false) Integer orderId,
			@RequestParam(value = "mUid",required = false) Integer mUid,
			@RequestParam(value = "checkUid",required = false) Integer checkUid,
			@RequestParam(value = "mPolicyId",required = false) Integer mPolicyId,
			@RequestParam(value = "aConId",required = false) Integer aConId,
			@RequestParam(value = "aAssessId",required = false) Integer aAssessId,
			@RequestParam(value = "status",required = false) Integer status,
			HttpServletRequest request
			) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return workToolsMaintenanceService.updateWorkToolsMaintenanc(orderId, mUid, checkUid, mPolicyId, aConId, aAssessId, status);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("更新失败!");
	}

	@Api(author="Jesson",
			createtime="2018-05-05",
			name="机具维修列表",
			desc="",
			auth = {
					@Auth(checkEnc = true)
			},
			params={
					@Rule(name="orderId",desc="维修单id"),
					@Rule(name="managerCategoryId",desc="机具管理类别id"),
					@Rule(name="categoryId",desc="机具种类id"),
					@Rule(name="status",desc="机具状态标识 0:待处理,1:维修完毕待确认,2:维修结果确认完毕并重新入库,-1:建议报废"),
					@Rule(name="startDate",desc="起始日期,格式：yyyy-MM-dd HH:mm:ss"),
					@Rule(name="endDate",desc="截止日期,格式：yyyy-MM-dd HH:mm:ss"),
					@Rule(name="page",desc="起始页"),
					@Rule(name="limit",desc="每页显示的条数")
			},
			demo = @Demo(param="categoryId=3",success="{'code':0,'count':1,'data':[{'aAssess':'合格','aAssessId':84,"
					+ "'aCon':'试车三分钟','aConId':83,'aTime':null,'borrowerName':'张森','borrowerUid':1,'categoryId':3,"
					+ "'categoryName':'磁力切割机[规格:空]','categoryNumber':'Z001','checkUid':4,'checkUsername':'黄健玲','id':1,"
					+ "'insertTime':'2018-05-05 12:20:42','mPolicyId':76,'mPolicyName':'零件更换','mTime':null,'mUid':2,'mUname':'张淼',"
					+ "'managerCategoryId':46,'managerCategoryName':'专用类 (Z)',"
					+ "'planDate':'2018-05-05 00:00:00','sCategoryId':58,'sCategoryName':'电工','sCon':'碳刷','sConId':73,'status':2,'toolsCode':'T09001363','toolsId':2}],'msg':'成功'}"))
	@RequestMapping(value = "/list.json",method = RequestMethod.POST)
	public @ResponseBody Result getMaintenanceList(@RequestParam(value = "orderId",required = false) Integer orderId,
			@RequestParam(value = "managerCategoryId",required = false) Integer managerCategoryId,
			@RequestParam(value = "categoryId",required = false) Integer categoryId,
			@RequestParam(value = "status",required = false) Integer status,
			@RequestParam(value = "startDate",required = false) String startDate,
			@RequestParam(value = "endDate",required = false) String endDate,
			@RequestParam(value = "page",required = false,defaultValue="1") Integer page,
			@RequestParam(value = "limit",required = false,defaultValue="15") Integer limit,
			HttpServletRequest request
			) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return workToolsMaintenanceService.getMaintenancList(orderId, managerCategoryId, categoryId, status, startDate, endDate, page, limit);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("获取列表失败!");
	}
	@Api(author="Jesson",
			createtime="2018-05-05",
			name="删除机具维修单",
			desc="",
			auth = {
					@Auth(checkEnc = true)
			},
			params={
					@Rule(name="orderId",desc="维修单id"),
			},
			demo = @Demo(param="orderId=1",success="{'code':0,'count':1,'data':null,'msg':'成功'}"))
	@RequestMapping(value = "/delete.json",method = RequestMethod.POST)
	public @ResponseBody Result updateMaintenance(@RequestParam(value = "orderId",required = false) Integer orderId,
			HttpServletRequest request
			) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return workToolsMaintenanceService.deleteMaintenanc(orderId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("删除失败!");
	}
}
