package com.wh.tms.dao.notice;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

import com.wh.tms.entity.po.NoticePerson;
import com.wh.tms.entity.vo.NoticeVo;

public interface INoticePersonDAO extends Mapper<NoticePerson>{
	
	/**
	 * 查询通知已读或未读数量
	* @Title: selectReadNoticeCount 
	* @Description: TODO
	* @param @param uid
	* @param @param sourceType
	* @param @param isread
	* @param @return 
	* @return long
	* @throws
	 */
	long selectNoticeCount(@Param("uid") Integer uid, @Param("sourceType") Integer sourceType,@Param("isread") Integer isread);
	/**
	 * 查询我的通知
	* @Title: selectMyNotice 
	* @Description: TODO
	* @param @param createrUid
	* @param @param createrName
	* @param @param title
	* @param @param sourceType
	* @param @param isread
	* @param @return 
	* @return List<NoticeVo>
	* @throws
	 */
	List<NoticeVo> selectMyNotice(@Param("type") Integer type,@Param("uid") Integer uid,@Param("createrName") String createrName,
			@Param("title") String title,@Param("sourceType") Integer sourceType,
			@Param("isread") Integer isread);
}