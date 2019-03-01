package com.wh.tms.service;

import java.util.List;

import com.wh.tms.entity.vo.RepositoryVo;
import com.wh.tms.result.Result;

public interface IRepositoryService {
	
	List<RepositoryVo> getGoodList(Integer id);

	/**
	 * 添加货位
	* @Title: addGoods 
	* @Description: TODO
	* @param @param pid
	* @param @param name
	* @param @return 
	* @return Result
	* @throws
	 */
	Result addGoods(Integer pid,String name);
	/**
	 * 删除货位
	* @Title: deleteGoods 
	* @Description: TODO
	* @param @param deptId
	* @param @return 
	* @return Result
	* @throws
	 */
	Result deleteGoods(Integer id);
	/**
	 * 更新货位名称
	* @Title: updateDept 
	* @Description: TODO
	* @param @param name
	* @param @return 
	* @return Result
	* @throws
	 */
	Result updateGoods(Integer id,String name);
}
