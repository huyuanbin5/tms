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
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wh.tms.constans.OrderCons;
import com.wh.tms.constans.SystemCons;
import com.wh.tms.dao.order.IImprestListDAO;
import com.wh.tms.dao.order.IImprestOrderDAO;
import com.wh.tms.dao.sys.IRoleDAO;
import com.wh.tms.dao.sys.IUserRoleDAO;
import com.wh.tms.dao.user.ILoginUserDAO;
import com.wh.tms.entity.po.ImprestList;
import com.wh.tms.entity.po.ImprestOrder;
import com.wh.tms.entity.po.LoginUser;
import com.wh.tms.entity.po.UserRole;
import com.wh.tms.result.Result;
import com.wh.tms.service.IImprestOrderService;
import com.wh.tms.util.DateUtils;
import com.wh.tms.util.ResultUtils;

@Service("imprestOrderService")
public class ImprestOrderServiceImpl implements IImprestOrderService {
	
	private static final Log log = LogFactory.getLog(ImprestOrderServiceImpl.class);
	
	@Autowired
	private ILoginUserDAO userDao;
	@Autowired
	private IImprestOrderDAO imprestOrderDao;
	@Autowired
	private IUserRoleDAO userRoleDao;
	@Autowired
	private IRoleDAO roleDao;
	@Autowired
	private IImprestListDAO imprestListDao;

	@Override
	public Result addImprestOrder(Integer uid, String resTime,
			String invTime, Integer workType, String outInst) throws Exception{
		
		if (null == uid || uid.intValue() == 0) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "用户id不能为空");
		}
		if (StringUtils.isBlank(resTime) || StringUtils.isBlank(invTime)) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "预约时间或截止时间不能为空!");
		}
		if (null == workType) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "作业类型不能为空");
		}
		LoginUser user = userDao.selectByPrimaryKey(uid);
		if (null == user) {
			return ResultUtils.error(SystemCons.FAILURE, "用户不存在!");
		}
		
		
		ImprestOrder order = new ImprestOrder();
		order.setUid(uid);
		order.setUserName(user.getUserName());
		order.setOutInst(outInst);
		order.setWorkType(workType);
		order.setImprestStatus(OrderCons.OPER_STATUS_NOT_SUBMIT);//默认为未提交状态
		order.setStatus(OrderCons.STATUS_DEFAULT);
		order.setReservationTime(DateUtils.getDateTime(resTime, "yyyy-MM-dd"));
		order.setInvalidTime(DateUtils.getDateTime(invTime, "yyyy-MM-dd"));
		int saveCount = imprestOrderDao.insert(order);
		if (saveCount > 0) {
			return ResultUtils.success(1, order, "添加预借单成功！");
		}
		return  ResultUtils.error(SystemCons.FAILURE, "预借单添加失败！");
	}

	@Override
	public Result getImprestOrderList(Integer uid, Integer orderId,Integer imprestStatus,
			String startTime, String endTime, int pageNum, int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		if (null == uid) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "用户id不能为空!");
		}
		LoginUser user = userDao.selectByPrimaryKey(uid);
		if (null == user) {
			return ResultUtils.error(SystemCons.FAILURE, "用户不存在!");
		}
		//获取用户权限
		boolean isManager = isSystemManager(uid);
		
		Example example = new Example(ImprestOrder.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("status", OrderCons.STATUS_DEFAULT);//未删除的借单
		if (null != orderId) {
			criteria.andEqualTo("id", orderId);
		}
		if (null != uid && !isManager) {
			criteria.andEqualTo("uid", uid);
		}
		if (null != imprestStatus) {
			criteria.andEqualTo("imprestStatus", imprestStatus);
		}
		if (null != startTime) {
			criteria.andGreaterThanOrEqualTo("reservationTime", DateUtils.getDateTime(startTime, "yyyy-MM-dd"));
		}
		if (null != endTime) {
			criteria.andLessThanOrEqualTo("reservationTime", DateUtils.getDateTime(startTime, "yyyy-MM-dd"));
		}
		example.setOrderByClause(" id desc ");
		
		PageInfo<ImprestOrder> pageInfo = null;
		try {
			List<ImprestOrder> list = imprestOrderDao.selectByExample(example);
			if (null != list) {
				pageInfo = new PageInfo<ImprestOrder>(list);
				return ResultUtils.success(pageInfo.getTotal(), list);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.error(SystemCons.FAILURE, "获取预借单失败!");
	}

	@Override
	public Result updateImprestOrder(Integer orderId, Integer uid,
			String resTime, String invTime, Integer workType, String outInst) throws Exception{
		
		if (null == orderId || null == uid) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "参数不能为空!");
		}
		//获取用户权限
		boolean isManager = isSystemManager(uid);
		Example example = new Example(ImprestOrder.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("status", OrderCons.STATUS_DEFAULT);//未删除的借单
		if (null != orderId) {
			criteria.andEqualTo("id", orderId);
		}
		if (null != uid && !isManager) {
			criteria.andEqualTo("uid", uid);
		}
		 
		List<ImprestOrder> list = imprestOrderDao.selectByExample(example);
		if (null == list || list.size() == 0) {
			return ResultUtils.error(SystemCons.FAILURE, "预借单不存在或已删除!");
		}
		ImprestOrder order = list.get(0);
		//检查预借单状态
		Integer orderStatus = order.getImprestStatus();
		if (OrderCons.OPER_STATUS_INVALIDATION.equals(orderStatus)) {
			return ResultUtils.error(SystemCons.FAILURE, "预借单已失效!");
		}else{
			if (orderStatus > OrderCons.OPER_STATUS_NOT_SUBMIT) {
				return ResultUtils.error(SystemCons.FAILURE, "预借单正在处理中，不能更新预借单信息!");
			}
		}
		
		if (StringUtils.isNotBlank(resTime)) {
			order.setReservationTime(DateUtils.getDateTime(resTime, "yyyy-MM-dd"));
		}
		if (StringUtils.isNotBlank(invTime)) {
			order.setInvalidTime(DateUtils.getDateTime(invTime, "yyyy-MM-dd"));
		}
		if (null != workType) {
			order.setWorkType(workType);
		}
		if (StringUtils.isNotBlank(outInst)) {
			order.setOutInst(outInst);
		}
		order.setUpdateTime(new Date());
		order.setUpdateUid(uid);
		int updateCount = imprestOrderDao.updateByPrimaryKeySelective(order);
		if (updateCount > 0) {
			return ResultUtils.success(1, order, "预借单更新成功!");
		}
		return ResultUtils.error(SystemCons.FAILURE, "更新预借单失败!");
	}

	@Override
	@Transactional
	public Result deleteImprestOrder(Integer orderId, Integer uid) throws Exception{
		if (null == orderId || null == uid) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "参数不能为空!");
		}
		//获取用户权限
		boolean isManager = isSystemManager(uid);
		Example example = new Example(ImprestOrder.class);
		Criteria criteria = example.createCriteria();
		if (null != orderId) {
			criteria.andEqualTo("id", orderId);
		}
		if (null != uid && !isManager) {
			criteria.andEqualTo("uid", uid);
		}
		List<ImprestOrder> list = imprestOrderDao.selectByExample(example);
		if (null == list || list.size() == 0) {
			return ResultUtils.error(SystemCons.FAILURE, "预借单不存在!");
		}
		ImprestOrder order = list.get(0);
		if (OrderCons.STATUS_DELETE.equals(order.getStatus())) {
			//如果预借单已处于删除状态，直接返回成功
			return ResultUtils.success(1, order, "预借单删除成功!");
		}
		//验证预借单是否是在未提交状态
		Integer imprestStatus = order.getImprestStatus();
		if (!OrderCons.STATUS_DEFAULT.equals(imprestStatus)) {
			return ResultUtils.error(SystemCons.FAILURE, "预借单当前状态不允许删除!");
		}
		order.setStatus(OrderCons.STATUS_DELETE);//标记为删除
		int updateCount = this.imprestOrderDao.updateByPrimaryKeySelective(order);
		if (updateCount > 0) {
			//删除预借清单
			example = new Example(ImprestList.class);
			example.createCriteria().andEqualTo("orderId", orderId).andEqualTo("status", OrderCons.STATUS_DEFAULT);
			List<ImprestList> imprestList = imprestListDao.selectByExample(example);
			if (null != imprestList && imprestList.size() > 0) {
				ImprestList record = new ImprestList();
				record.setStatus(OrderCons.STATUS_DELETE);//标记为删除
				imprestListDao.updateByExampleSelective(record, example);
			}
			return ResultUtils.success(updateCount,"","预借单删除成功!");
		}
		return ResultUtils.error(SystemCons.FAILURE, "预借单删除失败!");
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
