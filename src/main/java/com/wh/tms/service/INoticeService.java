package com.wh.tms.service;

import com.wh.tms.result.Result;

public interface INoticeService {
	
	/**
	 * 删除消息
	* @Title: deleteNotice 
	* @Description: TODO
	* @param @return 
	* @return Result
	* @throws
	 */
	Result deleteNotice(Integer uid,Integer noticeId);
	/**
	 * 撤回消息
	* @Title: recallNotice 
	* @Description: TODO
	* @param @param createrPuid
	* @param @param noticeId
	* @param @return 
	* @return Result
	* @throws
	 */
	Result recallNotice(Integer createrPuid,Integer noticeId);
	/**
	 * 消息详情
	* @Title: getNotice 
	* @Description: TODO
	* @param  uid
	* @param  noticeId
	* @return Result
	* @throws
	 */
	Result getNotice(Integer uid,Integer noticeId);
	
	/**
	 * 添加消息
	* @Title: addNotice 
	* @Description: TODO
	* @param  createrUid
	* @param  sourceType
	* @param  title
	* @param  content
	* @param  recipient
	* @return Result
	* @throws
	 */
	Result sendNotice(Integer createrUid,Integer sourceType,String title,String content,String recipient);
	/**
	 * 更新消息信息
	* @Title: updateNotice 
	* @Description: TODO
	* @param @param noticeId
	* @param @param createrUid
	* @param @param title
	* @param @param content
	* @param @return 
	* @return Result
	* @throws
	 */
	Result updateNotice(Integer noticeId,Integer createrUid,String title,String content);
	/**
	 * 获取未读和已读数量
	* @Title: getReadCount 
	* @Description: TODO
	* @param @param createrUid
	* @param @param sourceType
	* @param @param isread
	* @param @return 
	* @return long
	* @throws
	 */
	Result getReadCount(Integer uid,Integer sourceType,Integer isread);
	/**
	 * 获取我的消息列表
	* @Title: getNoticeList 
	* @Description: TODO
	* @param  createrUid
	* @param  sourceType
	* @param  startTime
	* @param  endTime
	* @param  pageNum
	* @param  pageSize
	* @return Result
	* @throws
	 */
	Result getMyNoticeList(Integer uid,Integer type,String createrName,String title,Integer sourceType,Integer isread,int pageNum,int pageSize);

}
