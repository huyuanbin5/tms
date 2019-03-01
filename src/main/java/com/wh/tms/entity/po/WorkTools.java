package com.wh.tms.entity.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 
 */
@Table(name = "t_work_tools")
public class WorkTools implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="JDBC")
    private Integer id;

    /**
     * 操作人id
     */
    private Integer uid;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 所属部门id
     */
    private Integer deptId;

    /**
     * 所属部门名称
     */
    private String deptName;

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
     * 机具管理类别id
     */
    private Integer managerCategoryId;

    /**
     * 机具管理类别名称
     */
    private String managerCategoryName;

    /**
     * 货位id
     */
    private Integer repositoryId;

    /**
     * 具体货位
     */
    private String repositoryName;

    /**
     * 制造商id
     */
    private Integer manufacturerId;

    /**
     * 制造商名称
     */
    private String manufacturerName;

    /**
     * 供应商id
     */
    private Integer supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 厂家编号
     */
    private String factoryNumber;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 机具入库时间
     */
    private Date insertTime;

    private String inStorageTime;

    /**
     * 新增个数
     */
    private Integer addCount;

    /**
     * 机具编码
     */
    private String toolsCode;

    /**
     * 使用年限
     */
    private String yearLimit;

    /**
     * 评估价值
     */
    private Integer evaluation;

    /**
     * 状态0:正常,1:维修中,2:检验中,3:借出,4:挂失,5:建议报废,6:确认丢失,7:确认报废,8:非发放状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

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

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public Integer getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Integer repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getFactoryNumber() {
        return factoryNumber;
    }

    public void setFactoryNumber(String factoryNumber) {
        this.factoryNumber = factoryNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getInStorageTime() {
        return inStorageTime;
    }

    public void setInStorageTime(String inStorageTime) {
        this.inStorageTime = inStorageTime;
    }

    public Integer getAddCount() {
        return addCount;
    }

    public void setAddCount(Integer addCount) {
        this.addCount = addCount;
    }

    public String getToolsCode() {
        return toolsCode;
    }

    public void setToolsCode(String toolsCode) {
        this.toolsCode = toolsCode;
    }

    public String getYearLimit() {
        return yearLimit;
    }

    public void setYearLimit(String yearLimit) {
        this.yearLimit = yearLimit;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}