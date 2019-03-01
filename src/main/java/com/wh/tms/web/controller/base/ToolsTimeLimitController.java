package com.wh.tms.web.controller.base;

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
import com.wh.tms.service.IToolsTimeLimitService;
import com.wh.tms.util.ResultUtils;
import com.wh.tms.web.controller.BaseController;


@Controller
@RequestMapping("/base/tools/timelimit")
@Domain(desc="基础数据-机具时限管理")
public class ToolsTimeLimitController extends BaseController {
	
	private static final Log log = LogFactory.getLog(ToolsTimeLimitController.class);
	
	@Autowired
	private IToolsTimeLimitService toolsTimeLimitService;
	
	
	@Api(author="Jesson",
			createtime="2018-04-29",
			name="机具时限列表",
			desc="",
			params={
					@Rule(name="id",desc="通过id获取详情"),
					@Rule(name="category",desc="类别1:吊具类,2:通用类,3:专用类,4:个人工机具")
			},
			auth = {
				@Auth(checkEnc = true)
			},
			demo = @Demo(param="",success="{'code':0,'count':12,'data':[{'categoryName':'通用类(T)','categoryType':2,'id':1,'jobName':'本地作业','limitTime':3},"
					+ "{'categoryName':'通用类(T)','categoryType':2,'id':2,'jobName':'外埠作业','limitTime':6},"
					+ "{'categoryName':'通用类(T)','categoryType':2,'id':3,'jobName':'出海作业','limitTime':6},"
					+ "{'categoryName':'专用类(Z)','categoryType':3,'id':4,'jobName':'本地作业','limitTime':3},"
					+ "{'categoryName':'通用类(T)','categoryType':3,'id':5,'jobName':'外埠作业','limitTime':6},"
					+ "{'categoryName':'通用类(T)','categoryType':3,'id':6,'jobName':'出海作业','limitTime':6},"
					+ "{'categoryName':'吊具类(D)','categoryType':1,'id':7,'jobName':'本地作业','limitTime':3},"
					+ "{'categoryName':'吊具类(D)','categoryType':1,'id':8,'jobName':'外埠作业','limitTime':6},"
					+ "{'categoryName':'吊具类(D)','categoryType':1,'id':9,'jobName':'出海作业','limitTime':6},"
					+ "{'categoryName':'个人工机具(G)','categoryType':4,'id':10,'jobName':'本地作业','limitTime':3},"
					+ "{'categoryName':'个人工机具(G)','categoryType':4,'id':11,'jobName':'外埠作业','limitTime':6},"
					+ "{'categoryName':'个人工机具(G)','categoryType':4,'id':12,'jobName':'出海作业','limitTime':6}],'msg':'获取列表成功！'}"))
	@RequestMapping(value = "/list.json",method = RequestMethod.POST)
	public @ResponseBody Result getToolsTimeLimitList(@RequestParam(value = "id",required = false) Integer id,
			@RequestParam(value = "category",required = false) Integer category,
			HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return toolsTimeLimitService.getToolsTimeLimitList(id,category);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("获取机具时限列表失败！");
	}
	
	@Api(author="Jesson",
			createtime="2018-04-29",
			name="更新机具时限",
			desc="",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="timelimits",desc="json字符串utf-8编码,格式：[{'id':1,'limit':3},{'id':2,'limit':6},{'id':3,'limit':6}]")
			},
			demo = @Demo(param="timelimits=%5b%7b%22id%22%3a1%2c%22limit%22%3a3333%7d%2c%7b%22id%22%3a2%2c%22limit%22%3a6%7d%2c%7b%22id%22%3a3%2c%22limit%22%3a6%7d%5d",success="{'code':0,'count':3,'data':[{'categoryName':'通用类(T)','categoryType':2,'id':1,'jobName':'本地作业','limitTime':3333},"
					+ "{'categoryName':'通用类(T)','categoryType':2,'id':2,'jobName':'外埠作业','limitTime':6},{'categoryName':'通用类(T)','categoryType':2,'id':3,'jobName':'出海作业','limitTime':6}],'msg':'更新成功!'}"))
	@RequestMapping(value = "/update.json",method = RequestMethod.POST)
	public @ResponseBody Result updateToolsTimeLimit(@RequestParam(value = "timelimits",required = true) String timelimits,HttpServletRequest request) {
		
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return toolsTimeLimitService.updateTimelimit(timelimits);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("更新机具时限失败！");
	}

}
