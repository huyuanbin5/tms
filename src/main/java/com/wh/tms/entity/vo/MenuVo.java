package com.wh.tms.entity.vo;

import java.util.List;

public class MenuVo {
	
	private Integer id;
    /**
     * 菜单父节点id
     */
    private Integer pid;

    /**
     * 菜单路径
     */
    private String xpath;

    /**
     * 菜单名
     */
    private String mName;

    /**
     * 菜单url
     */
    private String mUrl;
    /**
     * 子节点
     */
    private List<MenuVo> children;
    
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
	public String getXpath() {
		return xpath;
	}
	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public String getmUrl() {
		return mUrl;
	}
	public void setmUrl(String mUrl) {
		this.mUrl = mUrl;
	}
	public List<MenuVo> getChildren() {
		return children;
	}
	public void setChildren(List<MenuVo> children) {
		this.children = children;
	}
    
    

}
