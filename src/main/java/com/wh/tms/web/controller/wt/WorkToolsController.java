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
import com.wh.tms.service.IWorkToolsService;
import com.wh.tms.util.ResultUtils;
import com.wh.tms.web.controller.BaseController;

/**
 * 工机具管理
* @ClassName: WorkToolController 
* @Description: TODO 
* @author Huqk
* @date 2018年2月26日 下午10:21:49
 */
@Controller
@RequestMapping("/work/tool")
@Domain(desc="个人工机具管理")
public class WorkToolsController extends BaseController{
	
	private static final Log log = LogFactory.getLog(WorkToolsController.class);
	
	@Autowired
	private IWorkToolsService workToolService;
	
	/**
	 * 添加工机具
	* @Title: addWorkTool 
	* @Description: TODO
	* @param @param categoryName
	* @param @param deptName
	* @param @param insertTime
	* @param @param addCount
	* @param @return 
	* @return Result
	* @throws
	 */
	@Api(author="Jesson",
			createtime="2018-03-02",
			name="添加工机具",
			desc="添加个人工机具",
			auth = {
					@Auth(checkEnc = true)
			},
			params={
					@Rule(name="categoryId",desc="机具分类id"),
					@Rule(name="deptId",desc="部门id"),
					@Rule(name="insertTime",desc="初次入库时间"),
					@Rule(name="addCount",desc="新增个数")
			},
			demo = @Demo(param="categoryId=5&deptId=1&insertTime=2018-04-14 17:55:23&addCount=20",success="{'code':0,'count':1,'data':{'addCount':20,'categoryId':5,'categoryName':'对讲机[规则:TK278G]',"
					+ "'categoryNumber':'G001','categoryType':4,'deptId':1,'deptName':'建造公司','id':3,'inStorageTime':'2018-04-14',"
					+ "'insertTime':'2018-04-14 17:55:23','status':null,'temp':null,'temp1':null,'temp2':null,'uid':1,'userName':'何欢'},'msg':'成功'}"))
	@RequestMapping(value = "/add.json",method = RequestMethod.POST)
	public @ResponseBody Result addWorkTool(@RequestParam("categoryId") Integer categoryId,
			@RequestParam("deptId") Integer deptId,
			@RequestParam("insertTime") String insertTime,
			@RequestParam("addCount") Integer addCount,HttpServletRequest request) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return workToolService.addWorkTool(user.getId(), categoryId, deptId, insertTime, addCount);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("添加机具失败!");
	}
	
	/**
	 * 机具基本信息
	* @Title: getBaseInfoList 
	* @Description: TODO
	* @param @param name
	* @param @param deptName
	* @param @param insertTime
	* @param @param page
	* @param @param limit
	* @param @return 
	* @return BaseResult
	* @throws
	 */
	@Api(author="Jesson",
			createtime="2018-03-02",
			name="个人工机具列表",
			desc="",
			auth = {
					@Auth(checkEnc = true)
			},
			params={
					@Rule(name="categoryName",desc="机具类别名称"),
					@Rule(name="deptId",desc="部门id"),
					@Rule(name="deptName",desc="部门名称"),
					@Rule(name="insertTime",desc="入库时间"),
					@Rule(name="page",desc="起始页"),
					@Rule(name="limit",desc="每页显示的条数,不传默认每页显示15条")
			},
			demo = @Demo(success="{'code':0,'count':1,'data':[{'addCount':20,'categoryId':5,'categoryName':'对讲机[规则:TK278G]','categoryNumber':'G001',"
					+ "'categoryType':4,'deptId':1,'deptName':'建造公司','id':3,'inStorageTime':'2018-04-14','insertTime':'2018-04-14 17:55:23','status':0,"
					+ "'temp':null,'temp1':null,'temp2':null,'uid':1,'userName':'何欢'}],'msg':'成功'}"))
	@RequestMapping(value = "/list.json",method = RequestMethod.POST)
	public @ResponseBody Result getBaseInfoList(
			@RequestParam(value = "categoryName",required = false) String categoryName,
			@RequestParam(value = "deptId",required = false) Integer deptId,
			@RequestParam(value = "deptName",required = false) String deptName,
			@RequestParam(value = "insertTime",required = false)  String insertTime,
			@RequestParam(value = "page",defaultValue = "1",required = false) Integer page,
			@RequestParam(value = "limit",defaultValue = "15",required = false) Integer limit,HttpServletRequest request) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return workToolService.getWorkToolsList(48,categoryName, deptId, deptName, insertTime, page, limit);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("查询失败！");
	}

	
}
