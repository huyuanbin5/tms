package com.wh.tms.web.controller.auth;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wh.tms.doc.annotation.Api;
import com.wh.tms.doc.annotation.Auth;
import com.wh.tms.doc.annotation.Demo;
import com.wh.tms.doc.annotation.Domain;
import com.wh.tms.doc.annotation.Rule;
import com.wh.tms.entity.vo.MenuVo;
import com.wh.tms.result.Result;
import com.wh.tms.service.IMenuService;
import com.wh.tms.service.IRoleService;
import com.wh.tms.util.ResultUtils;
import com.wh.tms.web.controller.BaseController;

/**
 * 角色管理
* @ClassName: RoleManagerController 
* @Description: TODO 
* @author Huqk
* @date 2018年3月17日 下午3:05:57
 */
@Controller
@RequestMapping("/system/role")
@Domain(desc="系统管理-角色管理")
public class RoleManagerController extends BaseController {
	
	@Autowired
	private IRoleService roleSerivce;
	@Autowired
	private IMenuService menuService;
	/**
	 * 获取角色列表
	* @Title: getRoleList 
	* @Description: TODO
	* @param @param page
	* @param @param limit
	* @param @param request
	* @param @return 
	* @return Result
	* @throws
	 */
	@Api(author="Jesson",
			createtime="2018-03-17",
			name="角色列表",
			desc="查询角色信息",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="roleName",desc="通过角色名称搜索"),
					@Rule(name="page",desc="起始页"),
					@Rule(name="limit",desc="每页显示的条数")
			},
			demo = @Demo(param="",success="{'code': 0,'count': 2,'data': [{'id': 1,'insertTime': '2018-03-07 22:20:41','remark': null,'roleName': '系统管理员'},"
					+ "{'id': 2,'insertTime': '2018-03-17 15:43:25','remark': '测试','roleName': 'test'}],'msg': '成功'}"))
	@RequestMapping(value = "/list.json", method = RequestMethod.POST)
	public @ResponseBody Result getRoleList(@RequestParam(value = "roleName" , required = false) String roleName,
			@RequestParam(value = "page" , required = false, defaultValue = "1") Integer page,
			@RequestParam(value = "limit" , required = false, defaultValue = "15") Integer limit) {
		
		return roleSerivce.getRoleList(roleName,page,limit);
	}
	
	/**
	 * 添加角色
	* @Title: addRole 
	* @Description: TODO
	* @param @param roleName
	* @param @param remark
	* @param @return 
	* @return Result
	* @throws
	 */
	@Api(author="Jesson",
			createtime="2018-03-17",
			name="添加角色",
			desc="",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="roleName",desc="角色名称"),
					@Rule(name="remark",desc="备注")
			},
			demo = @Demo(param="",success="{'code': 0,'count': 1,'data': [{'id': 2,'insertTime': '2018-03-17 15:43:25','remark': '测试','roleName': 'test'}],'msg': '添加成功'}"))
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	public @ResponseBody Result addRole(@RequestParam(value = "roleName" , required = true) String roleName,
			@RequestParam(value = "remark" , required = false) String remark) {
		
		return roleSerivce.addRole(roleName, remark);
	}
	/**
	 * 更新角色信息
	* @Title: addRole 
	* @Description: TODO
	* @param @param roleId
	* @param @param roleName
	* @param @param remark
	* @param @param request
	* @param @return 
	* @return Result
	* @throws
	 */
	@Api(author="Jesson",
			createtime="2018-03-17",
			name="更新角色信息",
			desc="",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="roleId",desc="角色id"),
					@Rule(name="roleName",desc="角色名称"),
					@Rule(name="remark",desc="备注")
			},
			demo = @Demo(param="",success="{'code': 0,'count': 1,'data': [{'id': 2,'insertTime': '2018-03-17 15:43:25',"
					+ "'updateTime': '2018-03-17 15:45:25','remark': '测试','roleName': 'test'}],'msg': '更新成功'}"))
	@RequestMapping(value = "/update.json", method = RequestMethod.POST)
	public @ResponseBody Result updateRole(@RequestParam(value = "roleId" , required = true) Integer roleId,
			@RequestParam(value = "roleName" , required = true) String roleName,
			@RequestParam(value = "remark" , required = false) String remark) {
		
		return roleSerivce.updateRole(roleId, roleName, remark);
	}
	/**
	 * 删除角色
	* @Title: deleteRole 
	* @Description: TODO
	* @param @param roleId
	* @param @param request
	* @param @return 
	* @return Result
	* @throws
	 */
	@Api(author="Jesson",
			createtime="2018-03-17",
			name="删除角色",
			desc="",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="roleId",desc="角色id")
			},
			demo = @Demo(param="",success="{'code': 0,'count': 1,'data':'','msg': '删除成功'}"))
	@RequestMapping(value = "/delete.json", method = RequestMethod.POST)
	public @ResponseBody Result deleteRole(@RequestParam(value = "roleId" , required = true) Integer roleId) {
		
		return roleSerivce.deleteRole(roleId);
	}
	
	/**
	 * 获取角色对应资源
	* @Title: getMenuList 
	* @Description: TODO
	* @param @param request
	* @param @return 
	* @return Result
	* @throws
	 */
	@Api(author="Jesson",
			createtime="2018-03-17",
			name="获取角色对应菜单",
			desc="注意：roleId参数为空时获取系统所有菜单，反之获取角色对应菜单。",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="roleId",desc="角色id")
			},
			demo = @Demo(param="",success="{'code': 0,'count': 1,'data': [{'children': "
					+ "[{'children': null,'id': 9,'mName': '个人工机具列表','mUrl': 'working_machine_manager.html',"
					+ "'pid': 1,'xpath': '1/9'},{'children': null,'id': 10,'mName': '工机具添加',"
					+ "'mUrl': 'working_machine_add_list.html','pid': 1,'xpath': '1/10'}],'id': 1,"
					+ "'mName': '个人工机具管理','mUrl': null,'pid': 0,'xpath': '1/'}],'msg': '成功'}"))
	@RequestMapping(value = "/menus.json",method = RequestMethod.POST)
	public @ResponseBody Result getRoleMenus(@RequestParam(value = "roleId",required = false) Integer roleId,
			HttpServletRequest request) {
		
		List<MenuVo> list = menuService.getRoleMenuList(roleId);
		return ResultUtils.success(null == list ? 0 : list.size(), list);
	}
	
	/**
	 * 更新角色对应菜单
	* @Title: updateRoleMens 
	* @Description: TODO
	* @param @param roleId
	* @param @param menuIds
	* @param @param request
	* @param @return 
	* @return Result
	* @throws
	 */
	@Api(author="Jesson",
			createtime="2018-03-17",
			name="更新角色对应菜单",
			desc="新增或更新角色对应菜单",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="roleId",desc="角色id"),
					@Rule(name="menuIds",desc="菜单id,多个id以逗号分隔")
			},
			demo = @Demo(param="",success="{'code': 0,'count': 1,'data': '','msg': '处理成功!'}"))
	@RequestMapping(value = "/menus/update.json",method = RequestMethod.POST)
	public Result saveOrUpdateRoleMens(@RequestParam(value = "roleId",required = true) Integer roleId,
			@RequestParam(value = "menuIds",required = true) String menuIds) {

		return menuService.saveOrUpdateRoleMenus(roleId, menuIds);
	}

}
