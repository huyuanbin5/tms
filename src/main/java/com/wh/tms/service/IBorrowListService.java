package com.wh.tms.service;

import com.wh.tms.result.Result;

public interface IBorrowListService {

	/**
	 * 增加借还清单
	 * @param uid 操作人uid
	 * @param imprestOrderId 预借单id
	 * @param imprestListId 预借清单id
	 * @param toolCode 机具编码
	 * @param goodsAllo 货位
	 * @param borrowerTime 借出日期
	 * @param endTime 截止日期
	 * @return
	 */
	Result addBorrowList(Integer uid,Integer imprestOrderId,Integer imprestListId,Integer borrowOrderId,String toolCode,String goodsAllo,String borrowerTime,String endTime);
	/**
	 * 更新清单信息
	 * @param uid
	 * @param id 清单id
	 * @param toolCode 机具编码
	 * @param goodsAllo 货位
	 * @param borrowerTime
	 * @param endTime
	 * @return
	 */
	Result updateBorrowList(Integer uid,Integer id,String toolCode,String goodsAllo,String borrowerTime,String endTime,Integer borrowerStatus);
	/**
	 * 删除借还清单
	 * @param uid
	 * @param id
	 * @return
	 */
	Result deleteBorrowList(Integer uid,Integer id);
	/**
	 * 预借单详情
	 * @param uid
	 * @param borrowListId 
	 * @return
	 */
	Result getBorrowListDetail(Integer uid,Integer borrowListId);
	/**
	 * 借还清单列表
	 * @param uid
	 * @param borrowOrderId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	Result getBorrowList(Integer uid,Integer borrowOrderId,int pageNum,int pageSize);
}
