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
import com.wh.tms.service.IToolsCategoryService;
import com.wh.tms.util.ResultUtils;
import com.wh.tms.web.controller.BaseController;

@Controller
@RequestMapping("/work/tool/category")
@Domain(desc="机具分类")
public class WorkToolsCategoryController extends BaseController{
	
	private static final Log log = LogFactory.getLog(WorkToolsCategoryController.class);
	
	@Autowired
	private IToolsCategoryService toolsCategoryService;
	
	
	@Api(author="Jesson",
			createtime="2018-04-14",
			name="机具种类",
			desc="查询机具种类",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="managerCategoryId",desc="机具管理类别id")
			},
			demo = @Demo(param="categoryType=4",success="{'code':0,'count':1,'data':[{'categoryName':'90°角尺[规格:150×300]',"
					+ "'categoryNumber':'G005','categoryType':4,'id':9,'insertTime':'2018-04-14 16:24:30','inventoryCount':1}],'msg':'成功'}"))
	@RequestMapping(value = "/list.json",method = RequestMethod.POST)
	public @ResponseBody Result getToolsCategoryList(@RequestParam(value = "managerCategoryId",required = false) Integer managerCategoryId,HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return toolsCategoryService.getToolsCategoryList(managerCategoryId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("获取机具分类失败!");
	}

}
