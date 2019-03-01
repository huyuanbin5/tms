package com.wh.tms.service;

import java.util.List;

import com.wh.tms.entity.vo.DeptVo;
import com.wh.tms.result.Result;

public interface IDeptService {
	
	List<DeptVo> getDeptList(Integer deptId);

	/**
	 * 添加部门
	* @Title: addDept 
	* @Description: TODO
	* @param @param pid
	* @param @param name
	* @param @return 
	* @return Result
	* @throws
	 */
	Result addDept(Integer pid,String name);
	/**
	 * 删除部门
	* @Title: deleteDept 
	* @Description: TODO
	* @param @param deptId
	* @param @return 
	* @return Result
	* @throws
	 */
	Result deleteDept(Integer deptId);
	/**
	 * 更新部门名称
	* @Title: updateDept 
	* @Description: TODO
	* @param @param name
	* @param @return 
	* @return Result
	* @throws
	 */
	Result updateDept(Integer deptId,String name);
}
