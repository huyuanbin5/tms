package com.wh.tms.entity.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 
 */
@Table(name="t_user_role")
public class UserRole implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="JDBC")
    private Integer id;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 角色id
     */
    @Column(name="role_id")
    private Integer roleId;

    /**
     * 角色名称
     */
    @Column(name="role_name")
    private String roleName;
    /**
	 * 权限标识,0:普通角色，1:系统管理员
	 */
	private Integer sysFlag;

    /**
     * 创建时间
     */
    @Column(name="insert_time")
    private Date insertTime;

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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

	public Integer getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(Integer sysFlag) {
		this.sysFlag = sysFlag;
	}
    
}