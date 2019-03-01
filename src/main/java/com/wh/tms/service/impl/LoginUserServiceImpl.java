package com.wh.tms.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wh.tms.constans.SystemCons;
import com.wh.tms.dao.sys.IDeptDAO;
import com.wh.tms.dao.user.ILoginUserDAO;
import com.wh.tms.entity.po.Dept;
import com.wh.tms.entity.po.LoginUser;
import com.wh.tms.result.Result;
import com.wh.tms.service.ILoginUserService;
import com.wh.tms.util.EncodeUtils;
import com.wh.tms.util.ResultUtils;

@Service("loginUserService")
public class LoginUserServiceImpl implements ILoginUserService {
	
	private static final Log log = LogFactory.getLog(LoginUserServiceImpl.class);
	
	
	@Autowired
	private ILoginUserDAO loginUserDao;
	@Autowired
	private IDeptDAO deptDao;

	@Override
	public LoginUser getLoginUserById(Integer uid) {
		if (null != uid) {
			return loginUserDao.selectByPrimaryKey(uid);
		}
		return null;
	}

	@Override
	public LoginUser getLoginUser(String uname, String password) {
		if (StringUtils.isNotBlank(uname) && StringUtils.isNotBlank(password)) {
			Example example = new Example(LoginUser.class);
			example.createCriteria().andEqualTo("account", uname).andEqualTo("password", password);
			List<LoginUser> list = loginUserDao.selectByExample(example);
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
		}
		
		return null;
	}
	
	@Override
	public PageInfo<LoginUser> getLoginUserList(Integer deptid, String uname,
			Integer page, Integer pageSize) {
		
		PageHelper.startPage(page, pageSize);
		Example example = new Example(LoginUser.class);
		Criteria criteria = example.createCriteria();
		if (null != deptid) {
			criteria.andEqualTo("deptId", deptid);
		}
		if (StringUtils.isNotBlank(uname)) {
			criteria.andLike("userName", uname);
		}
		List<LoginUser> list = null;
		try {
			list = loginUserDao.selectByExample(example);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return new PageInfo<LoginUser>(list);
	}

	@Override
	public Result updateLoginUser(Integer uid, Integer deptId, Integer sex) {
		if (null == uid) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "用户id不能为空！");
		}
		LoginUser user = loginUserDao.selectByPrimaryKey(uid);
		if (null == user) {
			return ResultUtils.error(SystemCons.FAILURE, "用户不存在！");
		}
		if (null != deptId) {
			//TODO 查询部门
			Dept dept = deptDao.selectByPrimaryKey(deptId);
			if (null == dept) {
				return ResultUtils.error(SystemCons.FAILURE, "部门不存在！");
			}
			user.setDeptId(deptId);
			user.setDeptName(dept.getDeptName());
		}
		if (null != sex) {
			if (sex.intValue() < 1 || sex.intValue() > 2) {
				return ResultUtils.error(SystemCons.FAILURE, "未知性别！");
			}
		}
		int updateCount = 0;
		try {
			updateCount = loginUserDao.updateByPrimaryKeySelective(user);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		if (updateCount < 1) {
			return ResultUtils.error(SystemCons.FAILURE, "更新失败！");
		}
		return ResultUtils.success(updateCount, "");
	}

	@Override
	public int update_loginUser(LoginUser user) {
		if (null == user) {
			return 0;
		}
		return this.loginUserDao.updateByPrimaryKeySelective(user);
	}

	@Override
	public Result addLoginUser(String uName,String account,String password,Integer deptId,Integer officPhone,String email,String mobilePhone,Integer sex) {
		if (StringUtils.isBlank(uName)) {
			return ResultUtils.paramsEmpty("用户名不能为空!");
		}
		if (StringUtils.isBlank(account)) {
			return ResultUtils.paramsEmpty("账号不能为空!");
		}
		if (StringUtils.isBlank(password)) {
			return ResultUtils.paramsEmpty("密码不能为空!");
		}
		if (null == sex) {
			return ResultUtils.paramsEmpty("性别不能为空!");
		}
		if (null ==  officPhone && StringUtils.isBlank(mobilePhone)) {
			return ResultUtils.paramsEmpty("电话号码不能为空!");
		}
		if (null == deptId) {
			return ResultUtils.paramsEmpty("所属部门不能为空!");
		}
		Dept dept = deptDao.selectByPrimaryKey(deptId);
		if (null == dept) {
			return ResultUtils.error(SystemCons.FAILURE, "部门不存在！");
		}
		uName = EncodeUtils.urlDecode(uName);
		LoginUser user = new LoginUser();
		user.setUserName(uName);
		user.setAccount(account);
		user.setPassword(password);
		user.setDeptId(deptId);
		user.setDeptName(dept.getDeptName());
		user.setOfficPhone(officPhone);
		user.setSex(sex);
		user.setMobilePhone(mobilePhone);
		user.setInsertTime(new Date());
		int insertCount = this.loginUserDao.insertSelective(user);
		if (insertCount < 1) {
			return ResultUtils.failure("新增用户失败!");
		}
		return ResultUtils.success(user);
	}
	@Override
	public Result deleteLoginUser(Integer id) {
		if (null == id) {
			return ResultUtils.paramsEmpty("参数不能为空!");
		}
		LoginUser user = loginUserDao.selectByPrimaryKey(id);
		if (null == user) {
			return ResultUtils.failure("用户不存在!");
		}
		Integer status = user.getStatus() == null ? 0 : user.getStatus();
		if (status.intValue() != -1) {
			user.setStatus(-1);
			int updateCount = loginUserDao.updateByPrimaryKeySelective(user);
			if (updateCount > 0) {
				return ResultUtils.success("删除成功!");
			}
		}
		return ResultUtils.failure("删除失败！");
	}


}
