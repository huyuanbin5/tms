package com.wh.tms.service;

import com.wh.tms.result.Result;

public interface IRoleService {
	
	/**
	 * 获取角色列表
	* @Title: getRoleList 
	* @Description: TODO
	* @param @param page
	* @param @param pageSize
	* @param @return 
	* @return Result
	* @throws
	 */
	Result getRoleList(String roleName,int page,int pageSize);
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
	Result addRole(String roleName,String remark);
	/**
	 * 更新角色信息
	* @Title: updateRole 
	* @Description: TODO
	* @param @param roleId
	* @param @param roleName
	* @param @param remark
	* @param @return 
	* @return Result
	* @throws
	 */
	Result updateRole(Integer roleId,String roleName,String remark);
	/**
	 * 删除角色
	* @Title: deleteRole 
	* @Description: TODO
	* @param @param roleId
	* @param @return 
	* @return Result
	* @throws
	 */
	Result deleteRole(Integer roleId);

}
