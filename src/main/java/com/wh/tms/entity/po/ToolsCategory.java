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
@Table(name = "t_tools_category")
public class ToolsCategory implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="JDBC")
    private Integer id;

    /**
     * 机具编号
     */
    private String categoryNumber;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 入库时间
     */
    private Date insertTime;
    /**
     * 库存数
     */
    private Integer inventoryCount;
    /**
     * 机具管理类别id
     */
    private Integer managerCategoryId;
    /**
     * 机具管理类别名称
     */
    private String managerCategoryName;


    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
	public Integer getInventoryCount() {
		return inventoryCount;
	}

	public void setInventoryCount(Integer inventoryCount) {
		this.inventoryCount = inventoryCount;
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
	
    
}