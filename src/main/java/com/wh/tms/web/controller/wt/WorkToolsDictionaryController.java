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
import com.wh.tms.service.IWorkToolsDictionaryService;
import com.wh.tms.util.ResultUtils;
import com.wh.tms.web.controller.BaseController;

/**
 * 工机具字典
* @ClassName: WorkToolsDictionaryController 
* @Description: TODO 
* @author Huqk
* @date 2018年4月29日 下午11:39:18
 */
@Controller
@RequestMapping("/work/tools/dictionary")
@Domain(desc="工机具管理-字典")
public class WorkToolsDictionaryController extends BaseController {
	
	private static final Log log = LogFactory.getLog(WorkToolsDictionaryController.class);
	
	@Autowired
	private IWorkToolsDictionaryService workToolsDictionaryService;

	@Api(author="Jesson",
			createtime="2018-04-30",
			name="添加字典",
			desc="",
			auth = {
					@Auth(checkEnc = true)
			},
			params={
					@Rule(name="type",desc="类型,1:制造商,2:供应商,3:机具管理类别,4:维修类别,5:需要维修内容，6：维修人维修对策，7：验收内容，8：验收评语，9：保养内容"),
					@Rule(name="name",desc="名称(utf-8编码)")
			},
			demo = @Demo(param="type=1&name=test",success="{'code':0,'count':1,'data':{'id':44,'name':'test','type':1},'msg':'成功'}"))
	@RequestMapping(value = "/add.json",method = RequestMethod.POST)
	public @ResponseBody Result addWorkToolsDictionary(@RequestParam(value = "type",required = true) Integer type,
			@RequestParam(value = "name",required = true) String name,
			HttpServletRequest request) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return workToolsDictionaryService.addDictionary(type, name);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("添加字典失败!");
	}
	
	@Api(author="Jesson",
			createtime="2018-04-30",
			name="字典列表",
			desc="",
			auth = {
					@Auth(checkEnc = true)
			},
			params={
					@Rule(name="type",desc="类型,1:制造商,2:供应商,3:机具管理类别,4:维修类别,5:需要维修内容，6：维修人维修对策，7：验收内容，8：验收评语，9：保养内容")
			},
			demo = @Demo(param="type=2",success="{'code':0,'count':13,'data':[{'id':31,'name':'航威机电设备有限公司','type':2},{'id':32,'name':'天亚五金机电有限公司','type':2},"
					+ "{'id':33,'name':'天赢机电设备进出口贸易','type':2},"
					+ "{'id':34,'name':'金源升船舶设备工程有限公司','type':2},"
					+ "{'id':35,'name':'嘉华伟业科工贸有限公司','type':2},"
					+ "{'id':36,'name':'天津欣方盛国际贸易有限公司','type':2},"
					+ "{'id':37,'name':'世鑫铁船舶物资有限公司','type':2},"
					+ "{'id':38,'name':'航威达机电','type':2},"
					+ "{'id':39,'name':'回收','type':2},"
					+ "{'id':40,'name':'北京泰莱斯达','type':2},"
					+ "{'id':41,'name':'瑞兴机电工具设备有限公司','type':2},"
					+ "{'id':42,'name':'亚达工贸有限公司','type':2},"
					+ "{'id':43,'name':'哈恩库博（天津）国贸有限公司','type':2}],'msg':'成功'}"))
	@RequestMapping(value = "/list.json",method = RequestMethod.POST)
	public @ResponseBody Result getWorkToolsDictionaryList(@RequestParam(value = "type",required = false) Integer type,
			HttpServletRequest request) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return workToolsDictionaryService.getDictionaryList(type);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("查询失败！");
	}
	
	@Api(author="Jesson",
			createtime="2018-04-30",
			name="更新字典",
			desc="",
			auth = {
					@Auth(checkEnc = true)
			},
			params={
					@Rule(name="id",desc="字典id"),
					@Rule(name="type",desc="类型,1:制造商,2:供应商,3:机具管理类别,4:维修类别,5:需要维修内容，6：维修人维修对策，7：验收内容，8：验收评语，9：保养内容"),
					@Rule(name="name",desc="名称(utf-8编码)")
			},
			demo = @Demo(param="id=44&type=2&name=test",success="{'code':0,'count':1,'data':{'id':44,'name':'test','type':2},'msg':'成功'}"))
	@RequestMapping(value = "/update.json",method = RequestMethod.POST)
	public @ResponseBody Result updateWorkToolsDictionary(@RequestParam(value = "id",required = true) Integer id,
			@RequestParam(value = "type",required = false) Integer type,
			@RequestParam(value = "name",required = false) String name,
			HttpServletRequest request) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return workToolsDictionaryService.updateDictionary(id, type, name);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("更新字典失败!");
	}
	
	@Api(author="Jesson",
			createtime="2018-04-30",
			name="删除字典",
			desc="",
			auth = {
					@Auth(checkEnc = true)
			},
			params={
					@Rule(name="id",desc="字典id"),
			},
			demo = @Demo(param="id=44",success="{'code':0,'count':1,'data':null,'msg':'删除成功！'}"))
	@RequestMapping(value = "/delete.json",method = RequestMethod.POST)
	public @ResponseBody Result deleteWorkToolsDictionary(@RequestParam(value = "id",required = true) Integer id,
			HttpServletRequest request) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return workToolsDictionaryService.deleteDictionary(id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("删除字典失败!");
	}
	
}
