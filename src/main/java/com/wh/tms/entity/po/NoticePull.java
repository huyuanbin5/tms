package com.wh.tms.entity.po;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 通知拉取
* @ClassName: NoticePull 
* @Description: TODO 
* @author Huqk
* @date 2018年4月1日 下午3:47:50
 */
@Table(name = "t_notice_noticepull")
public class NoticePull implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="JDBC")
    private Integer id;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 上一次拉取公告时间
     */
    private Long prevPullTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Long getPrevPullTime() {
        return prevPullTime;
    }

    public void setPrevPullTime(Long prevPullTime) {
        this.prevPullTime = prevPullTime;
    }
}