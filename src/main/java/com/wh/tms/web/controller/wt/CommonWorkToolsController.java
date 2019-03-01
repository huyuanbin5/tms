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
 * 通用工机具管理
* @ClassName: CommonWorkToolsController 
* @Description: TODO 
* @author Huqk
* @date 2018年4月30日 下午1:53:43
 */
@Controller
@RequestMapping("/common/work/tools")
@Domain(desc="通用工机具管理")
public class CommonWorkToolsController extends BaseController {
	
	private static final Log log = LogFactory.getLog(CommonWorkToolsController.class);
	
	@Autowired
	private IWorkToolsService workToolsService;
	
	@Api(author="Jesson",
			createtime="2018-04-30",
			name="通用工机具列表",
			desc="",
			auth = {
					@Auth(checkEnc = true)
			},
			params={
					@Rule(name="id",desc="机具id"),
					@Rule(name="managerCategoryId",desc="机具管理类别id"),
					@Rule(name="categoryId",desc="机具种类id"),
					@Rule(name="toolsCode",desc="机具编码"),
					@Rule(name="supplierId",desc="供应商id"),
					@Rule(name="manufacturerId",desc="制造商id"),
					@Rule(name="status",desc="机具状态0:正常,1:维修中,2:检验中,3:借出,4:挂失,5:建议报废,6:确认丢失,7:确认报废,8:非发放状态"),
					@Rule(name="page",desc="起始页"),
					@Rule(name="limit",desc="每页显示的条数,不传默认每页显示15条")
			},
			demo = @Demo(param = "managerCategoryId=46",success="{'code':0,'count':1,'data':[{'addCount':1,'brand':'test','categoryId':3,'categoryName':'磁力切割机[规格:空]',"
					+ "'categoryNumber':'Z001','deptId':1,'deptName':'建造公司','evaluation':90,'factoryNumber':'111','id':3,'inStorageTime':'2018-04-30',"
					+ "'insertTime':'2018-04-30 16:35:26','managerCategoryId':46,'managerCategoryName':'专用类 (Z)','manufacturerId':1,'manufacturerName':'BOSCH（博世）',"
					+ "'remark':null,'repositoryId':8,'repositoryName':'[1号仓库] 电动工具库/[01-1]  一号架/[8012] 2层','status':0,'supplierId':31,'supplierName':'航威机电设备有限公司',"
					+ "'toolsCode':'T09001363','uid':1,'userName':'何欢','yearLimit':'1'}],'msg':'成功'}"))
	@RequestMapping(value = "/list.json",method = RequestMethod.POST)
	public @ResponseBody Result getCommonWorkToolsList(
			@RequestParam(value = "id",required = false) Integer id,
			@RequestParam(value = "managerCategoryId",required = false) Integer managerCategoryId,
			@RequestParam(value = "categoryId",required = false) Integer categoryId,
			@RequestParam(value = "toolsCode",required = false) String toolsCode,
			@RequestParam(value = "supplierId",required = false) Integer supplierId,
			@RequestParam(value = "status",required = false) Integer status,
			@RequestParam(value = "manufacturerId",required = false) Integer manufacturerId,
			@RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
			@RequestParam(value = "limit",required = false,defaultValue = "15") Integer limit,
			HttpServletRequest request) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return workToolsService.getCommonWorkToolsList(id,managerCategoryId, categoryId, toolsCode, supplierId, manufacturerId, status, page, limit);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("获取机具列表失败!");
	}
	
	@Api(author="Jesson",
			createtime="2018-04-30",
			name="删除工机具",
			desc="",
			auth = {
					@Auth(checkEnc = true)
			},
			params={
					@Rule(name="id",desc="机具id")
			},
			demo = @Demo(param = "id=1",success="{'code':0,'count':1,'data':null,'msg':'删除成功!'}"))
	@RequestMapping(value = "/delete.json",method = RequestMethod.POST)
	public @ResponseBody Result deleteWorkToolsList(
			@RequestParam(value = "id",required = false) Integer id,HttpServletRequest request) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return workToolsService.deleteWorkTools(id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("删除机具失败!");
	}
	
	@Api(author="Jesson",
			createtime="2018-04-30",
			name="新增通用工机具",
			desc="补充:供应商信息、制造商信息、机具管理类别通过字典接口获取",
			auth = {
					@Auth(checkEnc = true)
			},
			params={
					@Rule(name="managerCategoryId",desc="机具管理类别id"),
					@Rule(name="categoryId",desc="机具种类id"),
					@Rule(name="deptId",desc="单位部门id"),
					@Rule(name="repositoryId",desc="库房货位id"),
					@Rule(name="supplierId",desc="供应商id"),
					@Rule(name="manufacturerId",desc="制造商id"),
					@Rule(name="factoryNumber",desc="厂家编号"),
					@Rule(name="brand",desc="品牌"),
					@Rule(name="toolsCode",desc="机具编码"),
					@Rule(name="yearLimit",desc="使用年限"),
					@Rule(name="evaluation",desc="价值"),
					@Rule(name="insertTime",desc="入库时间，格式yyyy-MM-dd HH:mm:ss"),
					@Rule(name="remark",desc="备注(utf-8编码)")
			},
			demo = @Demo(param = "managerCategoryId=46&categoryId=3&deptId=2&repositoryId=8&supplierId=31&manufacturerId=1&"
					+ "factoryNumber=GS102&brand=博世&toolsCode=T09001363&yearLimit=1&evaluation=1200&insertTime=2018-04-30 16:35:26",
					success="{'code':0,'count':1,'data':{'addCount':1,'brand':'博世','categoryId':3,'categoryName':'磁力切割机[规格:空]','categoryNumber':'Z001',"
							+ "'deptId':2,'deptName':'结构车间','evaluation':1200,'factoryNumber':'GS102','id':3,'inStorageTime':'2018-04-30','insertTime':'2018-04-30 16:35:26',"
							+ "'managerCategoryId':46,'managerCategoryName':'专用类 (Z)','manufacturerId':1,'manufacturerName':'BOSCH（博世）','remark':null,'repositoryId':8,"
							+ "'repositoryName':'[1号仓库] 电动工具库/[01-1]  一号架/[8012] 2层','status':null,"
							+ "'supplierId':31,'supplierName':'航威机电设备有限公司','toolsCode':'T09001363','uid':1,'userName':'何欢','yearLimit':'1'},'msg':'成功'}"))
	@RequestMapping(value = "/add.json",method = RequestMethod.POST)
	public @ResponseBody Result addCommonWorkToolsList(
			@RequestParam(value = "managerCategoryId",required = false) Integer managerCategoryId,
			@RequestParam(value = "categoryId",required = true) Integer categoryId,
			@RequestParam(value = "deptId",required = true) Integer deptId,
			@RequestParam(value = "repositoryId",required = false) Integer repositoryId,
			@RequestParam(value = "manufacturerId",required = false) Integer manufacturerId,
			@RequestParam(value = "supplierId",required = false) Integer supplierId,
			@RequestParam(value = "factoryNumber",required = false) String factoryNumber,
			@RequestParam(value = "brand",required = false) String brand,
			@RequestParam(value = "toolsCode",required = false) String toolsCode,
			@RequestParam(value = "yearLimit",required = false) String yearLimit,
			@RequestParam(value = "evaluation",required = false) Integer evaluation,
			@RequestParam(value = "insertTime",required = true) String insertTime,
			@RequestParam(value = "remark",required = false) String remark,
			HttpServletRequest request) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return workToolsService.addCommonWorkTools(user.getId(), managerCategoryId, categoryId, deptId, repositoryId, manufacturerId, supplierId, factoryNumber, brand, toolsCode,
					yearLimit, evaluation, insertTime, remark);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("新增机具失败!");
	}
	
	@Api(author="Jesson",
			createtime="2018-04-30",
			name="更新工机具信息",
			desc="补充:供应商信息、制造商信息、机具管理类别通过字典接口获取",
			auth = {
					@Auth(checkEnc = true)
			},
			params={
					@Rule(name="id",desc="机具id"),
					@Rule(name="managerCategoryId",desc="机具管理类别id"),
					@Rule(name="deptId",desc="单位部门id"),
					@Rule(name="repositoryId",desc="库房货位id"),
					@Rule(name="supplierId",desc="供应商id"),
					@Rule(name="manufacturerId",desc="制造商id"),
					@Rule(name="factoryNumber",desc="厂家编号"),
					@Rule(name="brand",desc="品牌"),
					@Rule(name="toolsCode",desc="机具编码"),
					@Rule(name="yearLimit",desc="使用年限"),
					@Rule(name="evaluation",desc="价值"),
					@Rule(name="remark",desc="备注(utf-8编码)"),
					@Rule(name="status",desc="状态0:正常,1:维修中,2:检验中,3:借出,4:挂失,5:建议报废,6:确认丢失,7:确认报废,8:非发放状态")
			},
			demo = @Demo(param = "id=3&managerCategoryId=46&deptId=1&supplierId=31&manufacturerId=1&brand=test&toolsCode=T09001363&yearLimit=1&evaluation=90",
			success="{'code':0,'count':1,'data':{'addCount':1,'brand':'test','categoryId':3,'categoryName':'磁力切割机[规格:空]',"
					+ "'categoryNumber':'Z001','deptId':1,'deptName':'建造公司','evaluation':90,'factoryNumber':'111','id':3,'inStorageTime':'2018-04-30',"
					+ "'insertTime':'2018-04-30 16:35:26','managerCategoryId':46,'managerCategoryName':'专用类 (Z)','manufacturerId':1,'manufacturerName':'BOSCH（博世）',"
					+ "'remark':null,'repositoryId':8,'repositoryName':'[1号仓库] 电动工具库/[01-1]  一号架/[8012] 2层','status':0,'supplierId':31,'supplierName':'航威机电设备有限公司',"
					+ "'toolsCode':'T09001363','uid':1,'userName':'何欢','yearLimit':'1'},'msg':'成功'}"))
	@RequestMapping(value = "/update.json",method = RequestMethod.POST)
	public @ResponseBody Result updateCommonWorkToolsList(
			@RequestParam(value = "id",required = true) Integer id,
			@RequestParam(value = "managerCategoryId",required = false) Integer managerCategoryId,
			@RequestParam(value = "deptId",required = true) Integer deptId,
			@RequestParam(value = "repositoryId",required = false) Integer repositoryId,
			@RequestParam(value = "manufacturerId",required = false) Integer manufacturerId,
			@RequestParam(value = "supplierId",required = false) Integer supplierId,
			@RequestParam(value = "factoryNumber",required = false) String factoryNumber,
			@RequestParam(value = "brand",required = false) String brand,
			@RequestParam(value = "toolsCode",required = false) String toolsCode,
			@RequestParam(value = "yearLimit",required = false) String yearLimit,
			@RequestParam(value = "evaluation",required = false) Integer evaluation,
			@RequestParam(value = "remark",required = false) String remark,
			@RequestParam(value = "status",required = false) Integer status,
			HttpServletRequest request) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return workToolsService.updateCommonWorkTools(id, managerCategoryId, deptId, repositoryId, manufacturerId, supplierId, factoryNumber, brand, toolsCode, yearLimit, evaluation, remark, status);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("更新机具信息失败!");
	}

}
