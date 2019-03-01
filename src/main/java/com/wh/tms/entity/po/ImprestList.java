package com.wh.tms.entity.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 预借清单
* @ClassName: ImprestList 
* @Description: TODO 
* @author Huqk
* @date 2018年3月20日 下午9:17:11
 */
@Table(name = "t_imprest_list")
public class ImprestList implements Serializable {
	
	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -5290746338251189354L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="JDBC")
    private Integer id;

    /**
     * 预借单id
     */
    private Integer orderId;
    /**
     * 创建人id
     */
    private Integer createrId;
    /**
     * 创建人名称
     */
    private String createrName;

    /**
     * 机具种类编号
     */
    private String toolTypeNum;

    /**
     * 机具种类名称
     */
    private String toolTypeName;

    /**
     * 借用人id
     */
    private Integer borrowerId;

    /**
     * 借用人名称
     */
    private String borrowerName;

    /**
     * 负责人id
     */
    private Integer leaderId;

    /**
     * 负责人名称
     */
    private String leaderName;

    /**
     * 起始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 是否带票,0:否 1:是
     */
    private Integer partRemark;

    /**
     * 说明
     */
    private String explains;
    /**
     * 保存时间
     */
    private Date insertTime;
    /**
     * 预借清单状态，0：正常，-1：已删除
     */
    private Integer status;
    /**
     * 更新时间
     */
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getToolTypeNum() {
		return toolTypeNum;
	}

	public void setToolTypeNum(String toolTypeNum) {
		this.toolTypeNum = toolTypeNum;
	}

	public String getToolTypeName() {
		return toolTypeName;
	}

	public void setToolTypeName(String toolTypeName) {
		this.toolTypeName = toolTypeName;
	}

	public Integer getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Integer borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getPartRemark() {
        return partRemark;
    }

    public void setPartRemark(Integer partRemark) {
        this.partRemark = partRemark;
    }
	public String getExplains() {
		return explains;
	}

	public void setExplains(String explains) {
		this.explains = explains;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	
}