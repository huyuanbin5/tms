package com.wh.tms.entity.po;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 消息接收人
* @ClassName: NoticePerson 
* @Description: TODO 
* @author Huqk
* @date 2018年4月1日 上午10:44:27
 */
@Table(name = "t_notice_noticeperson")
public class NoticePerson implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="JDBC")
    private Integer id;

    /**
     * 通知id
     */
    private Integer noticeId;

    /**
     * 消息接收人id
     */
    private Integer uid;

    /**
     * 消息接收人名称
     */
    private String userName;

    /**
     * 消息接收时间
     */
    private Long insertTime;

    /**
     * 是否已读,0:未读,1:已读
     */
    private Integer isRead;

    /**
     * 已读时间
     */
    private Long readTime;

    /**
     * 是否是接收人并且是接收人
     */
    private Integer isReceive;

    /**
     * 状态，0：正常，-1：删除
     */
    private Integer status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Long insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Long getReadTime() {
        return readTime;
    }

    public void setReadTime(Long readTime) {
        this.readTime = readTime;
    }

    public Integer getIsReceive() {
        return isReceive;
    }

    public void setIsReceive(Integer isReceive) {
        this.isReceive = isReceive;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}