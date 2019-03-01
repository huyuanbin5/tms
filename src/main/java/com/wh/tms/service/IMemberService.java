package com.wh.tms.service;

import com.github.pagehelper.PageInfo;
import com.wh.tms.entity.po.Member;
import com.wh.tms.result.Result;

public interface IMemberService {
	
	/**
	 * 获取用户列表
	* @Title: getLoginUserList 
	* @Description: TODO
	* @param @param deptid
	* @param @param uname
	* @param @param page
	* @param @param pageSize
	* @param @return 
	* @return PageInfo<LoginUser>
	* @throws
	 */
	PageInfo<Member> getMemberList(Integer uid,Integer deptid,String mName,Integer page,Integer pageSize);
	/**
	 * 更新用户信息
	* @Title: updateLoginUser 
	* @Description: TODO
	* @param @param uid
	* @param @param deptId
	* @param @param sex
	* @param @return 
	* @return boolean
	* @throws
	 */
	Result updateMember(Integer uid,Integer deptId,Integer teamId,String mName,Integer sex);
	/**
	 * 删除成员
	* @Title: deleteMember 
	* @Description: TODO
	* @param @param uid
	* @param @return 
	* @return Result
	* @throws
	 */
	Result deleteMember(Integer uid);
	/**
	 * 添加成员
	* @Title: addMember 
	* @Description: TODO
	* @param @param deptId
	* @param @param mName
	* @param @param sex
	* @param @return 
	* @return Result
	* @throws
	 */
	Result addMember(Integer deptId,Integer teamId,String mName,Integer sex);

}
