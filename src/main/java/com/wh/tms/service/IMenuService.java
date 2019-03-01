package com.wh.tms.service;

import java.util.List;

import com.wh.tms.entity.vo.MenuVo;
import com.wh.tms.result.Result;

public interface IMenuService {

	/**
	 * 获取用户对应角色菜单
	* @Title: getUserMenuList 
	* @Description: TODO
	* @param @param uid
	* @param @return 
	* @return List<MenuVo>
	* @throws
	 */
	List<MenuVo> getUserMenuList(Integer uid);
	/**
	 * 获取角色对应菜单
	* @Title: getRoleMenuList 
	* @Description: TODO
	* @param @param roleId
	* @param @return 
	* @return List<MenuVo>
	* @throws
	 */
	List<MenuVo> getRoleMenuList(Integer roleId);
	/**
	 * 更新角色对应菜单
	* @Title: saveOrUpdateRoleMenus 
	* @Description: TODO
	* @param @param roleId
	* @param @param menuIds
	* @param @return 
	* @return Result
	* @throws
	 */
	Result saveOrUpdateRoleMenus(Integer roleId,String menuIds);
}
