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
import com.wh.tms.dao.sys.IDeptDAO;
import com.wh.tms.dao.sys.IRepositoryDAO;
import com.wh.tms.dao.tools.IToolsCategoryDAO;
import com.wh.tms.dao.user.ILoginUserDAO;
import com.wh.tms.dao.wt.IWorkToolsDAO;
import com.wh.tms.dao.wt.IWorkToolsDictionaryDAO;
import com.wh.tms.entity.po.Dept;
import com.wh.tms.entity.po.LoginUser;
import com.wh.tms.entity.po.Repository;
import com.wh.tms.entity.po.ToolsCategory;
import com.wh.tms.entity.po.WorkTools;
import com.wh.tms.entity.po.WorkToolsDictionary;
import com.wh.tms.result.Result;
import com.wh.tms.service.IWorkToolsService;
import com.wh.tms.util.DateUtils;
import com.wh.tms.util.EncodeUtils;
import com.wh.tms.util.ResultUtils;

@Service("workToolService")
public class WorkToolsServiceImpl implements IWorkToolsService{
	
	private static final Log log = LogFactory.getLog(WorkToolsServiceImpl.class);
	
	@Autowired
	private IWorkToolsDAO workToolDao;
	@Autowired
	private IDeptDAO deptDao;
	@Autowired
	private IToolsCategoryDAO toolsCategoryDao;
	@Autowired
	private ILoginUserDAO userDao;
	@Autowired
	private IWorkToolsDictionaryDAO  workToolsDictionaryDao;
	@Autowired
	private IRepositoryDAO repositoryDao;

	@Override
	@Transactional
	public Result addWorkTool(Integer uid,Integer categoryId,Integer deptId,String insertTime,Integer addCount) {
		if (null == uid) {
			return ResultUtils.paramsEmpty("用户id不能为空!");
		}
		if (null == categoryId) {
			return ResultUtils.paramsEmpty("机具种类id不能为空!");
		}
		if (null == deptId) {
			return ResultUtils.paramsEmpty("部门id不能为空!");
		}
		if (addCount == null || addCount.intValue() == 0) {
			return ResultUtils.paramsEmpty("无效的机具数量!");
		}
		Date insert_time = null;
		try {
			insert_time = DateUtils.getDateTime(insertTime, "yyyy-MM-dd HH:mm:ss");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (null == insert_time) {
			return ResultUtils.paramsEmpty("日期格式错误!");
		}
		LoginUser user = userDao.selectByPrimaryKey(uid);
		if (null == user) {
			return ResultUtils.paramsEmpty("用户不存在!");
		}
		//获取机具分类
		ToolsCategory toolsCategory = toolsCategoryDao.selectByPrimaryKey(categoryId);
		if (null == toolsCategory) {
			return ResultUtils.failure("机具分类不存在!");
		}
		//是否是个人工机具类型
		Integer managerCategoryId = toolsCategory.getManagerCategoryId();
		if (null == managerCategoryId || managerCategoryId.intValue() != 48) {
			return ResultUtils.failure("机具类型错误，只能添加个人工机具!");
		}
		//获取部门id
		Dept dept = deptDao.selectByPrimaryKey(deptId);
		if (null == dept) {
			return ResultUtils.failure("部门不存在!");
		}
		
		WorkTools wt = new WorkTools();
		wt.setUid(uid);
		wt.setUserName(user.getUserName());
		wt.setCategoryId(categoryId);
		wt.setCategoryNumber(toolsCategory.getCategoryNumber());
		wt.setCategoryName(toolsCategory.getCategoryName());
		wt.setDeptId(deptId);
		wt.setDeptName(dept.getDeptName());
		wt.setInsertTime(insert_time);
		wt.setInStorageTime(DateUtils.getDate(insertTime, "yyyy-MM-dd"));
		wt.setAddCount(addCount);
		int saveCount = workToolDao.insertSelective(wt);
		if (saveCount > 0) {
			//更新机具库存数量
			Integer invCount = toolsCategory.getInventoryCount();
			if (null == invCount || invCount.intValue() < 0) {
				invCount = 0;
			}
			toolsCategory.setInventoryCount(invCount + addCount);
			this.toolsCategoryDao.updateByPrimaryKeySelective(toolsCategory);
		}
		return ResultUtils.success(saveCount, wt);
	}

	@Override
	public Result getWorkToolsList(Integer managerCategoryId,String categoryName,
			Integer deptId, String deptName, String insertTime, int page,
			int limit) {
		
		PageHelper.startPage(page, limit);
		Example ex = new Example(WorkTools.class);
		Example.Criteria criteria = ex.createCriteria();
		criteria.andEqualTo("managerCategoryId", managerCategoryId);
		if (StringUtils.isNotBlank(categoryName)) {
			try {
				categoryName = EncodeUtils.urlDecode(categoryName);
			} catch (Exception e) {
				return ResultUtils.paramsEmpty("机具种类名称编码错误!");
			}
			criteria.andLike("categoryName", categoryName);
		}
		if (null != deptId) {
			criteria.andEqualTo("deptId", deptId);
		}
		if (StringUtils.isNotBlank(deptName)) {
			try {
				deptName = EncodeUtils.urlDecode(deptName);
			} catch (Exception e) {
				return ResultUtils.paramsEmpty("部门名称编码错误!");
			}
			
			criteria.andLike("deptName", deptName);
		}
		if (StringUtils.isNotBlank(insertTime)) {
			criteria.andEqualTo("inStorageTime", insertTime);
		}
		ex.orderBy("categoryNumber").asc();
		PageInfo<WorkTools> pageInfo = null;
		List<WorkTools> list = this.workToolDao.selectByExample(ex);
		long totalCount = 0;
		if (null != list) {
			pageInfo = new PageInfo<WorkTools>(list);
			totalCount = pageInfo.getTotal();
		}
		return ResultUtils.success(totalCount, list);
	}

	@Override
	public Result addCommonWorkTools(Integer uid, Integer managerCategoryId,
			Integer categoryId, Integer deptId, Integer repositoryId,
			Integer manufacturerId, Integer supplierId, String factoryNumber,
			String brand, String toolsCode, String yearLimit,
			Integer evaluation, String insertTime, String remark) {
		
		if (null == uid) {
			return ResultUtils.paramsEmpty("用户id不能为空!");
		}
		if (null == deptId) {
			return ResultUtils.paramsEmpty("所属部门不能为空!");
		}
		if (managerCategoryId == null) {
			return ResultUtils.paramsEmpty("机具管理类别不能为空!");
		}
		
		if (null == categoryId) {
			return ResultUtils.paramsEmpty("机具种类不能为空!");
		}
		if (StringUtils.isBlank(toolsCode)) {
			return ResultUtils.paramsEmpty("机具编码不能为空!");
		}
		//获取用户
		LoginUser user = userDao.selectByPrimaryKey(uid);
		if (null == user) {
			return ResultUtils.failure("用户不存在!");
		}
		//获取部门
		Dept dept = this.deptDao.selectByPrimaryKey(deptId);
		if (null == dept) {
			return ResultUtils.failure("部门不存在!");
		}
		//获取机具管理类别
		WorkToolsDictionary workToolsManagerType = workToolsDictionaryDao.selectByPrimaryKey(managerCategoryId);
		if (null == workToolsManagerType) {
			return ResultUtils.failure("机具管理类别不存在!");
		}
		Integer mType = workToolsManagerType.getType();
		if (null == mType || !mType.equals(3)) {
			return ResultUtils.failure("无效的机具管理类别!");
		}
		//获取机具分类
		ToolsCategory toolsCategory = toolsCategoryDao.selectByPrimaryKey(categoryId);
		if (null == toolsCategory) {
			return ResultUtils.failure("机具分类不存在!");
		}
		Date insert_time = null;
		try {
			insert_time = DateUtils.getDateTime(insertTime, "yyyy-MM-dd HH:mm:ss");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.failure("入库时间格式错误!");
		}
		if (StringUtils.isNotBlank(remark)) {
			try {
				remark = EncodeUtils.urlDecode(remark);
			} catch (Exception e) {
				return ResultUtils.failure("备注编码错误!");
			}
		}
		WorkTools wt = new WorkTools();
		wt.setUid(uid);
		wt.setUserName(user.getUserName());
		wt.setDeptId(deptId);
		wt.setDeptName(dept.getDeptName());
		wt.setCategoryId(categoryId);
		wt.setCategoryName(toolsCategory.getCategoryName());
		wt.setCategoryNumber(toolsCategory.getCategoryNumber());
		wt.setManagerCategoryId(managerCategoryId);
		wt.setManagerCategoryName(workToolsManagerType.getName());
		wt.setInsertTime(insert_time);
		wt.setInStorageTime(DateUtils.getDate(insertTime, "yyyy-MM-dd"));
		wt.setYearLimit(yearLimit);
		wt.setToolsCode(toolsCode);
		wt.setRemark(remark);
		wt.setEvaluation(evaluation);
		wt.setBrand(brand);
		wt.setFactoryNumber(factoryNumber);
		wt.setAddCount(1);
		if (null != manufacturerId) {
			//获取制造商信息
			WorkToolsDictionary manufacturer = workToolsDictionaryDao.selectByPrimaryKey(manufacturerId);
			if (null == manufacturer) {
				return ResultUtils.failure("制造商不存在!");
			}
			if (null == manufacturer.getType() || !manufacturer.getType().equals(1)) {
				return ResultUtils.failure("制造商信息无效!");
			}
			wt.setManufacturerId(manufacturerId);
			wt.setManufacturerName(manufacturer.getName());
		}
		if (null != supplierId) {
			//供应商
			WorkToolsDictionary supplier = workToolsDictionaryDao.selectByPrimaryKey(supplierId);
			if (null == supplier) {
				return ResultUtils.failure("供应商商不存在!");
			}
			if (null == supplier.getType() || !supplier.getType().equals(2)) {
				return ResultUtils.failure("供应商信息无效!");
			}
			wt.setSupplierId(supplierId);
			wt.setSupplierName(supplier.getName());
		}
		
		//仓库货位信息
		if (null != repositoryId) {
			//获取仓库信息
			Repository rep = repositoryDao.selectByPrimaryKey(repositoryId);
			Integer pid = rep.getPid() == null ? 0 : rep.getPid();
			wt.setRepositoryId(repositoryId);
			//父节点
			if (pid.intValue() == 0) {
				wt.setRepositoryName(rep.getName());
			}else{
				String xpath = rep.getXpath();
				if (StringUtils.isNotBlank(xpath)) {
					String[] ids = xpath.split("/");
					if (null != ids && ids.length > 0) {
						Repository r = null;
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < ids.length; i++) {
							Integer id = Integer.parseInt(ids[i]);
							r = repositoryDao.selectByPrimaryKey(id);
							if (null != r) {
								sb.append(r.getName());
								if (i < (ids.length - 1)) {
									sb.append("/");
								}
							}
						}
						wt.setRepositoryName(sb.toString());
					}
				}
						
				
			}
		}
		int insertCount = this.workToolDao.insertSelective(wt);
		if (insertCount > 0) {
			return ResultUtils.success(1, wt);
		}
		return ResultUtils.failure("添加工机具失败!");
	}

	@Override
	public Result updateCommonWorkTools(Integer id, Integer managerCategoryId,
			Integer deptId, Integer repositoryId, Integer manufacturerId,
			Integer supplierId, String factoryNumber, String brand,
			String toolsCode, String yearLimit, Integer evaluation,
			String remark, Integer status) {
		
		if (null == id) {
			return ResultUtils.paramsEmpty("机具id不能为空!");
		}
		if (null == deptId) {
			return ResultUtils.paramsEmpty("所属部门不能为空!");
		}
		if (managerCategoryId == null) {
			return ResultUtils.paramsEmpty("机具管理类别不能为空!");
		}
		if (StringUtils.isBlank(toolsCode)) {
			return ResultUtils.paramsEmpty("机具编码不能为空!");
		}
		//获取部门
		Dept dept = this.deptDao.selectByPrimaryKey(deptId);
		if (null == dept) {
			return ResultUtils.failure("部门不存在!");
		}
		//获取机具管理类别
		WorkToolsDictionary workToolsManagerType = workToolsDictionaryDao.selectByPrimaryKey(managerCategoryId);
		if (null == workToolsManagerType) {
			return ResultUtils.failure("机具管理类别不存在!");
		}
		Integer mType = workToolsManagerType.getType();
		if (null == mType || !mType.equals(3)) {
			return ResultUtils.failure("无效的机具管理类别!");
		}
		if (StringUtils.isNotBlank(remark)) {
			try {
				remark = EncodeUtils.urlDecode(remark);
			} catch (Exception e) {
				return ResultUtils.failure("备注编码错误!");
			}
		}
		WorkTools wt = this.workToolDao.selectByPrimaryKey(id);
		if (null == wt) {
			return ResultUtils.failure("机具信息不存在!");
		}
		wt.setDeptId(deptId);
		wt.setDeptName(dept.getDeptName());
		wt.setManagerCategoryId(managerCategoryId);
		wt.setManagerCategoryName(workToolsManagerType.getName());
		wt.setYearLimit(yearLimit);
		wt.setToolsCode(toolsCode);
		wt.setRemark(remark);
		wt.setEvaluation(evaluation);
		wt.setBrand(brand);
		wt.setFactoryNumber(factoryNumber);
		wt.setStatus(status);
		if (null != manufacturerId) {
			//获取制造商信息
			WorkToolsDictionary manufacturer = workToolsDictionaryDao.selectByPrimaryKey(manufacturerId);
			if (null == manufacturer) {
				return ResultUtils.failure("制造商不存在!");
			}
			if (null == manufacturer.getType() || !manufacturer.getType().equals(1)) {
				return ResultUtils.failure("制造商信息无效!");
			}
			wt.setManufacturerId(manufacturerId);
			wt.setManufacturerName(manufacturer.getName());
		}
		if (null != supplierId) {
			//供应商
			WorkToolsDictionary supplier = workToolsDictionaryDao.selectByPrimaryKey(supplierId);
			if (null == supplier) {
				return ResultUtils.failure("供应商商不存在!");
			}
			if (null == supplier.getType() || !supplier.getType().equals(2)) {
				return ResultUtils.failure("供应商信息无效!");
			}
			wt.setSupplierId(supplierId);
			wt.setSupplierName(supplier.getName());
		}
		
		//仓库货位信息
		if (null != repositoryId) {
			//获取仓库信息
			Repository rep = repositoryDao.selectByPrimaryKey(repositoryId);
			Integer pid = rep.getPid() == null ? 0 : rep.getPid();
			wt.setRepositoryId(repositoryId);
			//父节点
			if (pid.intValue() == 0) {
				wt.setRepositoryName(rep.getName());
			}else{
				String xpath = rep.getXpath();
				if (StringUtils.isNotBlank(xpath)) {
					String[] ids = xpath.split("/");
					if (null != ids && ids.length > 0) {
						Repository r = null;
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < ids.length; i++) {
							Integer rid = Integer.parseInt(ids[i]);
							r = repositoryDao.selectByPrimaryKey(rid);
							if (null != r) {
								sb.append(r.getName());
								if (i < (ids.length - 1)) {
									sb.append("/");
								}
							}
						}
						wt.setRepositoryName(sb.toString());
					}
				}
			}
		}
		int updateCount = this.workToolDao.updateByPrimaryKeySelective(wt);
		if (updateCount > 0) {
			return ResultUtils.success(1, workToolDao.selectByPrimaryKey(id));
		}
		return ResultUtils.failure("更新机具信息失败!");
	}

	@Override
	public Result deleteWorkTools(Integer id) {
		if (null == id) {
			return ResultUtils.paramsEmpty("机具id不能为空!");
		}
		WorkTools wt = this.workToolDao.selectByPrimaryKey(id);
		if (null != wt) {
			workToolDao.delete(wt);
		}
		return ResultUtils.success(1, null, "删除成功!");
	}

	@Override
	public Result getCommonWorkToolsList(Integer id,Integer managerCategoryId,
			Integer categoryId, String toolsCode, Integer supplierId,
			Integer manufacturerId, Integer status,Integer page,Integer limit) {
		
		PageHelper.startPage(page, limit);
		Example ex = new Example(WorkTools.class);
		Example.Criteria criteria = ex.createCriteria();
		if (null != id) {
			criteria.andEqualTo("id", id);
		}
		if (null != managerCategoryId) {
			criteria.andEqualTo("managerCategoryId", managerCategoryId);
		}
		if (null != categoryId) {
			criteria.andEqualTo("categoryId", categoryId);
		}
		if (StringUtils.isNotBlank(toolsCode)) {
			criteria.andEqualTo("toolsCode", toolsCode);
		}
		if (null != manufacturerId) {
			criteria.andEqualTo("manufacturerId", manufacturerId);
		}
		if (null != supplierId) {
			criteria.andEqualTo("supplierId", supplierId);
		}
		if (null != status) {
			criteria.andEqualTo("status", status);
		}
		ex.orderBy("categoryNumber").asc();
		PageInfo<WorkTools> pageInfo = null;
		List<WorkTools> list = this.workToolDao.selectByExample(ex);
		long totalCount = 0;
		if (null != list) {
			pageInfo = new PageInfo<WorkTools>(list);
			totalCount = pageInfo.getTotal();
		}
		return ResultUtils.success(totalCount, list);
	}

}
