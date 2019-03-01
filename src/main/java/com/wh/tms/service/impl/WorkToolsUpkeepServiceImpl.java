package com.wh.tms.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wh.tms.dao.tools.IToolsCategoryDAO;
import com.wh.tms.dao.tools.IWorkToolsUpkeepDAO;
import com.wh.tms.dao.user.IMemberDAO;
import com.wh.tms.dao.wt.IWorkToolsDAO;
import com.wh.tms.dao.wt.IWorkToolsDictionaryDAO;
import com.wh.tms.entity.po.Member;
import com.wh.tms.entity.po.ToolsCategory;
import com.wh.tms.entity.po.WorkTools;
import com.wh.tms.entity.po.WorkToolsDictionary;
import com.wh.tms.entity.po.WorkToolsUpkeep;
import com.wh.tms.result.Result;
import com.wh.tms.service.IWorkToolsUpkeepService;
import com.wh.tms.util.DateUtils;
import com.wh.tms.util.ResultUtils;

@Service("workToolsUpkeepService")
public class WorkToolsUpkeepServiceImpl implements IWorkToolsUpkeepService {
	
	@Autowired
	private IMemberDAO memberDao;
	@Autowired
	private IWorkToolsDictionaryDAO  workToolsDictionaryDao;
	@Autowired
	private IWorkToolsDAO workToolDao;
	@Autowired
	private IToolsCategoryDAO toolsCategoryDao;
	@Autowired
	private IWorkToolsUpkeepDAO workToolsUpkeepDao;

	@Override
	public Result addWorkToolsUpkeep(Integer toolsId, String toolsCode,
			Integer managerCategoryId, Integer categoryId, Integer operUid,
			Integer operConId, String planDate) {
		
		if (null == toolsId && StringUtils.isBlank(toolsCode)) {
			return ResultUtils.paramsEmpty("机具唯一标识不能为空!");
		}
		if (null == managerCategoryId) {
			return ResultUtils.paramsEmpty("机具管理类别不能为空!");
		}
		if (null == categoryId) {
			return ResultUtils.paramsEmpty("机具种类不能为空!");
		}
		if (null == operUid) {
			return ResultUtils.paramsEmpty("保养人不能为空!");
		}
		if (null == operConId) {
			return ResultUtils.paramsEmpty("维修内容不能为空!");
		}
		if (StringUtils.isBlank(planDate)) {
			return ResultUtils.paramsEmpty("计划保养日期不能为空!");
		}
		Member mPeople = memberDao.selectByPrimaryKey(operUid);
		if (null == mPeople) {
			return ResultUtils.failure("保养人不存在!");
		}
		//获取机具分类
		ToolsCategory toolsCategory = toolsCategoryDao.selectByPrimaryKey(categoryId);
		if (null == toolsCategory) {
			return ResultUtils.failure("机具分类不存在!");
		}
		//获取机具管理类别
		WorkToolsDictionary workToolsManagerType = workToolsDictionaryDao.selectByPrimaryKey(managerCategoryId);
		if (null == workToolsManagerType || !workToolsManagerType.getType().equals(3)) {
			return ResultUtils.failure("机具管理类别不存在!");
		}
		WorkToolsDictionary operCon = workToolsDictionaryDao.selectByPrimaryKey(operConId);
		if (null == operCon || !operCon.getType().equals(9)) {
			return ResultUtils.failure("机具保养类别不存在!");
		}
		WorkTools tools = null;
		if (null != toolsId) {
			tools = workToolDao.selectByPrimaryKey(toolsId);
			
		}else{
			PageHelper.startPage(1, 1);
			Example ex = new Example(WorkTools.class);
			ex.createCriteria().andEqualTo("toolsCode", toolsCode);
			List<WorkTools> list = this.workToolDao.selectByExample(ex);
			if (null != list && list.size() > 0) {
				tools = list.get(0);
			}
		}
		if (null == tools) {
			return ResultUtils.failure("机具不存在!");
		}
		WorkToolsUpkeep wtu = new WorkToolsUpkeep();
		wtu.setToolsId(tools.getId());
		wtu.setToolsCode(tools.getToolsCode());
		wtu.setManagerCategoryId(managerCategoryId);
		wtu.setManagerCategoryName(workToolsManagerType.getName());
		wtu.setCategoryId(categoryId);
		wtu.setCategoryName(toolsCategory.getCategoryName());
		wtu.setCategoryNumber(toolsCategory.getCategoryNumber());
		wtu.setOperUid(operUid);
		wtu.setOperUname(mPeople.getName());
		wtu.setOperConId(operConId);
		wtu.setOperCon(operCon.getName());
		wtu.setInsertTime(new Date());
		wtu.setPlanDate(DateUtils.getDateTime(planDate, "yyyy-MM-dd"));
		wtu.setStatus(0);//默认未处理
		int saveCount = workToolsUpkeepDao.insertSelective(wtu);
		if (saveCount > 0) {
			return ResultUtils.success(1, wtu, "添加保养单成功!");
		}
		return ResultUtils.failure("添加保养单失败!");
	}

	@Override
	public Result updateWorkToolsUpkeep(Integer id, Integer operUid,
			Integer operConId,Integer status) {
		if (null == id) {
			return ResultUtils.paramsEmpty("保养单id不能为空！");
		}
		WorkToolsUpkeep workToolsUpkeep = workToolsUpkeepDao.selectByPrimaryKey(id);
		if (null == workToolsUpkeep) {
			return ResultUtils.failure("保养单不存在!");
		}
		if (null != operUid) {
			Member mPeople = memberDao.selectByPrimaryKey(operUid);
			if (null == mPeople) {
				return ResultUtils.failure("保养人不存在!");
			}
			workToolsUpkeep.setOperConId(operUid);
			workToolsUpkeep.setOperUname(mPeople.getName());
		}
		if (null != operConId) {
			WorkToolsDictionary operCon = workToolsDictionaryDao.selectByPrimaryKey(operConId);
			if (null == operCon || !operCon.getType().equals(9)) {
				return ResultUtils.failure("机具保养类别不存在!");
			}
			workToolsUpkeep.setOperConId(operConId);
			workToolsUpkeep.setOperCon(operCon.getName());
		}
		if (null != status) {
			if (status.equals(1)) {
				workToolsUpkeep.setOperTime(new Date());//实际保养时间
			}
			workToolsUpkeep.setStatus(status);
		}
		int updateCount = workToolsUpkeepDao.updateByPrimaryKeySelective(workToolsUpkeep);
		if (updateCount > 0) {
			return ResultUtils.success(1, workToolsUpkeepDao.selectByPrimaryKey(id), "更新保养单成功！");
		}
		return ResultUtils.failure("更新保养单失败!");
	}

	@Override
	public Result getWorkToolsUpkeepList(Integer id, Integer managerCategoryId,
			Integer categoryId, Integer status, String startDate,
			String endDate, int page, int pageSize) {
		
		PageHelper.startPage(page, pageSize);
		Example ex = new Example(WorkToolsUpkeep.class);
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
		if (null != status) {
			criteria.andEqualTo("status", status);
		}
		if (StringUtils.isNotBlank(startDate)) {
			criteria.andGreaterThan("planDate", DateUtils.getDateTime(startDate, "yyyy-MM-dd"));
		}
		if (StringUtils.isNotBlank(endDate)) {
			criteria.andLessThanOrEqualTo("planDate", DateUtils.getDateTime(endDate, "yyyy-MM-dd"));
		}
		ex.orderBy("id").desc();
		PageInfo<WorkToolsUpkeep> pageInfo = null;
		List<WorkToolsUpkeep> list = this.workToolsUpkeepDao.selectByExample(ex);
		long totalCount = 0;
		if (null != list) {
			pageInfo = new PageInfo<WorkToolsUpkeep>(list);
			totalCount = pageInfo.getTotal();
		}
		return ResultUtils.success(totalCount, list);
	}

	@Override
	public Result deleteUpkeepOrder(Integer id) {
		if (null == id) {
			return ResultUtils.paramsEmpty("保养单id不能为空！");
		}
		WorkToolsUpkeep workToolsUpkeep = workToolsUpkeepDao.selectByPrimaryKey(id);
		if (null == workToolsUpkeep) {
			return ResultUtils.failure("保养单不存在!");
		}
		workToolsUpkeepDao.delete(workToolsUpkeep);
		return ResultUtils.success(1, null, "保养单删除成功！");
	}

}
