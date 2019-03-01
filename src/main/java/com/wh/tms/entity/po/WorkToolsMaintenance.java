package com.wh.tms.entity.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 机具维修
* @ClassName: WorkToolsMaintenance 
* @Description: TODO 
* @author Huqk
* @date 2018年5月2日 下午9:46:59
 */
@Table(name = "t_work_tools_maintenance")
public class WorkToolsMaintenance implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="JDBC")
    private Integer id;
	
	/**
	 * 机具id
	 */
	private Integer  toolsId;

    /**
     * 机具编码
     */
    private String toolsCode;
    /**
     * 机具管理类别id
     */
    private Integer managerCategoryId;

    /**
     * 机具管理类别名称
     */
    private String managerCategoryName;

    /**
     * 机具种类id
     */
    private Integer categoryId;

    /**
     * 机具种类编号
     */
    private String categoryNumber;

    /**
     * 机具种类名称
     */
    private String categoryName;

    /**
     * 借用人id
     */
    private Integer borrowerUid;

    /**
     * 借用人员名称
     */
    private String borrowerName;
    /**
     * 维修人uid
     */
    private Integer mUid;
    /**
     * 维修人名称
     */
    private String mUname;

    /**
     * 验收人uid
     */
    private Integer checkUid;

    /**
     * 验收人名称
     */
    private String checkUsername;

    /**
     * 维修类别id
     */
    private Integer sCategoryId;

    /**
     * 维修类别名称
     */
    private String sCategoryName;

    /**
     * 维修内容id
     */
    private Integer sConId;

    /**
     * 维修内容
     */
    private String sCon;

    /**
     * 维修策略id
     */
    private Integer mPolicyId;

    /**
     * 维修策略
     */
    private String mPolicyName;

    /**
     * 验收内容id
     */
    private Integer aConId;

    /**
     * 验收内容
     */
    private String aCon;

    /**
     * 验收评价id
     */
    private Integer aAssessId;

    /**
     * 验收评价
     */
    private String aAssess;

    /**
     * 维修状态标识,0:待处理,1:维修完毕入库,-1:建议报废
     */
    private Integer status;

    /**
     * 入库时间
     */
    private Date insertTime;

    /**
     * 计划日期
     */
    private Date planDate;

    /**
     * 实际维修时间
     */
    private Date mTime;

    /**
     * 验收时间
     */
    private Date aTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getToolsId() {
		return toolsId;
	}

	public void setToolsId(Integer toolsId) {
		this.toolsId = toolsId;
	}

	public String getToolsCode() {
        return toolsCode;
    }

    public void setToolsCode(String toolsCode) {
        this.toolsCode = toolsCode;
    }

	public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryNumber() {
        return categoryNumber;
    }

    public void setCategoryNumber(String categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getBorrowerUid() {
		return borrowerUid;
	}

	public void setBorrowerUid(Integer borrowerUid) {
		this.borrowerUid = borrowerUid;
	}

	public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public Integer getCheckUid() {
        return checkUid;
    }

    public void setCheckUid(Integer checkUid) {
        this.checkUid = checkUid;
    }

    public String getCheckUsername() {
        return checkUsername;
    }

    public void setCheckUsername(String checkUsername) {
        this.checkUsername = checkUsername;
    }

    public Integer getsCategoryId() {
        return sCategoryId;
    }

    public void setsCategoryId(Integer sCategoryId) {
        this.sCategoryId = sCategoryId;
    }

    public String getsCategoryName() {
        return sCategoryName;
    }

    public void setsCategoryName(String sCategoryName) {
        this.sCategoryName = sCategoryName;
    }

    public Integer getsConId() {
        return sConId;
    }

    public void setsConId(Integer sConId) {
        this.sConId = sConId;
    }

    public String getsCon() {
        return sCon;
    }

    public void setsCon(String sCon) {
        this.sCon = sCon;
    }

    public Integer getmPolicyId() {
        return mPolicyId;
    }

    public void setmPolicyId(Integer mPolicyId) {
        this.mPolicyId = mPolicyId;
    }

    public String getmPolicyName() {
        return mPolicyName;
    }

    public void setmPolicyName(String mPolicyName) {
        this.mPolicyName = mPolicyName;
    }

    public Integer getaConId() {
        return aConId;
    }

    public void setaConId(Integer aConId) {
        this.aConId = aConId;
    }

    public String getaCon() {
        return aCon;
    }

    public void setaCon(String aCon) {
        this.aCon = aCon;
    }

    public Integer getaAssessId() {
        return aAssessId;
    }

    public void setaAssessId(Integer aAssessId) {
        this.aAssessId = aAssessId;
    }

    public String getaAssess() {
        return aAssess;
    }

    public void setaAssess(String aAssess) {
        this.aAssess = aAssess;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Date getmTime() {
        return mTime;
    }

    public void setmTime(Date mTime) {
        this.mTime = mTime;
    }

    public Date getaTime() {
        return aTime;
    }

    public void setaTime(Date aTime) {
        this.aTime = aTime;
    }

	public Integer getManagerCategoryId() {
		return managerCategoryId;
	}

	public void setManagerCategoryId(Integer managerCategoryId) {
		this.managerCategoryId = managerCategoryId;
	}

	public String getManagerCategoryName() {
		return managerCategoryName;
	}

	public void setManagerCategoryName(String managerCategoryName) {
		this.managerCategoryName = managerCategoryName;
	}

	public Integer getmUid() {
		return mUid;
	}

	public void setmUid(Integer mUid) {
		this.mUid = mUid;
	}

	public String getmUname() {
		return mUname;
	}

	public void setmUname(String mUname) {
		this.mUname = mUname;
	}
    
}