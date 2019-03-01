package com.wh.tms.service;

import com.wh.tms.result.Result;

public interface IWorkToolsMaintenanceService {
	
	/**
	 * 添加维修机具
	* @Title: addWorkToolsMaintenance 
	* @Description: TODO
	* @param @param toolsCode 机具编码
	* @param @param categoryId 机具种类
	* @param @param borrowerUid 借用人id
	* @param @param sCategoryId 维修类别id
	* @param @param sConId 维修内容id
	* @param @return 
	* @return Result
	* @throws
	 */
	Result addWorkToolsMaintenance(Integer toolsId,String toolsCode,Integer managerCategoryrId,Integer categoryId,Integer borrowerUid,Integer mUid,Integer sCategoryId,Integer sConId);
	/**
	 * 更新维修情况
	* @Title: updateWorkToolsMaintenanc 
	* @Description: TODO
	* @param @param id 维修订单id
	* @param @param mUid 维修人uid
	* @param @param checkUid 检查人uid
	* @param @param mPolicyId 维修策略id
	* @param @param aConId 维修内容id
	* @param @param aAssessId 验收评价id
	* @param @param status 状态
	* @param @return 
	* @return Result
	* @throws
	 */
	Result updateWorkToolsMaintenanc(Integer id,Integer mUid,Integer checkUid,Integer mPolicyId,Integer aConId,Integer aAssessId,Integer status);
	/**
	 * 查询列表
	* @Title: getMaintenancList 
	* @Description: TODO
	* @param @param id
	* @param @param managerCategoryId
	* @param @param categoryId
	* @param @param status
	* @param @param startDate
	* @param @param endDate
	* @param @return 
	* @return Result
	* @throws
	 */
	Result getMaintenancList(Integer id,Integer managerCategoryId,Integer categoryId,Integer status,String startDate,String endDate,int page,int pageSize);
	/**
	 * 删除维修单
	* @Title: deleteMaintenanc 
	* @Description: TODO
	* @param @param id
	* @param @return 
	* @return Result
	* @throws
	 */
	Result deleteMaintenanc(Integer id);

}
