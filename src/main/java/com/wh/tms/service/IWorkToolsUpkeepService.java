package com.wh.tms.service;

import com.wh.tms.result.Result;

public interface IWorkToolsUpkeepService {
	
	/**
	 * 添加保养
	* @Title: addWorkToolsUpkeep 
	* @Description: TODO
	* @param @param toolsId
	* @param @param toolsCode
	* @param @param managerCategoryrId
	* @param @param categoryId
	* @param @param operUid
	* @param @param operConId
	* @param @return 
	* @return Result
	* @throws
	 */
	Result addWorkToolsUpkeep(Integer toolsId,String toolsCode,Integer managerCategoryrId,Integer categoryId,Integer operUid,Integer operConId,String planDate);
	/**
	 * 更新保养信息
	* @Title: updateWorkToolsUpkeep 
	* @Description: TODO
	* @param @param id
	* @param @param operUid
	* @param @param operConId
	* @param @return 
	* @return Result
	* @throws
	 */
	Result updateWorkToolsUpkeep(Integer id,Integer operUid,Integer operConId,Integer status);
	/**
	 * 保养列表
	* @Title: getWorkToolsUpkeepList 
	* @Description: TODO
	* @param @param id
	* @param @param managerCategoryId
	* @param @param categoryId
	* @param @param status
	* @param @param startDate
	* @param @param endDate
	* @param @param page
	* @param @param pageSize
	* @param @return 
	* @return Result
	* @throws
	 */
	Result getWorkToolsUpkeepList(Integer id,Integer managerCategoryId,Integer categoryId,Integer status,String startDate,String endDate,int page,int pageSize);
	/**
	 * 删除保养单
	* @Title: deleteUpkeepOrder 
	* @Description: TODO
	* @param @param id
	* @param @return 
	* @return Result
	* @throws
	 */
	Result deleteUpkeepOrder(Integer id);

}
