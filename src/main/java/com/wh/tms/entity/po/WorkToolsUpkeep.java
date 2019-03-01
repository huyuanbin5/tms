package com.wh.tms.entity.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 机具保养
* @ClassName: WorkToolsUpkeep 
* @Description: TODO 
* @author Huqk
* @date 2018年5月5日 下午1:24:00
 */
@Table(name = "t_work_tools_upkeep")
public class WorkToolsUpkeep implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="JDBC")
    private Integer id;

    /**
     * 机具id
     */
    private Integer toolsId;

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
     * 保养人uid
     */
    private Integer operUid;

    /**
     * 保养人名称
     */
    private String operUname;

    /**
     * 保养内容id
     */
    private Integer operConId;

    /**
     * 保养内容
     */
    private String operCon;

    /**
     * 保养状态标识,0:未保养,1:已保养
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
     * 实际保养时间
     */
    private Date operTime;

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

    public Integer getOperConId() {
        return operConId;
    }

    public void setOperConId(Integer operConId) {
        this.operConId = operConId;
    }

    public String getOperCon() {
        return operCon;
    }

    public void setOperCon(String operCon) {
        this.operCon = operCon;
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

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }
}