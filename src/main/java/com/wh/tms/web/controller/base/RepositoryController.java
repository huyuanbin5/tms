package com.wh.tms.web.controller.base;

import java.util.List;

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
import com.wh.tms.entity.vo.RepositoryVo;
import com.wh.tms.result.Result;
import com.wh.tms.service.IRepositoryService;
import com.wh.tms.util.ResultUtils;
import com.wh.tms.web.controller.BaseController;

/**
 * 仓库管理
* @ClassName: RepositoryManagerController 
* @Description: TODO 
* @author Huqk
* @date 2018年4月29日 上午11:54:29
 */
@Controller
@RequestMapping("/base/repository")
@Domain(desc="基础数据-仓库管理")
public class RepositoryController extends BaseController {
	
	private static final Log log = LogFactory.getLog(RepositoryController.class);
	
	@Autowired
	private IRepositoryService repositoryServices;
	
	
	@Api(author="Jesson",
			createtime="2018-04-29",
			name="获取仓库列表",
			desc="",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="id",desc="节点id")
			},
			demo = @Demo(param="",success="{'code':0,'count':4,'data':[{'children':[{'children':[{'children':null,'id':7,'level':3,'name':'[8011] 1层',"
					+ "'orderId':1,'pid':5,'xpath':'1/5/7'},{'children':null,'id':8,'level':3,'name':'[8012] 2层',"
					+ "'orderId':2,'pid':5,'xpath':'1/5/8'}],'id':5,'level':2,'name':'[01-1]  一号架','orderId':1,"
					+ "'pid':1,'xpath':'1/5'},{'children':null,'id':6,'level':2,'name':'[01-2] 二号架','orderId':2,'pid':1,"
					+ "'xpath':'1/6'}],'id':1,'level':1,'name':'[1号仓库] 电动工具库','orderId':1,'pid':0,'xpath':'1/'},"
					+ "{'children':null,'id':2,'level':1,'name':'[2号仓库] 风动工机具库','orderId':2,'pid':0,'xpath':'2/'},"
					+ "{'children':null,'id':3,'level':1,'name':'[3号仓库] 液压工具库','orderId':3,'pid':0,'xpath':'3/'},"
					+ "{'children':null,'id':4,'level':1,'name':'[4号仓库] 起重工具库','orderId':4,'pid':0,'xpath':'4/'}],'msg':'成功'}"))
	@RequestMapping(value = "/list.json",method = RequestMethod.POST)
	public @ResponseBody Result getList(@RequestParam(value = "id",required = false) Integer id,HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		List<RepositoryVo> list = repositoryServices.getGoodList(id);
		return ResultUtils.success(null == list ? 0 : list.size(), list);
	}
	
	@Api(author="Jesson",
			createtime="2018-04-29",
			name="添加仓库节点",
			desc="",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="pid",desc="父节点id,不传默认为0(根节点)"),
					@Rule(name="name",desc="节点名称(utf-8编码)")
			},
			demo = @Demo(param="pid=2&name=[02-2] 二号架",success="{'code':0,'count':1,'data':{'id':11,'insertTime':'2018-04-29 12:50:02','level':2,'name':'[02-2] 二号架','orderId':2,'pid':2,'xpath':'2/11'},'msg':'成功'}"))
	@RequestMapping(value = "/add.json",method = RequestMethod.POST)
	public @ResponseBody Result add(@RequestParam(value = "pid",required = false,defaultValue = "0") Integer pid,
			@RequestParam(value = "name",required = true) String name,HttpServletRequest request) {
		
		LoginUser loginUser = getSessionUser(request);
		if (null == loginUser) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录!"); 
		}
		
		try {
			return repositoryServices.addGoods(pid, name);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("添加失败！");
	}
	
	@Api(author="Jesson",
			createtime="2018-04-29",
			name="删除节点",
			desc="注意：删除节点会删除当前节点下所有节点.",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="id",desc="节点id")
			},
			demo = @Demo(param="id=12",success="{'code':0,'count':1,'data':'','msg':'删除成功!'}"))
	@RequestMapping(value = "/delete.json",method = RequestMethod.POST)
	public @ResponseBody Result delete(@RequestParam(value = "id",required = true) Integer id,HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return repositoryServices.deleteGoods(id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("删除失败!");
	}
	
	@Api(author="Jesson",
			createtime="2018-04-29",
			name="更新仓库信息",
			desc="",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="id",desc="节点id"),
					@Rule(name="name",desc="节点名称(utf-8编码)")
			},
			demo = @Demo(param="id=11&name=test",success="{'code':0,'count':1,'data':{'id':11,'insertTime':'2018-04-29 12:50:03','level':2,'name':'test','orderId':2,'pid':2,'xpath':'2/11'},'msg':'更新成功!'}"))
	@RequestMapping(value = "/update.json",method = RequestMethod.POST)
	public @ResponseBody Result update(@RequestParam(value = "id",required = true) Integer id,@RequestParam(value = "name",required = true) String name,HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		try {
			return repositoryServices.updateGoods(id, name);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.failure("更新信息失败!");
	}

}
