package com.wh.tms.entity.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 通知
* @ClassName: Notice 
* @Description: TODO 
* @author Huqk
* @date 2018年4月1日 上午10:41:03
 */
@Table(name = "t_notice_notice")
public class Notice implements Serializable {
	
	
	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -5533373832606370654L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="JDBC")
    private Integer id;

    /**
     * 消息发送人id
     */
    private Integer createrUid;
    /**
     * 消息发送人名称
     */
    private String createrName;

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
    private Date insertTime;
    private Long insertLongTime;

    /**
     * 消息更新时间
     */
    private Date updateTime;

    /**
     * 消息接收人前20人
     */
    private String users;

    

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
    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public Long getInsertLongTime() {
		return insertLongTime;
	}

	public void setInsertLongTime(Long insertLongTime) {
		this.insertLongTime = insertLongTime;
	}
    
}