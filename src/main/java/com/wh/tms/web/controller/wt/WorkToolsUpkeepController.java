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
import com.wh.tms.service.IWorkToolsUpkeepService;
import com.wh.tms.util.ResultUtils;
import com.wh.tms.web.controller.BaseController;

/**
 * 机具定期保养
* @ClassName: WorkToolsUpkeepController 
* @Description: TODO 
* @author Huqk
* @date 2018年5月5日 下午2:24:33
 */
@Controller
@RequestMapping("/work/tools/upkeep")
@Domain(desc="定期保养")
public class WorkToolsUpkeepController extends BaseController {

	private static final Log log = LogFactory.getLog(WorkToolsUpkeepController.class);
	
	@Autowired
	private IWorkToolsUpkeepService workToolsUpkeepService;
	
	@Api(author="Jesson",
			createtime="2018-05-03",
			name="添加机具保养单",
			desc="注意：toolsId和toolsCode不能同时为空,机具保养内容id通过字典接口获取",
			auth = {
					@Auth(checkEnc = true)
			},
			params={
					@Rule(name="toolsId",desc="机具id"),
					@Rule(name="toolsCode",desc="机具编码"),
					@Rule(name="managerCategoryId",desc="机具管理类别id"),
					@Rule(name="categoryId",desc="机具分类id"),
					@Rule(name="operUid",desc="保养人员id"),
					@Rule(name="operConId",desc="机具保养内容id"),
					@Rule(name="planDate",desc="计划保养时间(格式：yyyy-MM-dd)")
			},
			demo = @Demo(param="toolsId=3&managerCategoryId=46&categoryId=3&operUid=1&operConId=86&planDate=2018-05-09",success="{'code':0,'count':1,'data':{'categoryId':3,"
					+ "'categoryName':'磁力切割机[规格:空]','categoryNumber':'Z001','id':2,'insertTime':'2018-05-05 14:40:33','managerCategoryId':46,'managerCategoryName':'专用类 (Z)',"
					+ "'operCon':'润滑','operConId':86,'operTime':null,'operUid':1,'operUname':'张森',"
					+ "'planDate':'2018-05-09 00:00:00','status':0,'toolsCode':'T09001363','toolsId':3},'msg':'添加保养单成功!'}"))
	@RequestMapping(value = "/add.json",method = RequestMethod.POST)
	public @ResponseBody Result addMaintenance(@RequestParam(value = "toolsId",required = false) Integer toolsId,
			@RequestParam(value = "toolsCode",required = false) String toolsCode,
			@RequestParam(value = "managerCategoryId",required = true) Integer managerCategoryId,
			@RequestParam(value = "categoryId",required = true) Integer categoryId,
			@RequestParam(value = "operUid",required = true) Integer operUid,
			@RequestParam(value = "operConId",required = true) Integer operConId,
			@RequestParam(value = "planDate",required = true) String planDate,
			HttpServletRequest request
			) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return workToolsUpkeepService.addWorkToolsUpkeep(toolsId, toolsCode, managerCategoryId, categoryId, operUid, operConId, planDate);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("添加失败!");
	}
	
	@Api(author="Jesson",
			createtime="2018-05-05",
			name="更新机具保养单",
			desc="注意：机具保养内容等相关id通过字典接口获取。",
			auth = {
					@Auth(checkEnc = true)
			},
			params={
					@Rule(name="orderId",desc="保养单id"),
					@Rule(name="operUid",desc="保养人员id"),
					@Rule(name="operConId",desc="保养内容id"),
					@Rule(name="status",desc="维修状态标识,0:未保养,1:已保养"),
			},
			demo = @Demo(param="orderId=2&status=1",success="{'code':0,'count':1,'data':{'categoryId':3,'categoryName':'磁力切割机[规格:空]',"
					+ "'categoryNumber':'Z001','id':2,'insertTime':'2018-05-05 14:40:33','managerCategoryId':46,'managerCategoryName':'专用类 (Z)',"
					+ "'operCon':'润滑','operConId':86,'operTime':'2018-05-05 00:00:00','operUid':1,"
					+ "'operUname':'张森','planDate':'2018-05-09 00:00:00','status':1,'toolsCode':'T09001363','toolsId':3},'msg':'更新保养单成功！'}"))
	@RequestMapping(value = "/update.json",method = RequestMethod.POST)
	public @ResponseBody Result updateMaintenance(@RequestParam(value = "orderId",required = false) Integer orderId,
			@RequestParam(value = "operUid",required = false) Integer operUid,
			@RequestParam(value = "operConId",required = false) Integer operConId,
			@RequestParam(value = "status",required = false) Integer status,
			HttpServletRequest request
			) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return workToolsUpkeepService.updateWorkToolsUpkeep(orderId, operUid, operConId, status);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("更新失败!");
	}

	@Api(author="Jesson",
			createtime="2018-05-05",
			name="机具保养列表",
			desc="",
			auth = {
					@Auth(checkEnc = true)
			},
			params={
					@Rule(name="orderId",desc="保养单id"),
					@Rule(name="managerCategoryId",desc="机具管理类别id"),
					@Rule(name="categoryId",desc="机具种类id"),
					@Rule(name="status",desc="机具状态标识 0:未保养,1:已保养"),
					@Rule(name="startDate",desc="起始日期,格式：yyyy-MM-dd"),
					@Rule(name="endDate",desc="截止日期,格式：yyyy-MM-dd"),
					@Rule(name="page",desc="起始页"),
					@Rule(name="limit",desc="每页显示的条数")
			},
			demo = @Demo(param="orderId=2&startDate=2018-05-04&endDate=2018-05-10",success="{'code':0,'count':1,'data':{'categoryId':3,'categoryName':'磁力切割机[规格:空]',"
					+ "'categoryNumber':'Z001','id':2,'insertTime':'2018-05-05 14:40:33','managerCategoryId':46,'managerCategoryName':'专用类 (Z)',"
					+ "'operCon':'润滑','operConId':86,'operTime':'2018-05-05 00:00:00','operUid':1,"
					+ "'operUname':'张森','planDate':'2018-05-09 00:00:00','status':1,'toolsCode':'T09001363','toolsId':3},'msg':'成功！'}"))
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
			return workToolsUpkeepService.getWorkToolsUpkeepList(orderId, managerCategoryId, categoryId, status, startDate, endDate, page, limit);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("获取列表失败!");
	}
	@Api(author="Jesson",
			createtime="2018-05-05",
			name="删除保养单",
			desc="",
			auth = {
					@Auth(checkEnc = true)
			},
			params={
					@Rule(name="orderId",desc="保养单id"),
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
			return workToolsUpkeepService.deleteUpkeepOrder(orderId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("删除失败!");
	}
}
