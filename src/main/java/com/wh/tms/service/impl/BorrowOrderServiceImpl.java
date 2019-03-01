package com.wh.tms.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wh.tms.constans.OrderCons;
import com.wh.tms.constans.SystemCons;
import com.wh.tms.dao.order.IBorrowOrderDAO;
import com.wh.tms.dao.order.IImprestOrderDAO;
import com.wh.tms.entity.po.BorrowOrder;
import com.wh.tms.entity.po.ImprestOrder;
import com.wh.tms.result.Result;
import com.wh.tms.service.IBorrowOrderService;
import com.wh.tms.util.DateUtils;
import com.wh.tms.util.ResultUtils;

@Service("borrowOrderService")
public class BorrowOrderServiceImpl implements IBorrowOrderService {
	

	@Autowired
	private IBorrowOrderDAO borrowOrderDao;
	@Autowired
	private IImprestOrderDAO imprestOrderDao;
	
	@Override
	@Transactional
	public Result addBorrowOrder(Integer imprestId) throws Exception{
		if (null == imprestId) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "参数不能为空!");
		}
		//借还单
		Example example = new Example(BorrowOrder.class);
		example.createCriteria().andEqualTo("imprestOrderId", imprestId);
		List<BorrowOrder> borrowOrders = borrowOrderDao.selectByExample(example);
		if (null != borrowOrders && borrowOrders.size() > 0) {
			return ResultUtils.error(SystemCons.FAILURE, "借还单已存在，请不要重复添加!");
		}
		//预借单
		example = new Example(ImprestOrder.class);
		example.createCriteria().andEqualTo("id", imprestId).andEqualTo("status", OrderCons.STATUS_DEFAULT);
		List<ImprestOrder> orderList = imprestOrderDao.selectByExample(example);
		if (null == orderList || orderList.size() == 0) {
			return ResultUtils.error(SystemCons.FAILURE, "预借单信息不存在或者已删除!");
		}
		//预借单
		ImprestOrder imprestOrder = orderList.get(0);
		BorrowOrder borrowOrder = new BorrowOrder();
		PropertyUtils.copyProperties(borrowOrder,imprestOrder);
		borrowOrder.setId(null);
		borrowOrder.setImprestOrderId(imprestId);
		borrowOrder.setStatus(OrderCons.STATUS_DEFAULT);
		borrowOrder.setBorrowerOrderStatus(OrderCons.BORROW_ORDER_STATUS_UNFINISHED);//默认为未完成
		int insertCount = borrowOrderDao.insert(borrowOrder);
		if (insertCount > 0) {
			//更新预借单状态为未处理
			imprestOrder.setImprestStatus(OrderCons.OPER_STATUS_NOT_PROCESS);
			int updateIrCount = imprestOrderDao.updateByPrimaryKeySelective(imprestOrder);
			if (updateIrCount > 0) {
				return ResultUtils.success(insertCount, borrowOrder);
			}
		}
		return 	ResultUtils.error(SystemCons.FAILURE, "借还单创建失败！");
	}

	@Override
	public Result getBorrowOrderList(Integer uid,
			Integer borrowOrderStatus,String startTime,String endTime,int pageNum, int pageSize) {
		if (null == uid) {
			return ResultUtils.paramsEmpty("用户id不能为空!");
		}
		
		PageInfo<BorrowOrder> pageInfo = null;
		long totalCount = 0;
		PageHelper.startPage(pageNum, pageSize);
		Example ex = new Example(BorrowOrder.class);
		Example.Criteria criteria = ex.createCriteria();
		if (null != borrowOrderStatus) {
			criteria.andEqualTo("borrowOrderStatus", borrowOrderStatus);
		}
		if (StringUtils.isNotBlank(startTime)) {
			criteria.andGreaterThanOrEqualTo("deliveryDate", DateUtils.getDateTime(startTime,"yyyy-MM-dd"));
		}
		if (StringUtils.isNotBlank(endTime)) {
			criteria.andLessThanOrEqualTo("deliveryDate", DateUtils.getDateTime(endTime,"yyyy-MM-dd"));
		}
		List<BorrowOrder> list = borrowOrderDao.selectByExample(ex);
		if (null != list) {
			Set<Integer> impOrderIds = new HashSet<Integer>();
			for (BorrowOrder borrowOrder : list) {
				Integer impOrderId = borrowOrder.getImprestOrderId();
				if (null == impOrderId) {
					continue;
				}
				impOrderIds.add(impOrderId);
			}
			Map<Integer,ImprestOrder> impOrderMap = null;
			if (impOrderIds.size() > 0) {
				ex = new Example(ImprestOrder.class);
				ex.createCriteria().andIn("id", impOrderIds);
				List<ImprestOrder> impOrders = imprestOrderDao.selectByExample(ex);
				if (null != impOrders && impOrders.size() > 0) {
					impOrderMap = new HashMap<Integer,ImprestOrder>();
					for (ImprestOrder imprestOrder : impOrders) {
						impOrderMap.put(imprestOrder.getId(), imprestOrder);
					}
				}
			}
			if (null != impOrderMap && !impOrderMap.isEmpty()) {
				for (BorrowOrder borrowOrder : list) {
					Integer impOrderId = borrowOrder.getImprestOrderId();
					if (null == impOrderId) {
						continue;
					}
					ImprestOrder impOrder = impOrderMap.get(impOrderId);
					if (null != impOrder) {
						borrowOrder.setLeaderId(impOrder.getUid());
						borrowOrder.setLeader(impOrder.getUserName());
					}
				}
			}
			pageInfo = new PageInfo<BorrowOrder>(list);
			totalCount = pageInfo.getTotal();
		}
		return ResultUtils.success(totalCount, list, "获取借还单数据成功!");
	}

	@Override
	public Result getBorrowOrderDetail(Integer id) {
		if (null == id) {
			return ResultUtils.paramsEmpty("参数不能为空!");
		}
		Example example = new Example(BorrowOrder.class);
		example.createCriteria().andEqualTo("id", id).andEqualTo("status", OrderCons.STATUS_DEFAULT);
		List<BorrowOrder> borrowOrders = borrowOrderDao.selectByExample(example);
		if (null == borrowOrders || borrowOrders.size() == 0) {
			return ResultUtils.error(SystemCons.FAILURE, "借还单不存在!");
		}
		BorrowOrder order = borrowOrders.get(0);
		Integer impOrderId = order.getImprestOrderId();
		if (null != impOrderId) {
			ImprestOrder impOrder = imprestOrderDao.selectByPrimaryKey(impOrderId);
			if (null != impOrder) {
				order.setLeaderId(impOrder.getUid());
				order.setLeader(impOrder.getUserName());
			}
		}
		return ResultUtils.success(order);
	}

}
