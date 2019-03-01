package com.wh.tms.entity.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 预借单
* @ClassName: ImprestOrder 
* @Description: TODO 
* @author Huqk
* @date 2018年3月20日 下午8:39:43
 */
@Table(name="t_imprest_order")
public class ImprestOrder implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="JDBC")
    private Integer id;

    /**
     * 操作人id
     */
    private Integer uid;

    /**
     * 操作人名称
     */
    private String userName;

    /**
     * 预约日期
     */
    @JSONField(format="yyyy-MM-dd")
    private Date reservationTime;

    /**
     * 失效日期
     */
    @JSONField(format="yyyy-MM-dd")
    private Date invalidTime;

    /**
     * 0:未提交,1:未处理,2:已处理,3:已失效
     */
    private Integer imprestStatus;

    /**
     * 作业类型,1:本地作业,2:外部作业,3:出海作业
     */
    private Integer workType;

    /**
     * 借出说明
     */
    private String outInst;
    /**
     * 更新操作人id
     */
    private Integer updateUid;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 预借单状态，0:正常,-1:已删除
     */
    private Integer status;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Date reservationTime) {
        this.reservationTime = reservationTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public Integer getImprestStatus() {
        return imprestStatus;
    }

    public void setImprestStatus(Integer imprestStatus) {
        this.imprestStatus = imprestStatus;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public String getOutInst() {
        return outInst;
    }

    public void setOutInst(String outInst) {
        this.outInst = outInst;
    }

	public Integer getUpdateUid() {
		return updateUid;
	}

	public void setUpdateUid(Integer updateUid) {
		this.updateUid = updateUid;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
    
}