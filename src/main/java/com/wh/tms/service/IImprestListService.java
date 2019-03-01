package com.wh.tms.service;

import com.wh.tms.result.Result;

public interface IImprestListService {
	
	/**
	 * 添加预借清单
	 * @param uid
	 * @param orderId
	 * @param toolTypeNum
	 * @param toolTypeName
	 * @param startTime
	 * @param endTime
	 * @param borrId
	 * @param leaderId
	 * @param explain
	 * @param part_remark
	 * @return
	 */
	Result addImprestList(Integer uid,Integer orderId,String toolTypeNum,String toolTypeName,String startTime,
			String endTime,Integer borrId,Integer leaderId,String explain,Integer part_remark);
	
	/**
	 * 更新预借清单
	 * @param uid
	 * @param id
	 * @param toolTypeNum
	 * @param toolTypeName
	 * @param startTime
	 * @param endTime
	 * @param borrId
	 * @param leaderId
	 * @param explain
	 * @param part_remark
	 * @return
	 */
	Result updateImprestList(Integer uid,Integer id,String toolTypeNum,String toolTypeName,String startTime,
			String endTime,Integer borrId,Integer leaderId,String explain,Integer part_remark);
	
	/**
	 * 删除预借清单(创建人或管理员可以删除)
	* @Title: deleteImprestList 
	* @Description: TODO
	* @param @param uid 当前登录用户id 
	* @param @param id 清单id
	* @param @return 
	* @return Result
	* @throws
	 */
	Result deleteImprestList(Integer uid,Integer id);
	/**
	 * 获取清单详情
	* @Title: getImprestList 
	* @Description: TODO
	* @param @param id
	* @param @return 
	* @return Result
	* @throws
	 */
	Result getImprestListDetail(Integer id);
	/**
	 * 获取预借清单列表
	* @Title: getImprestList 
	* @Description: TODO
	* @param @param orderId
	* @param @param pageNum
	* @param @param pageSize
	* @param @return 
	* @return Result
	* @throws
	 */
	Result getImprestList(Integer orderId,int pageNum,int pageSize);

}
