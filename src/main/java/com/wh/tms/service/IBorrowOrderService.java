package com.wh.tms.service;

import com.wh.tms.result.Result;

public interface IBorrowOrderService {
	
	/**
	 * 添加借还单
	 * @param imprestId
	 * @return
	 */
	Result addBorrowOrder(Integer imprestId) throws Exception;
	/**
	 * 获取借还单列表
	* @Title: getBorrowOrderList 
	* @Description: TODO
	* @param @param uid
	* @param @param borrowOrderId
	* @param @param pageNum
	* @param @param pageSize
	* @param @return 
	* @return Result
	* @throws
	 */
	Result getBorrowOrderList(Integer uid,Integer borrowOrderStatus,String startTime,String endTime,int pageNum,int pageSize);
	/**
	 * 获取借还单详情
	* @Title: getBorrowOrderDetail 
	* @Description: TODO
	* @param @param id
	* @param @return 
	* @return Result
	* @throws
	 */
	Result getBorrowOrderDetail(Integer id);
}
