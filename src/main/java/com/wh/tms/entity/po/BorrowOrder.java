package com.wh.tms.entity.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 借还单
 * @author Administrator
 *
 */
@Table(name="t_borrow_order")
public class BorrowOrder implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="JDBC")
    private Integer id;
	
	/**
	 * 预借单id
	 */
	private Integer imprestOrderId;
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
    private Date reservationTime;

    /**
     * 失效日期
     */
    private Date invalidTime;
    /**
     * 出库日期
     */
    private Date deliveryDate;


    /**
     * 作业类型,1:本地作业,2:外部作业,3:出海作业
     */
    private Integer workType;

    /**
     * 借出说明
     */
    private String outInst;
    /**
     * 状态0:正常,-1:删除
     */
    private Integer status;
    /**
     * 借还单完结状态0:未完结,1:已完结
     */
    private Integer borrowerOrderStatus;
    
    @Transient
    private Integer leaderId;//负责人Id
    @Transient
    private String leader;//负责人

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

	public Integer getImprestOrderId() {
		return imprestOrderId;
	}

	public void setImprestOrderId(Integer imprestOrderId) {
		this.imprestOrderId = imprestOrderId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getBorrowerOrderStatus() {
		return borrowerOrderStatus;
	}

	public void setBorrowerOrderStatus(Integer borrowerOrderStatus) {
		this.borrowerOrderStatus = borrowerOrderStatus;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Integer getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}
    
}