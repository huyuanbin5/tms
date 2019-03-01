package com.wh.tms.entity.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 借还清单
* @ClassName: BorrowOrder 
* @Description: TODO 
* @author Huqk
* @date 2018年3月21日 下午11:08:36
 */
@Table(name="t_borrow_list")
public class BorrowList implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="JDBC")
    private Integer id;

    /**
     * 预借单id
     */
    private Integer imprestOrderId;

    /**
     * 预借清单id
     */
    private Integer imprestListId;
    /**
     * 借还单id
     */
    private Integer borrowOrderId;

    /**
     * 操作人uid
     */
    private Integer operUid;

    /**
     * 操作人名称
     */
    private String operUname;

    /**
     * 机具种类编号
     */
    private String toolTypeNum;

    /**
     * 机具种类名称
     */
    private String toolTypeName;

    /**
     * 机具编码
     */
    private String toolCode;

    /**
     * 货位
     */
    private String goodsAllo;

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
     * 借出时间
     */
    private Date borrowerTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 是否带票,0:否 1:是
     */
    private Integer partRemark;

    /**
     * 借还单生成时间
     */
    private Date insertTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 借还单状态，0:正常 -1:已删除
     */
    private Integer status;
    /**
     * 借还状态，1:未还,2:已还,3:更换并且维修,4:更换并且入库,5:挂失,6:建议报废,7:已还并且维修
     */
    private Integer borrowerStatus;

    /**
     * 说明
     */
    private String explains;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImprestListId() {
        return imprestListId;
    }

    public void setImprestListId(Integer imprestListId) {
        this.imprestListId = imprestListId;
    }

    public Integer getOperUid() {
        return operUid;
    }

    public void setOperUid(Integer operUid) {
        this.operUid = operUid;
    }

    public String getOperUname() {
        return operUname;
    }

    public void setOperUname(String operUname) {
        this.operUname = operUname;
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

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getGoodsAllo() {
        return goodsAllo;
    }

    public void setGoodsAllo(String goodsAllo) {
        this.goodsAllo = goodsAllo;
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

    public Date getBorrowerTime() {
        return borrowerTime;
    }

    public void setBorrowerTime(Date borrowerTime) {
        this.borrowerTime = borrowerTime;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

	public Integer getBorrowerStatus() {
		return borrowerStatus;
	}

	public void setBorrowerStatus(Integer borrowerStatus) {
		this.borrowerStatus = borrowerStatus;
	}

	public Integer getImprestOrderId() {
		return imprestOrderId;
	}

	public void setImprestOrderId(Integer imprestOrderId) {
		this.imprestOrderId = imprestOrderId;
	}

	public Integer getBorrowOrderId() {
		return borrowOrderId;
	}

	public void setBorrowOrderId(Integer borrowOrderId) {
		this.borrowOrderId = borrowOrderId;
	}
	
    
}