package com.wh.tms.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wh.tms.constans.SystemCons;
import com.wh.tms.doc.annotation.Api;
import com.wh.tms.doc.annotation.Auth;
import com.wh.tms.doc.annotation.Demo;
import com.wh.tms.doc.annotation.Domain;
import com.wh.tms.entity.po.LoginUser;
import com.wh.tms.entity.vo.MenuVo;
import com.wh.tms.result.Result;
import com.wh.tms.service.IMenuService;
import com.wh.tms.util.ResultUtils;

@Controller
@Domain(desc="首页相关接口")
public class IndexController extends BaseController{
	
	@Autowired
	private IMenuService menuService;
	
	/**
	 * 根据用户权限获取对应菜单
	* @Title: getMenuList 
	* @Description: TODO
	* @param @param request
	* @param @return 
	* @return Result
	* @throws
	 */
	@Api(author="Jesson",
			createtime="2018-03-08",
			name="获取权限对应菜单",
			desc="根据用户权限获取对应菜单",
			auth = {
				@Auth(checkEnc = true)
			},
			demo = @Demo(param="",success="{'code': 0,'count': 1,'data': [{'children': "
					+ "[{'children': null,'id': 9,'mName': '个人工机具列表','mUrl': 'working_machine_manager.html',"
					+ "'pid': 1,'xpath': '1/9'},{'children': null,'id': 10,'mName': '工机具添加',"
					+ "'mUrl': 'working_machine_add_list.html','pid': 1,'xpath': '1/10'}],'id': 1,"
					+ "'mName': '个人工机具管理','mUrl': null,'pid': 0,'xpath': '1/'}],'msg': '成功'}"))
	@RequestMapping(value = "/menu/list.json",method = RequestMethod.POST)
	public @ResponseBody Result getMenuList(HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.error(SystemCons.ERROR_NO_LOGINING, "请登录！");
		}
		List<MenuVo> list = menuService.getUserMenuList(user.getId());
		return ResultUtils.success(null == list ? 0 : list.size(), list);
	}

}
