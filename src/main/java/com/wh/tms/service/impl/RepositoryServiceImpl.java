package com.wh.tms.service.impl;

import java.util.ArrayList;
import java.util.Date;
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

import com.wh.tms.dao.sys.IRepositoryDAO;
import com.wh.tms.entity.po.Repository;
import com.wh.tms.entity.vo.RepositoryVo;
import com.wh.tms.result.Result;
import com.wh.tms.service.IRepositoryService;
import com.wh.tms.util.EncodeUtils;
import com.wh.tms.util.ResultUtils;

@Service("repositoryServices")
public class RepositoryServiceImpl implements IRepositoryService {

	private static final Log log = LogFactory.getLog(RepositoryServiceImpl.class);
	@Autowired
	private IRepositoryDAO repositoryDao;
	
	@Override
	public List<RepositoryVo> getGoodList(Integer id) {
		Example example = new Example(Repository.class);
		if (null != id) {
			Repository repository = repositoryDao.selectByPrimaryKey(id);
			if (null == repository) {
				return null;
			}
			String xpath = repository.getXpath();
			if (StringUtils.isBlank(xpath)) {
				return null;
			}
			Example.Criteria criteria = example.createCriteria();
			criteria.andLike("xpath", xpath + "%");
		}
		example.setOrderByClause(" pid asc ");
		List<Repository> list = repositoryDao.selectByExample(example);
		if (null == list || list.size() == 0) {
			return null;
		}
		
		List<RepositoryVo> treeList = new ArrayList<RepositoryVo>();
		RepositoryVo vo = null;
		for (Repository repository : list) {
			vo = new RepositoryVo();
			try {
				BeanUtils.copyProperties(vo, repository);
				vo.setName(repository.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			treeList.add(vo);
		}
		//根节点
		Map<Integer,RepositoryVo> map = new HashMap<Integer,RepositoryVo>();
		RepositoryVo root = new RepositoryVo();
		if (null != id) {
			root.setId(id);
		}else{
			root.setId(0);
		}
		
		map.put(root.getId(), root);

		// 初始化所有数据
		for (RepositoryVo re : treeList) {
			map.put(re.getId(), re);
		}
		for (RepositoryVo childVo : treeList) {
			if (map.containsKey(childVo.getPid())) {
				// 取父节点
				RepositoryVo parent = map.get(childVo.getPid());
				// 取子节点
				List<RepositoryVo> children = parent.getChildren();
				if (children == null) {
					children = new ArrayList<RepositoryVo>();
					parent.setChildren(children);
				}
				// 追加子节点
				children.add(map.get(childVo.getId()));
			}

		}
		if (null != id) {
			List<RepositoryVo> datas = new ArrayList<RepositoryVo>();
			datas.add(map.get(id));
			return datas;
		}else{
			return root.getChildren();
		}
	}

	@Override
	@Transactional
	public Result addGoods(Integer pid, String name) {
		if (null == pid || StringUtils.isBlank(name)) {
			return ResultUtils.paramsEmpty("参数为空或无效!");
		}
		try {
			name = EncodeUtils.urlDecode(name);
		} catch (Exception e) {
			return ResultUtils.failure("无效的货位名称!");
		}
		Integer level = 0;
		Repository parentDept = null;
		Repository re = new Repository();
		re.setName(name);
		re.setPid(pid);
		re.setInsertTime(new Date());
		if (pid.intValue() == 0) {
			//添加根节点
			re.setLevel(1);
			re.setOrderId(1);
		}else{
			parentDept = this.repositoryDao.selectByPrimaryKey(pid);
			if (null == parentDept) {
				return ResultUtils.failure("父节点不存在!");
			}
			//取最后一个子节点
			Example ex = new Example(Repository.class);
			ex.createCriteria().andEqualTo("pid", pid);
			ex.orderBy("id").desc();
			List<Repository> list = this.repositoryDao.selectByExample(ex);
			if (null == list || list.size() == 0) {
				level = parentDept.getLevel();
				if (null != level) {
					re.setLevel(level + 1);
				}
				re.setOrderId(1);
			}else{
				Repository childRes = list.get(0);
				level = childRes.getLevel();
				Integer order = childRes.getOrderId();
				if (null != level) {
					re.setLevel(level);
				}
				if (null != order) {
					re.setOrderId(order + 1);
				}
			}
		}
		
		int saveCount = this.repositoryDao.insertSelective(re);
		if (saveCount < 1 || re.getId() == null) {
			return ResultUtils.failure("添加部门失败!");
		}
		Integer parentId = re.getId();
		
		if (null == parentDept) {
			re.setXpath(parentId+"/");
		}else{
			String xpath = parentDept.getXpath();
			re.setXpath(xpath+parentId);
		}
		this.repositoryDao.updateByPrimaryKeySelective(re);
		return ResultUtils.success(re);
		
	}

	@Override
	@Transactional
	public Result deleteGoods(Integer id) {
		if (null == id) {
			return ResultUtils.paramsEmpty("货位id不能为空!");
		}
		//获取货位信息
		Repository re = this.repositoryDao.selectByPrimaryKey(id);
		if (null == re) {
			return ResultUtils.failure("部门不存在!");
		}
		//获取子节点
		String xpath = re.getXpath();
		if (StringUtils.isBlank(xpath)) {
			return ResultUtils.failure("部门信息异常!");
		}
		Example ex = new Example(Repository.class);
		ex.createCriteria().andLike("xpath", xpath+"%");
		//节点
		this.repositoryDao.deleteByExample(ex);
		return ResultUtils.success(1, null, "删除成功!");
	}

	@Override
	public Result updateGoods(Integer id,String name) {
		if (null == id || StringUtils.isBlank(name)) {
			return ResultUtils.paramsEmpty("参数不能为空!");
		}
		try {
			name = EncodeUtils.urlDecode(name);
		} catch (Exception e) {
			return ResultUtils.failure("无效的节点名称!");
		}
		Repository re = this.repositoryDao.selectByPrimaryKey(id);
		if (null == re) {
			return ResultUtils.failure("节点不存在!");
		}
		re.setName(name);
		long updateCount = 0;
		try {
			updateCount = repositoryDao.updateByPrimaryKeySelective(re);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return updateCount > 0 ? ResultUtils.success(updateCount,re,"更新成功!") : ResultUtils.failure("更新失败!");
	}

}
