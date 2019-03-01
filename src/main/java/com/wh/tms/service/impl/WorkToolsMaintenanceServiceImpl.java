package com.wh.tms.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wh.tms.dao.tools.IToolsCategoryDAO;
import com.wh.tms.dao.tools.IWorkToolsMaintenanceDAO;
import com.wh.tms.dao.user.ILoginUserDAO;
import com.wh.tms.dao.user.IMemberDAO;
import com.wh.tms.dao.wt.IWorkToolsDAO;
import com.wh.tms.dao.wt.IWorkToolsDictionaryDAO;
import com.wh.tms.entity.po.Member;
import com.wh.tms.entity.po.ToolsCategory;
import com.wh.tms.entity.po.WorkTools;
import com.wh.tms.entity.po.WorkToolsDictionary;
import com.wh.tms.entity.po.WorkToolsMaintenance;
import com.wh.tms.result.Result;
import com.wh.tms.service.IWorkToolsMaintenanceService;
import com.wh.tms.util.DateUtils;
import com.wh.tms.util.ResultUtils;

@Service("workToolsMaintenanceService")
public class WorkToolsMaintenanceServiceImpl implements IWorkToolsMaintenanceService {
	
	@Autowired
	private IWorkToolsDAO workToolDao;
	@Autowired
	private IToolsCategoryDAO toolsCategoryDao;
	@Autowired
	private ILoginUserDAO userDao;
	@Autowired
	private IMemberDAO memberDao;
	@Autowired
	private IWorkToolsDictionaryDAO  workToolsDictionaryDao;
	@Autowired
	private IWorkToolsMaintenanceDAO workToolsMaintenanceDao;

	@Override
	public Result addWorkToolsMaintenance(Integer toolsId,String toolsCode,
			Integer managerCategoryId, Integer categoryId,
			Integer borrowerUid, Integer mUid, Integer sCategoryId,
			Integer sConId) {
		
		if (null == toolsId && StringUtils.isBlank(toolsCode)) {
			return ResultUtils.paramsEmpty("机具唯一标识不能为空!");
		}
		if (null == managerCategoryId) {
			return ResultUtils.paramsEmpty("机具管理类别不能为空!");
		}
		if (null == categoryId) {
			return ResultUtils.paramsEmpty("机具种类不能为空!");
		}
		if (null == borrowerUid) {
			return ResultUtils.paramsEmpty("借用人不能为空!");
		}
		if (null == mUid) {
			return ResultUtils.paramsEmpty("维修人不能为空!");
		}
		if (null == sCategoryId) {
			return ResultUtils.paramsEmpty("维修类别不能为空!");
		}
		if (null == sConId) {
			return ResultUtils.paramsEmpty("维修内容不能为空!");
		}
		//借用人信息
		Member member = memberDao.selectByPrimaryKey(borrowerUid);
		if (null == member) {
			return ResultUtils.failure("借用人不存在!");
		}
		Member mPeople = memberDao.selectByPrimaryKey(mUid);
		if (null == mPeople) {
			return ResultUtils.failure("维修人不存在!");
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
		//维修类别
		WorkToolsDictionary mCategory = workToolsDictionaryDao.selectByPrimaryKey(sCategoryId);
		if (null == mCategory || !mCategory.getType().equals(4)) {
			return ResultUtils.failure("维修类别不能为空!");
		}
		//维修内容
		WorkToolsDictionary sCon = workToolsDictionaryDao.selectByPrimaryKey(sConId);
		if (null == sCon || !sCon.getType().equals(5)) {
			return ResultUtils.failure("维修内容不能为空!");
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
		WorkToolsMaintenance wtm = new WorkToolsMaintenance();
		wtm.setToolsId(tools.getId());
		wtm.setToolsCode(tools.getToolsCode());
		wtm.setManagerCategoryId(managerCategoryId);
		wtm.setManagerCategoryName(workToolsManagerType.getName());
		wtm.setCategoryId(categoryId);
		wtm.setCategoryName(toolsCategory.getCategoryName());
		wtm.setCategoryNumber(toolsCategory.getCategoryNumber());
		wtm.setBorrowerUid(borrowerUid);
		wtm.setBorrowerName(member.getName());
		wtm.setmUid(mUid);
		wtm.setmUname(mPeople.getName());
		wtm.setsCategoryId(sCategoryId);
		wtm.setsCategoryName(mCategory.getName());
		wtm.setsConId(sConId);
		wtm.setsCon(sCon.getName());
		wtm.setInsertTime(new Date());
		wtm.setPlanDate(new Date());
		int saveCount = workToolsMaintenanceDao.insertSelective(wtm);
		if (saveCount > 0) {
			return ResultUtils.success(1, wtm, "添加维修单成功!");
		}
		return ResultUtils.failure("添加维修单失败!");
	}

	@Override
	@Transactional
	public Result updateWorkToolsMaintenanc(Integer id, Integer mUid,
			Integer checkUid, Integer mPolicyId, Integer aConId,
			Integer aAssessId, Integer status) {
		
		if (null == id) {
			return ResultUtils.paramsEmpty("维修单id不能为空!");
		}
		
		WorkToolsMaintenance wtm = workToolsMaintenanceDao.selectByPrimaryKey(id);
		if (null == wtm) {
			return ResultUtils.failure("维修单不存在!");
		}
		if (null != mUid) {
			Member mPeople = memberDao.selectByPrimaryKey(mUid);
			if (null == mPeople) {
				return ResultUtils.failure("维修人不存在!");
			}
			wtm.setmUid(mUid);
			wtm.setmUname(mPeople.getName());
		}
		if (null != checkUid) {
			Member checkUser = memberDao.selectByPrimaryKey(checkUid);
			if (null == checkUser) {
				return ResultUtils.failure("验收人不存在!");
			}
			wtm.setCheckUid(checkUid);
			wtm.setCheckUsername(checkUser.getName());
		}
		if (null != mPolicyId) {
			WorkToolsDictionary mPolicy = workToolsDictionaryDao.selectByPrimaryKey(mPolicyId);
			if (null == mPolicy || !mPolicy.getType().equals(6)) {
				return ResultUtils.failure("未知维修策略!");
			}
			wtm.setmPolicyId(mPolicyId);
			wtm.setmPolicyName(mPolicy.getName());
			wtm.setmTime(new Date());//实际维修时间
		}
		if (null != aConId) {
			WorkToolsDictionary acceptance = workToolsDictionaryDao.selectByPrimaryKey(aConId);
			if (null == acceptance || !acceptance.getType().equals(7)) {
				return ResultUtils.failure("未知验收内容!");
			}
			wtm.setaConId(aConId);
			wtm.setaCon(acceptance.getName());
		}
		if (null != aAssessId) {
			WorkToolsDictionary assess = workToolsDictionaryDao.selectByPrimaryKey(aAssessId);
			if (null == assess || !assess.getType().equals(8)) {
				return ResultUtils.failure("未知验收评价!");
			}
			wtm.setaAssessId(aAssessId);
			wtm.setaAssess(assess.getName());
			wtm.setaTime(new Date());//验收评价时间
		}
		if (null != status) {
			wtm.setStatus(status);
		}
		int updateCount = workToolsMaintenanceDao.updateByPrimaryKeySelective(wtm);
		if (updateCount > 0) {
			//如果是维修完毕并且重新入库更新机具状态为正常
			if (null != status && status.intValue() == 2) {
				Integer toolsId = wtm.getToolsId();
				if (null != toolsId) {
					WorkTools tools = this.workToolDao.selectByPrimaryKey(toolsId);
					if (null != tools) {
						tools.setStatus(0);
						workToolDao.updateByPrimaryKeySelective(tools);
					}
				}
			}
			return ResultUtils.success(updateCount, workToolsMaintenanceDao.selectByPrimaryKey(id), "更新成功!");
		}
		return ResultUtils.failure("更新失败!");
	}

	@Override
	public Result getMaintenancList(Integer id, Integer managerCategoryId,
			Integer categoryId, Integer status, String startDate, String endDate,int page,int pageSize) {
		
		PageHelper.startPage(page, pageSize);
		Example ex = new Example(WorkToolsMaintenance.class);
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
			criteria.andGreaterThan("insertTime", DateUtils.getDateTime(startDate, "yyyy-MM-dd HH:mm:ss"));
		}
		if (StringUtils.isNotBlank(endDate)) {
			criteria.andLessThanOrEqualTo("insertTime", DateUtils.getDateTime(endDate, "yyyy-MM-dd HH:mm:ss"));
		}
		ex.orderBy("id").desc();
		PageInfo<WorkToolsMaintenance> pageInfo = null;
		List<WorkToolsMaintenance> list = this.workToolsMaintenanceDao.selectByExample(ex);
		long totalCount = 0;
		if (null != list) {
			pageInfo = new PageInfo<WorkToolsMaintenance>(list);
			totalCount = pageInfo.getTotal();
		}
		return ResultUtils.success(totalCount, list);
	}

	@Override
	public Result deleteMaintenanc(Integer id) {
		if (null == id) {
			return ResultUtils.paramsEmpty("维修单id不能为空!");
		}
		WorkToolsMaintenance wtm = workToolsMaintenanceDao.selectByPrimaryKey(id);
		if (null != wtm) {
			workToolsMaintenanceDao.delete(wtm);
		}
		return ResultUtils.success(1, null, "删除维修单成功！");
	}

}
