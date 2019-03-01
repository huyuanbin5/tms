package com.wh.tms.service;

import com.github.pagehelper.PageInfo;
import com.wh.tms.entity.po.LoginUser;
import com.wh.tms.result.Result;

/**
 * 登录用户相关service
* @ClassName: LoginUserService 
* @Description: TODO 
* @author Huqk
* @date 2018年3月6日 下午10:01:29
 */
public interface ILoginUserService {
	
	/**
	 * 通过用户id获取用户信息
	* @Title: getLoginUser 
	* @Description: TODO
	* @param @param uid
	* @param @return 
	* @return LoginUser
	* @throws
	 */
	LoginUser getLoginUserById(Integer uid);
	/**
	 * 通过用户名密码获取登录用户
	* @Title: getLoginUser 
	* @Description: TODO
	* @param @param uname
	* @param @param password
	* @param @return 
	* @return LoginUser
	* @throws
	 */
	LoginUser getLoginUser(String uname,String password);
	
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
	PageInfo<LoginUser> getLoginUserList(Integer deptid,String uname,Integer page,Integer pageSize);
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
	Result updateLoginUser(Integer uid,Integer deptId,Integer sex);
	/**
	 * 添加用户
	* @Title: addLoginUser 
	* @Description: TODO
	* @param @param uName
	* @param @param account
	* @param @param password
	* @param @param deptId
	* @param @param phone
	* @param @param email
	* @param @return 
	* @return Result
	* @throws
	 */
	Result addLoginUser(String uName,String account,String password,Integer deptId,Integer officPhone,String email,String mobilePhone,Integer sex);
	/**
	 * 更新用户
	* @Title: update_loginUser 
	* @Description: TODO
	* @param @param user
	* @param @return 
	* @return int
	* @throws
	 */
	int update_loginUser(LoginUser user);
	/**
	 *删除用户
	* @Title: deleteLoginUser 
	* @Description: TODO
	* @param @param id
	* @param @return 
	* @return Result
	* @throws
	 */
	Result deleteLoginUser(Integer id);

}
