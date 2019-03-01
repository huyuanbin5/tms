package com.wh.tms.entity.po;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 工机具字典
* @ClassName: WorkToolsDictionary 
* @Description: TODO 
* @author Huqk
* @date 2018年4月29日 下午11:17:52
 */
@Table(name = "t_work_tools_dictionary")
public class WorkToolsDictionary implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 5183272119086871582L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="JDBC")
	private Integer id;
	/**
	 * 字典类型标识 1：制造商,2:供应商
	 */
	private Integer type;
	/**
	 * 名称
	 */
	private String name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
