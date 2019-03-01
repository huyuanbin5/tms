package com.wh.tms.service;

import com.wh.tms.result.Result;

public interface IWorkToolsDictionaryService {
	
	/**
	 * 添加字典
	* @Title: addDictionary 
	* @Description: TODO
	* @param @param type
	* @param @param name
	* @param @return 
	* @return Result
	* @throws
	 */
	Result addDictionary(Integer type,String name);
	/**
	 * 获取字典列表
	* @Title: getDictionaryList 
	* @Description: TODO
	* @param @param type
	* @param @return 
	* @return Result
	* @throws
	 */
	Result getDictionaryList(Integer type);
	/**
	 * 更新字典信息
	* @Title: updateDictionary 
	* @Description: TODO
	* @param @param id
	* @param @param type
	* @param @param name
	* @param @return 
	* @return Result
	* @throws
	 */
	Result updateDictionary(Integer id,Integer type,String name);
	/**
	 * 删除字典
	* @Title: deleteDictionary 
	* @Description: TODO
	* @param @param id
	* @param @return 
	* @return Result
	* @throws
	 */
	Result deleteDictionary(Integer id);

}
