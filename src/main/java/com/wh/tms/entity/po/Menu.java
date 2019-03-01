package com.wh.tms.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 
 */
@Table(name="t_menu")
public class Menu implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="JDBC")
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
    @Column(name = "m_name")
    private String mName;

    /**
     * 菜单url
     */
    @Column(name = "m_url")
    private String mUrl;

    private static final long serialVersionUID = 1L;

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
}