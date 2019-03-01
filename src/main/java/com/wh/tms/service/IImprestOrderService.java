package com.wh.tms.service;

import com.wh.tms.result.Result;

public interface IImprestOrderService {
	
	/**
	 * 添加预借单
	 * @param uid
	 * @param userName
	 * @param resTime
	 * @param invTime
	 * @param workType
	 * @param outInst
	 * @return
	 */
	Result addImprestOrder(Integer uid,String resTime,String invTime,Integer workType,String outInst) throws Exception;
	/**
	 * 获取个人预借单列表
	 * @param uid
	 * @param imprestStatus
	 * @param startTime
	 * @param endTime
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	Result getImprestOrderList(Integer uid,Integer orderId,Integer imprestStatus,String startTime,String endTime,int pageNum,int pageSize);
	/**
	 * 更新预订单
	 * @param orderId
	 * @param uid 
	 * @param resTime
	 * @param invTime
	 * @param workType
	 * @param outInst
	 * @return
	 */
	Result updateImprestOrder(Integer orderId,Integer uid,String resTime,String invTime,Integer workType,String outInst) throws Exception;
	/**
	 * 删除预订单
	 * @param orderId
	 * @param uid
	 * @return
	 */
	Result deleteImprestOrder(Integer orderId,Integer uid) throws Exception;

}
