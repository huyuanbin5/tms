package com.wh.tms.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wh.tms.constans.SystemCons;
import com.wh.tms.dao.sys.IRoleDAO;
import com.wh.tms.dao.sys.IRoleMenuDAO;
import com.wh.tms.dao.sys.IUserRoleDAO;
import com.wh.tms.entity.po.Role;
import com.wh.tms.entity.po.RoleMenu;
import com.wh.tms.entity.po.UserRole;
import com.wh.tms.result.Result;
import com.wh.tms.service.IRoleService;
import com.wh.tms.util.EncodeUtils;
import com.wh.tms.util.ResultUtils;

@Service("roleService")
public class RoleServiceImpl implements IRoleService {
	
	private static final Log log = LogFactory.getLog(RoleServiceImpl.class);
	
	@Autowired
	private IRoleDAO roleDao;
	@Autowired
	private IRoleMenuDAO roleMenuDao;
	@Autowired
	private IUserRoleDAO userRoleDao;

	@Override
	public Result getRoleList(String roleName,int page, int pageSize) {
		PageHelper.startPage(page, pageSize);
		PageInfo<Role> pageInfo = null;
		try {
			List<Role> roleList = null;
			if (StringUtils.isNotBlank(roleName)) {
				roleName = EncodeUtils.urlDecode(roleName);
				Example example = new Example(Role.class);
				example.createCriteria().andLike("roleName", roleName);
				roleList = roleDao.selectByExample(example);
			}else{
				roleList = roleDao.selectAll();
			}
			if (null != roleList) {
				pageInfo = new PageInfo<Role>(roleList);
			}else{
				pageInfo = new PageInfo<Role>();
			}
			return ResultUtils.success(pageInfo.getTotal(), roleList);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.error(SystemCons.FAILURE, "获取角色列表失败!");
	}

	@Override
	public Result addRole(String roleName, String remark) {
		if (StringUtils.isBlank(roleName)) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "角色名称不能为空!");
		}
		try {
			roleName = EncodeUtils.urlDecode(roleName);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		if (StringUtils.isNotBlank(remark)) {
			try {
				remark = EncodeUtils.urlDecode(remark);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		
		Example example = new Example(Role.class);
		example.createCriteria().andEqualTo("roleName", roleName);
		int selectCount = roleDao.selectCountByExample(example);
		if (selectCount > 0) {
			return ResultUtils.error(SystemCons.FAILURE, "添加失败，角色已存在!");
		}
		Role role = new Role();
		role.setRoleName(roleName);
		role.setRemark(remark);
		role.setInsertTime(new Date());
		int saveCount = 0;
		try {
			saveCount = roleDao.insert(role);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		if (saveCount > 0) {
			return ResultUtils.success(saveCount, role);
		}
		return ResultUtils.error(SystemCons.FAILURE, "添加失败!");
	}

	@Override
	public Result updateRole(Integer roleId, String roleName, String remark) {
		if (null == roleId || roleId.intValue() == 0) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "角色ID不能为空!");
		}
		if (StringUtils.isBlank(roleName)) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "角色名称不能为空!");
		}
		Role role = roleDao.selectByPrimaryKey(roleId);
		if (null == role) {
			return ResultUtils.error(SystemCons.FAILURE, "角色不存在!");
		}
		try {
			roleName = EncodeUtils.urlDecode(roleName);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		if (StringUtils.isNotBlank(remark)) {
			try {
				remark = EncodeUtils.urlDecode(remark);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		role.setRoleName(roleName);
		role.setRemark(remark);
		role.setUpdateTime(new Date());
		int updateCount = 0;
		try {
			updateCount = roleDao.updateByPrimaryKeySelective(role);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		if (updateCount > 0) {
			return ResultUtils.success(updateCount, role, "更新成功!");
		}
		return ResultUtils.error(SystemCons.FAILURE, "更新失败!");
	}

	@Override
	@Transactional
	public Result deleteRole(Integer roleId) {
		if (null == roleId || roleId.intValue() == 0) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "角色ID不能为空!");
		}
		Role role = roleDao.selectByPrimaryKey(roleId);
		if (null == role) {
			return ResultUtils.success(1, null, "删除成功!");
		}
		int deleteCount = 0;
		Example example = null;
		try {
			deleteCount = roleDao.deleteByPrimaryKey(roleId);
			if (deleteCount > 0) {
				//删除角色分配的菜单
				example = new Example(RoleMenu.class);
				example.createCriteria().andEqualTo("roleId", roleId);
				roleMenuDao.deleteByExample(example);
				//删除用户与角色关系
				example = new Example(UserRole.class);
				example.createCriteria().andEqualTo("roleId", roleId);
				userRoleDao.deleteByExample(example);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		if (deleteCount > 0) {
			return ResultUtils.success(deleteCount, null, "删除成功!");
		}
		return ResultUtils.error(SystemCons.FAILURE, "删除失败!");
	}

}
