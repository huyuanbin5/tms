package com.wh.tms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.wh.tms.dao.sys.IDeptDAO;
import com.wh.tms.entity.po.Dept;
import com.wh.tms.entity.vo.DeptVo;
import com.wh.tms.result.Result;
import com.wh.tms.service.IDeptService;
import com.wh.tms.util.EncodeUtils;
import com.wh.tms.util.ResultUtils;

@Service("deptService")
public class DeptServiceImpl implements IDeptService {

	private static final Log log = LogFactory.getLog(DeptServiceImpl.class);
	@Autowired
	private IDeptDAO deptDao;
	
	@Override
	public List<DeptVo> getDeptList(Integer deptId) {
		Example example = new Example(Dept.class);
		if (null != deptId) {
			Dept dept = deptDao.selectByPrimaryKey(deptId);
			if (null == dept) {
				return null;
			}
			String xpath = dept.getXpath();
			if (StringUtils.isBlank(xpath)) {
				return null;
			}
			Example.Criteria criteria = example.createCriteria();
			criteria.andLike("xpath", xpath + "%");
		}
		example.setOrderByClause(" pid asc ");
		List<Dept> list = deptDao.selectByExample(example);
		if (null == list || list.size() == 0) {
			return null;
		}
		
		List<DeptVo> treeList = new ArrayList<DeptVo>();
		DeptVo vo = null;
		for (Dept dept : list) {
			vo = new DeptVo();
			try {
				BeanUtils.copyProperties(vo, dept);
				vo.setName(dept.getDeptName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			treeList.add(vo);
		}
		//根节点
		Map<Integer,DeptVo> map = new HashMap<Integer,DeptVo>();
		DeptVo root = new DeptVo();
		if (null != deptId) {
			root.setId(deptId);
		}else{
			root.setId(0);
		}
		
		map.put(root.getId(), root);

		// 初始化所有章节
		for (DeptVo dept : treeList) {
			map.put(dept.getId(), dept);
		}
		for (DeptVo childVo : treeList) {
			if (map.containsKey(childVo.getPid())) {
				// 取父节点
				DeptVo parent = map.get(childVo.getPid());
				// 取子节点
				List<DeptVo> children = parent.getChildren();
				if (children == null) {
					children = new ArrayList<DeptVo>();
					parent.setChildren(children);
				}
				// 追加子节点
				children.add(map.get(childVo.getId()));
			}

		}
		if (null != deptId) {
			List<DeptVo> datas = new ArrayList<DeptVo>();
			datas.add(map.get(deptId));
			return datas;
		}else{
			return root.getChildren();
		}
	}

	@Override
	@Transactional
	public Result addDept(Integer pid, String name) {
		if (null == pid || StringUtils.isBlank(name)) {
			return ResultUtils.paramsEmpty("参数为空或无效!");
		}
		try {
			name = EncodeUtils.urlDecode(name);
		} catch (Exception e) {
			return ResultUtils.failure("无效的部门名称!");
		}
		Integer level = 0;
		Dept parentDept = null;
		Dept dept = new Dept();
		dept.setDeptName(name);
		dept.setPid(pid);
		if (pid.intValue() == 0) {
			//添加根节点
			dept.setLevel(1);
			dept.setOrderId(1);
		}else{
			parentDept = this.deptDao.selectByPrimaryKey(pid);
			if (null == parentDept) {
				return ResultUtils.failure("父节点不存在!");
			}
			//取最后一个子节点
			Example ex = new Example(Dept.class);
			ex.createCriteria().andEqualTo("pid", pid);
			ex.orderBy("id").desc();
			List<Dept> list = this.deptDao.selectByExample(ex);
			if (null == list || list.size() == 0) {
				level = parentDept.getLevel();
				if (null != level) {
					dept.setLevel(level + 1);
				}
			}else{
				Dept childDept = list.get(0);
				level = childDept.getLevel();
				Integer order = childDept.getOrderId();
				if (null != level) {
					dept.setLevel(level);
				}
				if (null != order) {
					dept.setOrderId(order + 1);
				}
			}
		}
		
		int saveCount = this.deptDao.insertSelective(dept);
		if (saveCount < 1 || dept.getId() == null) {
			return ResultUtils.failure("添加部门失败!");
		}
		Integer parentId = dept.getId();
		
		if (null == parentDept) {
			dept.setXpath(parentId+"/");
		}else{
			String xpath = parentDept.getXpath();
			dept.setXpath(xpath+parentId);
		}
		this.deptDao.updateByPrimaryKeySelective(dept);
		return ResultUtils.success(dept);
		
	}

	@Override
	@Transactional
	public Result deleteDept(Integer deptId) {
		if (null == deptId) {
			return ResultUtils.paramsEmpty("部门id不能为空!");
		}
		//获取部门信息
		Dept dept = this.deptDao.selectByPrimaryKey(deptId);
		if (null == dept) {
			return ResultUtils.failure("部门不存在!");
		}
		//获取部门下子节点
		String xpath = dept.getXpath();
		if (StringUtils.isBlank(xpath)) {
			return ResultUtils.failure("部门信息异常!");
		}
		Example ex = new Example(Dept.class);
		ex.createCriteria().andLike("xpath", xpath+"%");
		//节点
		this.deptDao.deleteByExample(ex);
		return ResultUtils.success(1, null, "删除成功!");
	}

	@Override
	public Result updateDept(Integer deptId,String name) {
		if (null == deptId || StringUtils.isBlank(name)) {
			return ResultUtils.paramsEmpty("参数不能为空!");
		}
		try {
			name = EncodeUtils.urlDecode(name);
		} catch (Exception e) {
			return ResultUtils.failure("无效的部门名称!");
		}
		Dept dept = this.deptDao.selectByPrimaryKey(deptId);
		if (null == dept) {
			return ResultUtils.failure("部门不存在!");
		}
		dept.setDeptName(name);
		long updateCount = 0;
		try {
			updateCount = deptDao.updateByPrimaryKeySelective(dept);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return updateCount > 0 ? ResultUtils.success(updateCount,dept,"更新成功!") : ResultUtils.failure("更新失败!");
	}

}
