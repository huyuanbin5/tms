package com.wh.tms.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wh.tms.constans.OrderCons;
import com.wh.tms.constans.SystemCons;
import com.wh.tms.dao.order.IImprestListDAO;
import com.wh.tms.dao.order.IImprestOrderDAO;
import com.wh.tms.dao.sys.IUserRoleDAO;
import com.wh.tms.dao.user.ILoginUserDAO;
import com.wh.tms.entity.po.ImprestList;
import com.wh.tms.entity.po.ImprestOrder;
import com.wh.tms.entity.po.LoginUser;
import com.wh.tms.entity.po.UserRole;
import com.wh.tms.result.Result;
import com.wh.tms.service.IImprestListService;
import com.wh.tms.util.DateUtils;
import com.wh.tms.util.EncodeUtils;
import com.wh.tms.util.ResultUtils;

@Service("imprestListService")
public class ImprestListServiceImpl implements IImprestListService {
	
	private static final Log log = LogFactory.getLog(ImprestListServiceImpl.class);
	
	@Autowired
	private ILoginUserDAO userDao;
	@Autowired
	private IImprestOrderDAO imprestOrderDao;
	@Autowired
	private IImprestListDAO imprestListDao;
	@Autowired
	private IUserRoleDAO userRoleDao;
	

	@Override
	public Result addImprestList(Integer uid, Integer orderId, String toolTypeNum,
			String toolTypeName, String startTime, String endTime, Integer borrId,
			Integer leaderId, String explain, Integer part_remark) {
		
		if (null == uid) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "用户id不能为空");
		}
		if (null == orderId) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "预借单id不能为空");
		}
		if (StringUtils.isBlank(toolTypeNum) || StringUtils.isBlank(toolTypeName)) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "机具种类id或名称不能为空!");
		}
		if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "起始时间和结束时间不能为空!");
		}
		if (null == borrId) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "借用人id不能为空");
		}
		if (null == leaderId) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "负责人id不能为空");
		}
		//创建人 
		LoginUser createrUser = userDao.selectByPrimaryKey(uid);
		if (null == createrUser) {
			return ResultUtils.error(SystemCons.FAILURE, "用户不存在!");
		}
		//预借单信息
		ImprestOrder order = imprestOrderDao.selectByPrimaryKey(orderId);
		if (null == order) {
			return ResultUtils.error(SystemCons.FAILURE, "预借单信息不存在!");
		}
		Integer orderStatus = order.getStatus();
		if (orderStatus.intValue() < 0) {
			return ResultUtils.error(SystemCons.FAILURE, "预借单已删除!");
		}
		//验证预借单是否是在未提交状态
		Integer imprestStatus = order.getImprestStatus();
		if (!OrderCons.STATUS_DEFAULT.equals(imprestStatus)) {
			return ResultUtils.error(SystemCons.FAILURE, "预借单当前状态不允许添加!");
		}
		//借用人
		LoginUser borrUser = userDao.selectByPrimaryKey(borrId);
		if (null == borrUser) {
			return ResultUtils.error(SystemCons.FAILURE, "借用人信息不存在!");
		}
		//负责人
		LoginUser leader = userDao.selectByPrimaryKey(leaderId);
		if (null == leader) {
			return ResultUtils.error(SystemCons.FAILURE, "负责人信息不存在!");
		}
		if (StringUtils.isNotBlank(explain)) {
			explain = EncodeUtils.urlDecode(explain);
		}
		toolTypeName = EncodeUtils.urlDecode(toolTypeName);
		int saveCount = 0;
		ImprestList imprestListDetail = null;
		try {
			imprestListDetail = new ImprestList();
			imprestListDetail.setCreaterId(uid);
			imprestListDetail.setCreaterName(createrUser.getUserName());
			imprestListDetail.setOrderId(orderId);
			imprestListDetail.setBorrowerId(borrId);
			imprestListDetail.setBorrowerName(borrUser.getUserName());
			imprestListDetail.setLeaderId(leaderId);
			imprestListDetail.setLeaderName(leader.getUserName());
			imprestListDetail.setStartTime(DateUtils.getDateTime(startTime, "yyyy-MM-dd"));
			imprestListDetail.setEndTime(DateUtils.getDateTime(endTime, "yyyy-MM-dd"));
			imprestListDetail.setExplains(explain);
			imprestListDetail.setToolTypeNum(toolTypeNum);
			imprestListDetail.setToolTypeName(toolTypeName);
			imprestListDetail.setInsertTime(new Date());
			imprestListDetail.setPartRemark(part_remark);
			imprestListDetail.setStatus(OrderCons.STATUS_DEFAULT);
			saveCount = this.imprestListDao.insert(imprestListDetail);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		if (saveCount > 0) {
			return ResultUtils.success(saveCount, imprestListDetail, "预借清单添加成功!");
		}
		return ResultUtils.error(SystemCons.FAILURE, "预借清单添加失败！");
	}

	@Override
	public Result updateImprestList(Integer uid, Integer id, String toolTypeNum,
			String toolTypeName, String startTime, String endTime, Integer borrId,
			Integer leaderId, String explain, Integer part_remark) {
		if (null == uid) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "用户id不能为空");
		}
		if (null == id) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "预借清单id不能为空");
		}
		LoginUser user = userDao.selectByPrimaryKey(uid);
		if (null == user) {
			return ResultUtils.error(SystemCons.FAILURE, "用户不存在!");
		}
		//借用人
		LoginUser borrUser = userDao.selectByPrimaryKey(borrId);
		if (null == borrUser) {
			return ResultUtils.error(SystemCons.FAILURE, "借用人信息不存在!");
		}
		//负责人
		LoginUser leader = userDao.selectByPrimaryKey(leaderId);
		if (null == leader) {
			return ResultUtils.error(SystemCons.FAILURE, "负责人信息不存在!");
		}
		
		Example example = new Example(ImprestList.class);
		example.createCriteria().andEqualTo("id", id).andEqualTo("status", OrderCons.STATUS_DEFAULT);
		//验证当前登录用户是创建人或者是系统管理员
		boolean isManager = isSystemManager(uid);
		if (!isManager) {
			example.createCriteria().andEqualTo("createrId", uid);
		}
		List<ImprestList> list = this.imprestListDao.selectByExample(example);
		if (null == list || list.size() == 0) {
			return ResultUtils.error(SystemCons.FAILURE, "预借清单不存在或已删除!");
		}
		ImprestList imprestListDetail = list.get(0);
		int status = imprestListDetail.getStatus();
		if (status < 0) {
			return ResultUtils.error(SystemCons.FAILURE, "清单已删除!");
		}
		Integer orderId = imprestListDetail.getOrderId();
		ImprestOrder order = this.imprestOrderDao.selectByPrimaryKey(orderId);
		if (null == order) {
			return ResultUtils.error(SystemCons.FAILURE, "预借单不存在!");
		}
		//验证预借单是否是在未提交状态
		Integer imprestStatus = order.getImprestStatus();
		if (!OrderCons.STATUS_DEFAULT.equals(imprestStatus)) {
			return ResultUtils.error(SystemCons.FAILURE, "预借单当前状态不允许编辑!");
		}
		if (StringUtils.isNotBlank(explain)) {
			explain = EncodeUtils.urlDecode(explain);
		}
		toolTypeName = EncodeUtils.urlDecode(toolTypeName);
		int updateCount = 0;
		try {
			imprestListDetail.setBorrowerId(borrId);
			imprestListDetail.setBorrowerName(borrUser.getUserName());
			imprestListDetail.setLeaderId(leaderId);
			imprestListDetail.setLeaderName(leader.getUserName());
			imprestListDetail.setStartTime(DateUtils.getDateTime(startTime, "yyyy-MM-dd"));
			imprestListDetail.setEndTime(DateUtils.getDateTime(endTime, "yyyy-MM-dd"));
			imprestListDetail.setExplains(explain);
			imprestListDetail.setToolTypeNum(toolTypeNum);
			imprestListDetail.setToolTypeName(toolTypeName);
			imprestListDetail.setPartRemark(part_remark);
			imprestListDetail.setUpdateTime(new Date());
			updateCount = this.imprestListDao.updateByPrimaryKeySelective(imprestListDetail);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		if (updateCount > 0) {
			return ResultUtils.success(updateCount, imprestListDetail, "预借清单更新成功!");
		}
		return ResultUtils.error(SystemCons.FAILURE, "预借清单更新失败！");
	}

	@Override
	public Result deleteImprestList(Integer uid, Integer id) {
		if (null == uid) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "用户id不能为空");
		}
		if (null == id) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "预借清单id不能为空");
		}
		LoginUser user = userDao.selectByPrimaryKey(uid);
		if (null == user) {
			return ResultUtils.error(SystemCons.FAILURE, "用户不存在!");
		}
		Example example = new Example(ImprestList.class);
		example.createCriteria().andEqualTo("id", id).andEqualTo("status", OrderCons.STATUS_DEFAULT);
		//验证当前登录用户是创建人或者是系统管理员
		boolean isManager = isSystemManager(uid);
		if (!isManager) {
			example.createCriteria().andEqualTo("createrId", uid);
		}
		List<ImprestList> list = this.imprestListDao.selectByExample(example);
		if (null == list || list.size() == 0) {
			return ResultUtils.error(SystemCons.FAILURE, "预借清单不存在!");
		}
		ImprestList imprestListDetail = list.get(0);
		Integer orderId = imprestListDetail.getOrderId();
		ImprestOrder order = this.imprestOrderDao.selectByPrimaryKey(orderId);
		if (null != order) {
			//验证预借单状态
			Integer imprestStatus = order.getImprestStatus();
			if (!OrderCons.STATUS_DEFAULT.equals(imprestStatus)) {
				return ResultUtils.error(SystemCons.FAILURE, "预借单已提交，不能删除此清单!");
			}
		}
		
		int status = imprestListDetail.getStatus();
		if (status < 0) {
			return ResultUtils.success(1, "删除成功!");
		}
		int delCount = 0;
		try {
			imprestListDetail.setStatus(OrderCons.STATUS_DELETE);//标记为已删除
			delCount = this.imprestListDao.updateByPrimaryKeySelective(imprestListDetail);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		if (delCount > 0) {
			return ResultUtils.success(delCount,"预借清单删除成功!");
		}
		return ResultUtils.error(SystemCons.FAILURE, "预借清单删除失败！");
	}

	@Override
	public Result getImprestListDetail(Integer id) {
		if (null == id) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "清单id不能为空!");
		}
		Example example = new Example(ImprestList.class);
		example.createCriteria().andEqualTo("id", id).andEqualTo("status", OrderCons.STATUS_DEFAULT);
		List<ImprestList> list = this.imprestListDao.selectByExample(example);
		if (null == list || list.size() == 0) {
			return ResultUtils.error(SystemCons.FAILURE, "预借清单不存在!");
		}
		ImprestList imprestListDetail = list.get(0);
		return null == imprestListDetail ? ResultUtils.error(SystemCons.FAILURE, "预借清单信息不存在！") : ResultUtils.success(1, imprestListDetail);
	}

	@Override
	public Result getImprestList(Integer orderId, int pageNum, int pageSize) {
		if (null == orderId) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "预借单id不能为空!");
		}
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<ImprestList> pageInfo = null;
		try {
			Example example = new Example(ImprestList.class);
			example.createCriteria().andEqualTo("orderId", orderId).andEqualTo("status", OrderCons.STATUS_DEFAULT);
			List<ImprestList> list = this.imprestListDao.selectByExample(example);
			if (null != list) {
				pageInfo = new PageInfo<ImprestList>(list);
				return ResultUtils.success(pageInfo.getTotal(), list);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.error(SystemCons.FAILURE, "获取预借清单失败!");
	}
	
	/**
	 * 判断当前用户是否是系统管理员
	 * @param uid
	 * @param example
	 * @return
	 */
	private boolean isSystemManager(Integer uid) {
		if (null == uid) {
			return false;
		}
		Example example = new Example(UserRole.class);
		example.createCriteria().andEqualTo("uid", uid);
		example.setOrderByClause(" id desc ");
		List<UserRole> userRoleList = userRoleDao.selectByExample(example);
		if (null == userRoleList || userRoleList.size() == 0) {
			return false;
		}
		UserRole userRole =  userRoleList.get(0);
		if (null != userRole && SystemCons.SYSTEM_MANAGER.equals(userRole.getSysFlag())) {
			return true;
		}
		return false;
	}

}
