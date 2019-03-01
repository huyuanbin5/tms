package com.wh.tms.entity.vo;

import java.util.List;

/**
 * 部门VO
 * @author Jesson
 *
 */
public class DeptVo {
	
    private Integer id;
	/**
	 * 父级id
	 */
	private Integer pid;
	/**
	 * 部门名称
	 */
	private String name;
	/**
	 * 路径
	 */
	private String xpath;
	/**
	 * 层级
	 */
	private Integer level;
	/**
	 * 排序id
	 */
	private Integer orderId;
	/**
	 * 子节点
	 */
	private List<DeptVo> children;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getXpath() {
		return xpath;
	}
	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public List<DeptVo> getChildren() {
		return children;
	}
	public void setChildren(List<DeptVo> children) {
		this.children = children;
	}
	
}
