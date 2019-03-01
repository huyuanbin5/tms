package com.wh.tms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.wh.tms.constans.SystemCons;
import com.wh.tms.dao.sys.IMenuDAO;
import com.wh.tms.dao.sys.IRoleDAO;
import com.wh.tms.dao.sys.IRoleMenuDAO;
import com.wh.tms.dao.sys.IUserRoleDAO;
import com.wh.tms.entity.po.Menu;
import com.wh.tms.entity.po.Role;
import com.wh.tms.entity.po.RoleMenu;
import com.wh.tms.entity.po.UserRole;
import com.wh.tms.entity.vo.MenuVo;
import com.wh.tms.result.Result;
import com.wh.tms.service.IMenuService;
import com.wh.tms.util.ResultUtils;

@Service("menuService")
public class MenuServiceImpl implements IMenuService {
	
	private static final Log log = LogFactory.getLog(MenuServiceImpl.class);
	
	@Autowired
	private IUserRoleDAO userRoleDao;
	@Autowired
	private IRoleDAO roleDao;
	@Autowired
	private IRoleMenuDAO roleMenuDao;
	@Autowired
	private IMenuDAO menuDao;

	@Override
	public List<MenuVo> getUserMenuList(Integer uid) {
		if (null == uid || uid.intValue() == 0) {
			return null;
		}
		//获取权限
		Example example = new Example(UserRole.class);
		example.createCriteria().andEqualTo("uid", uid);
		List<UserRole> userRoles = userRoleDao.selectByExample(example);
		if (null == userRoles || userRoles.size() == 0) {
			return null;
		}
		
		Set<Integer> roleIds = new HashSet<Integer>();
		for (UserRole userRole : userRoles) {
			roleIds.add(userRole.getRoleId());
		}
		//获取权限对应菜单
		example = new Example(RoleMenu.class);
		example.createCriteria().andIn("roleId", roleIds);
		List<RoleMenu> roleMenus = roleMenuDao.selectByExample(example);
		if (null == roleMenus || roleMenus.size() == 0) {
			return null;
		}
		Set<Integer> menuIds = new HashSet<Integer>();
		for (RoleMenu roleMenu : roleMenus) {
			menuIds.add(roleMenu.getMenuId());
		}
		//获取菜单信息
		example = new Example(Menu.class);
		example.createCriteria().andIn("id", menuIds);
		example.setOrderByClause("id asc");
		return getMenuTreeNode(menuDao.selectByExample(example));
	}
	
	@Override
	public List<MenuVo> getRoleMenuList(Integer roleId) {
		if (null == roleId || roleId.intValue() == 0) {
			return getMenuTreeNode(menuDao.selectAll());
		}
		Role role = roleDao.selectByPrimaryKey(roleId);
		if (null == role) {
			return null;
		}
		Example example = new Example(RoleMenu.class);
		example.createCriteria().andEqualTo("roleId", roleId);
		List<RoleMenu> roleMenus = roleMenuDao.selectByExample(example);
		if (null == roleMenus || roleMenus.size() == 0) {
			return null;
		}
		Set<Integer> menuIds = new HashSet<Integer>();
		for (RoleMenu roleMenu : roleMenus) {
			menuIds.add(roleMenu.getMenuId());
		}
		//获取菜单信息
		example = new Example(Menu.class);
		example.createCriteria().andIn("id", menuIds);
		example.setOrderByClause("id asc");
		return getMenuTreeNode(menuDao.selectByExample(example));
	}
	
	@Override
	@Transactional
	public Result saveOrUpdateRoleMenus(Integer roleId, String menuIds) {
		if (null == roleId) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "角色id不能为空！");
		}
		if (StringUtils.isBlank(menuIds)) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "菜单id不能为空！");
		}
		String[] $menuIds = null;
		try {
			$menuIds = menuIds.split(",");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		if (null == $menuIds || $menuIds.length == 0) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "多个菜单id以逗号分隔!");
		}
		Set<Integer> mids = new HashSet<Integer>();
		for (String mid : $menuIds) {
			try {
				mids.add(Integer.parseInt(mid));
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		if (mids.size() == 0) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "无效的菜单id！");
		}
		
		Example example = null;
		//获取角色
		Role role = roleDao.selectByPrimaryKey(roleId);
		if (null == role) {
			//删除用户对应权限
			example = new Example(UserRole.class);
			example.createCriteria().andEqualTo("roleId", roleId);
			List<UserRole> userRoles = userRoleDao.selectByExample(example);
			if (null != userRoles && userRoles.size() > 0) {
				userRoleDao.deleteByExample(example);
			}
			//删除角色对应菜单
			deleteRoleMenu(roleId, example);
			return ResultUtils.error(SystemCons.FAILURE, "角色不存在！");
		}
		
		deleteRoleMenu(roleId, example);
		int updateCont = 0;
		try {
			example = new Example(Menu.class);
			example.createCriteria().andIn("id", mids);
			example.setOrderByClause("id asc");
			List<Menu> menus = menuDao.selectByExample(example);
			if (null != menus && menus.size() > 0) {
				//入库
				RoleMenu roleMenu = null;
				for (Menu menu : menus) {
					roleMenu = new RoleMenu();
					roleMenu.setRoleId(roleId);
					roleMenu.setMenuId(menu.getId());
					try {
						updateCont += roleMenuDao.insert(roleMenu);
					} catch (Exception e) {
						log.error(e.getMessage());
					}
					
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResultUtils.error(SystemCons.FAILURE, "更新失败");
		}
		
		return ResultUtils.success(updateCont, null, "更新成功!");
	}

	private void deleteRoleMenu(Integer roleId, Example example) {
		example = new Example(RoleMenu.class);
		example.createCriteria().andEqualTo("roleId", roleId);
		List<RoleMenu> roleMenus = roleMenuDao.selectByExample(example);
		//是否有分配过权限
		if (null != roleMenus && roleMenus.size() > 0) {
			//删除角色对应资源
			roleMenuDao.deleteByExample(example);
		}
	}
	
	/**
	 * 构造树形结构数据
	* @Title: getMenuTreeNode 
	* @Description: TODO
	* @param @param menus
	* @param @return 
	* @return List<MenuVo>
	* @throws
	 */
	private List<MenuVo> getMenuTreeNode(List<Menu> menus) {
		if (null == menus || menus.size() == 0) {
			return null;
		}
		List<MenuVo> treeList = new ArrayList<MenuVo>();
		MenuVo vo = null;
		for (Menu menu : menus) {
			vo = new MenuVo();
			try {
				BeanUtils.copyProperties(vo, menu);
			} catch (Exception e) {
				e.printStackTrace();
			}
			treeList.add(vo);
		}
		//根节点
		Map<Integer,MenuVo> map = new HashMap<Integer,MenuVo>();
		MenuVo root = new MenuVo();
		root.setId(0);
		map.put(root.getId(), root);

		// 初始化所有章节
		for (MenuVo menu : treeList) {
			map.put(menu.getId(), menu);
		}
		for (MenuVo childVo : treeList) {
			if (map.containsKey(childVo.getPid())) {
				// 取父节点
				MenuVo parent = map.get(childVo.getPid());
				// 取子节点
				List<MenuVo> children = parent.getChildren();
				if (children == null) {
					children = new ArrayList<MenuVo>();
					parent.setChildren(children);
				}
				// 追加子节点
				children.add(map.get(childVo.getId()));
			}

		}
		return root.getChildren();
	}


	

}
