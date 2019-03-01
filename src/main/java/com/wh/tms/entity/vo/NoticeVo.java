package com.wh.tms.entity.vo;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.wh.tms.entity.po.Notice;
import com.wh.tms.util.DateUtils;

public class NoticeVo {
	
	 private Integer id;

    /**
     * 消息发送人id
     */
    private Integer createrUid;
    /**
     * 消息发送人名称
     */
    private String createrName;
    
    private Integer uid;//接收人id
    /**
     * 接收人名称
     */
    private String userName;
    /**
     * 接收时间
     */
    private Long receiveTime;
    /**
     * 是否是通知发送人同时是接收人
     */
    private Integer isReceive;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息状态,-1：发送中，0:发送完成.1:撤回,-2:删除
     */
    private Integer status;

    /**
     * 消息类型,1:公告,2:通知,3:任务
     */
    private Integer sourceType;
    /**
     * 消息发送时间
     */
    private String insertTime;

    /**
     * 消息更新时间
     */
    private String updateTime;
    /**
     * 是否已读
     */
    private Integer isRead;
    /**
     * 已读时间
     */
    private String readTime;
    private Long rTime;
    /**
     * 消息接收人前20人
     */
    private JSONArray users;
    
    public static NoticeVo getInstance(Notice notice) {
	    	if (null == notice) {
    			return null;
	    	}
    		NoticeVo vo = new NoticeVo();
    		vo.setId(notice.getId());
    		vo.setCreaterUid(notice.getCreaterUid());
    		vo.setCreaterName(notice.getCreaterName());
    		vo.setTitle(notice.getTitle());
    		vo.setContent(notice.getContent());
    		Date insertTime = notice.getInsertTime();
    		Date updateTime = notice.getUpdateTime();
    		if (null != insertTime) {
    			vo.setInsertTime(DateUtils.getDate(insertTime, "yyyy-MM-dd HH:mm:ss"));
    		}
    		if (null != updateTime) {
    			vo.setUpdateTime(DateUtils.getDate(updateTime, "yyyy-MM-dd HH:mm:ss"));
    		}
    		String users = notice.getUsers();
    		if (StringUtils.isNotBlank(users)) {
    			try {
					vo.setUsers(JSONArray.parseArray(users));
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}
    		vo.setSourceType(notice.getSourceType());
    		vo.setStatus(notice.getStatus());
    		return vo;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCreaterUid() {
		return createrUid;
	}

	public void setCreaterUid(Integer createrUid) {
		this.createrUid = createrUid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public JSONArray getUsers() {
		return users;
	}

	public void setUsers(JSONArray users) {
		this.users = users;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public String getReadTime() {
		return readTime;
	}

	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}

	public Long getrTime() {
		return rTime;
	}

	public void setrTime(Long rTime) {
		this.rTime = rTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Long receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getIsReceive() {
		return isReceive;
	}

	public void setIsReceive(Integer isReceive) {
		this.isReceive = isReceive;
	}
	
}
