package com.wh.tms.service;

import com.wh.tms.result.Result;

public interface IToolsTimeLimitService {
	
	/**
	 * 机具时限列表
	* @Title: getToolsTimeLimitList 
	* @Description: TODO
	* @param @return 
	* @return Result
	* @throws
	 */
	Result getToolsTimeLimitList(Integer id,Integer categoryType);

	/**
	 * 更新机具时限
	* @Title: updateTimelimit 
	* @Description: TODO
	* @param @param timelimits json {id:timelimit}
	* @param @return 
	* @return Result
	* @throws
	 */
	Result updateTimelimit(String timelimits);
}
