package com.wh.tms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.wh.tms.dao.sys.IUserRoleDAO;
import com.wh.tms.entity.po.UserRole;
import com.wh.tms.service.IUserRoleService;

@Service("userRoleService")
public class UserRoleServiceImpl implements IUserRoleService {
	
	@Autowired
	private IUserRoleDAO userRoleDao;

	@Override
	public UserRole getUserRole(Integer uid) {
		if (null != uid) {
			Example example = new Example(UserRole.class);
			example.createCriteria().andEqualTo("uid", uid);
			List<UserRole> userRoleList = userRoleDao.selectByExample(example);
			if (null != userRoleList && userRoleList.size() > 0) {
				return userRoleList.get(0);
			}
		}
		return null;
	}

}
