package com.wh.tms.service;


import com.wh.tms.result.Result;

public interface IWorkToolsService {
	
	/**
	 * 添加个人工机具
	* @Title: addWorkTool 
	* @Description: TODO
	* @param @param uid
	* @param @param categoryId
	* @param @param deptId
	* @param @param insertTime
	* @param @param addCount
	* @param @return 
	* @return int
	* @throws
	 */
	Result addWorkTool(Integer uid,Integer categoryId,Integer deptId,String insertTime,Integer addCount);
	
	/**
	 * 获取工机具基本信息列表
	* @Title: getBaseInfoList 
	* @Description: TODO
	* @param @param name
	* @param @param deptName
	* @param @param insertTime
	* @param @param page
	* @param @param limit
	* @param @return 
	* @return BaseResult
	* @throws
	 */
	Result getWorkToolsList(Integer managerCategoryId,String categoryName,Integer deptId,String deptName,String insertTime,int page,int limit);
	/**
	 * 添加通用工机具
	* @Title: addCommonWorkTools 
	* @Description: TODO
	* @param @param uid
	* @param @param managerCategoryId 机具管理类别id
	* @param @param categoryId 机具种类id
	* @param @param deptId 所属单位部门id
	* @param @param repositoryId 仓库货位id
	* @param @param manufacturerId 制造商id
	* @param @param supplierId 供应商id
	* @param @param factoryNumber 厂家编号
	* @param @param brand 品牌
	* @param @param toolsCode 机具编码
	* @param @param yearLimit 年限
	* @param @param evaluation 估值
	* @param @param insertTime 入库时间
	* @param @param remark 备注
	* @param @return 
	* @return Result
	* @throws
	 */
	Result addCommonWorkTools(Integer uid,Integer managerCategoryId,Integer categoryId,Integer deptId,Integer repositoryId,
			Integer manufacturerId,Integer supplierId,String factoryNumber,String brand,String toolsCode,String yearLimit,
			Integer evaluation,String insertTime,String remark);
	
	/**
	 * 更新通用工机具
	* @Title: updateCommonWorkTools 
	* @Description: TODO
	* @param @param id
	* @param @param managerCategoryId
	* @param @param deptId
	* @param @param repositoryId
	* @param @param manufacturerId
	* @param @param supplierId
	* @param @param factoryNumber
	* @param @param brand
	* @param @param toolsCode
	* @param @param yearLimit
	* @param @param evaluation
	* @param @param remark
	* @param @param status
	* @param @return 
	* @return Result
	* @throws
	 */
	Result updateCommonWorkTools(Integer id,Integer managerCategoryId,Integer deptId,Integer repositoryId,
			Integer manufacturerId,Integer supplierId,String factoryNumber,String brand,String toolsCode,String yearLimit,
			Integer evaluation,String remark,Integer status);
	
	/**
	 * 删除工机具
	* @Title: deleteCommonWorkTools 
	* @Description: TODO
	* @param @param id
	* @param @return 
	* @return Result
	* @throws
	 */
	Result deleteWorkTools(Integer id);
	/**
	 * 通用工机具类列表
	* @Title: getCommonWorkToolsList 
	* @Description: TODO
	* @param @param managerCategoreyId
	* @param @param categoryId
	* @param @param toolsCode
	* @param @param supplierId
	* @param @param manufacturerId
	* @param @param status
	* @param @return 
	* @return Result
	* @throws
	 */
	Result getCommonWorkToolsList(Integer id,Integer managerCategoryId,Integer categoryId,String toolsCode,Integer supplierId,Integer manufacturerId,Integer status,Integer page,Integer limit);
	
	

}
