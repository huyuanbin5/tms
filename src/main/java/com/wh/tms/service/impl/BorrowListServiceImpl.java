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
import com.wh.tms.dao.order.IBorrowListDAO;
import com.wh.tms.dao.order.IBorrowOrderDAO;
import com.wh.tms.dao.order.IImprestListDAO;
import com.wh.tms.dao.order.IImprestOrderDAO;
import com.wh.tms.dao.user.ILoginUserDAO;
import com.wh.tms.entity.po.BorrowList;
import com.wh.tms.entity.po.BorrowOrder;
import com.wh.tms.entity.po.ImprestList;
import com.wh.tms.entity.po.ImprestOrder;
import com.wh.tms.entity.po.LoginUser;
import com.wh.tms.result.Result;
import com.wh.tms.service.IBorrowListService;
import com.wh.tms.util.DateUtils;
import com.wh.tms.util.EncodeUtils;
import com.wh.tms.util.ResultUtils;

@Service("borrowListService")
public class BorrowListServiceImpl implements IBorrowListService {
	
	private static final Log log = LogFactory.getLog(BorrowListServiceImpl.class);
	
	@Autowired
	private IBorrowListDAO borrowListDao;
	@Autowired
	private ILoginUserDAO loginUserDao;
	@Autowired
	private IImprestOrderDAO imprestOrderDao;
	@Autowired
	private IImprestListDAO imprestListDao;
	@Autowired
	private IBorrowOrderDAO borrowOrderDao;

	@Override
	public Result addBorrowList(Integer uid, Integer imprestOrderId,
			Integer imprestListId, Integer borrowOrderId, String toolCode,
			String goodsAllo, String borrowerTime, String endTime) {
		if (null == uid) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "用户id不能为空!");
		}
		if (null == imprestOrderId || imprestOrderId.intValue() == 0) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "预借单id不能为空!");
		}
		if (null == imprestListId || imprestListId.intValue() == 0) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "预借清单id不能为空!");
		}
		if (null == borrowOrderId || borrowOrderId.intValue() == 0) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "借还单id不能为空!");
		}
		if (StringUtils.isBlank(toolCode)) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "机具编码不能为空!");
		}
		if (StringUtils.isBlank(goodsAllo)) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "机具具体货位不能为空!");	
		}
		if (StringUtils.isBlank(borrowerTime)) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "借出日期不能为空!");
		}
		if (StringUtils.isBlank(endTime)) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "截止日期不能为空!");
		}
		LoginUser user = loginUserDao.selectByPrimaryKey(uid);
		if (null == user) {
			return ResultUtils.error(SystemCons.FAILURE,"用户不存在！");
		}
		goodsAllo = EncodeUtils.urlDecode(goodsAllo);
		//获取预订单信息
		Example example = new Example(ImprestOrder.class);
		example.createCriteria().andEqualTo("id", imprestOrderId).andEqualTo("status", OrderCons.STATUS_DEFAULT);
		List<ImprestOrder> imprestOrders = imprestOrderDao.selectByExample(example);
		if (null == imprestOrders || imprestOrders.size() == 0) {
			return ResultUtils.error(SystemCons.FAILURE,"预借单信息不存在或已删除！");
		}
		//获取预定清单
		example = new Example(ImprestList.class);
		example.createCriteria().andEqualTo("id", imprestListId).andEqualTo("status", OrderCons.STATUS_DEFAULT);
		List<ImprestList> imprestList = imprestListDao.selectByExample(example);
		if (null == imprestList || imprestList.size() == 0) {
			return ResultUtils.error(SystemCons.FAILURE,"预借清单信息不存在或已删除！");
		}
		ImprestList imprestListDetail = imprestList.get(0);
		//获取借还单
		example = new Example(BorrowOrder.class);
		example.createCriteria().andEqualTo("id", borrowOrderId).andEqualTo("status", OrderCons.STATUS_DEFAULT);
		List<BorrowOrder> borrowOrders = borrowOrderDao.selectByExample(example);
		if (null == borrowOrders || borrowOrders.size() == 0) {
			return ResultUtils.error(SystemCons.FAILURE,"借还单信息不存在或已删除！");
		}
		//查看借还清单是否已存在
		example = new Example(BorrowList.class);
		example.createCriteria().andEqualTo("imprestOrderId", imprestOrderId)
		.andEqualTo("imprestListId", imprestListId)
		.andEqualTo("borrowOrderId", borrowOrderId)
		.andEqualTo("status", OrderCons.STATUS_DEFAULT);
		List<BorrowList> borrowLists = borrowListDao.selectByExample(example);
		if (null != null && borrowLists.size() > 0) {
			return ResultUtils.error(SystemCons.FAILURE,"借还清单已存在！");
		}
		BorrowList borrowList = null;
		int saveCount = 0;
		try {
			borrowList = new BorrowList();
			borrowList.setImprestOrderId(imprestOrderId);
			borrowList.setImprestListId(imprestListId);
			borrowList.setBorrowOrderId(borrowOrderId);
			borrowList.setOperUid(user.getId());
			borrowList.setOperUname(user.getUserName());
			borrowList.setToolTypeNum(imprestListDetail.getToolTypeNum());
			borrowList.setToolTypeName(imprestListDetail.getToolTypeName());
			borrowList.setToolCode(toolCode);
			borrowList.setGoodsAllo(goodsAllo);
			borrowList.setBorrowerId(imprestListDetail.getBorrowerId());
			borrowList.setBorrowerName(imprestListDetail.getBorrowerName());
			borrowList.setLeaderId(imprestListDetail.getLeaderId());
			borrowList.setLeaderName(imprestListDetail.getLeaderName());
			borrowList.setExplains(imprestListDetail.getExplains());
			borrowList.setPartRemark(imprestListDetail.getPartRemark());
			borrowList.setInsertTime(new Date());
			borrowList.setBorrowerStatus(1);//默认为未还状态
			borrowList.setBorrowerTime(DateUtils.getDateTime(borrowerTime, "yyyy-MM-dd"));
			borrowList.setEndTime(DateUtils.getDateTime(endTime, "yyyy-MM-dd"));
			saveCount = borrowListDao.insertSelective(borrowList);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return saveCount > 0 ? ResultUtils.success(saveCount, borrowList,"借还清单创建成功!") : ResultUtils.error(SystemCons.FAILURE, "创建借还清单失败!");
	}

	@Override
	public Result updateBorrowList(Integer uid, Integer id, String toolCode,
			String goodsAllo, String borrowerTime, String endTime,Integer borrowerStatus) {
		if (null == uid) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "用户id不能为空!");
		}
		if (StringUtils.isBlank(toolCode)) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "机具编码不能为空!");
		}
		if (StringUtils.isBlank(goodsAllo)) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "机具具体货位不能为空!");	
		}
		if (StringUtils.isBlank(borrowerTime)) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "借出日期不能为空!");
		}
		if (StringUtils.isBlank(endTime)) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "截止日期不能为空!");
		}
		LoginUser user = loginUserDao.selectByPrimaryKey(uid);
		if (null == user) {
			return ResultUtils.error(SystemCons.FAILURE,"用户不存在！");
		}
		if (StringUtils.isNotBlank(goodsAllo)) {
			goodsAllo = EncodeUtils.urlDecode(goodsAllo);
		}
		Example example = new Example(BorrowList.class);
		example.createCriteria().andEqualTo("id", id).andEqualTo("status", OrderCons.STATUS_DEFAULT).andEqualTo("operUid", uid);
		int updateCount = 0;
		BorrowList borrowList = null;
		try {
			List<BorrowList> list = borrowListDao.selectByExample(example);
			if (null == list || list.size() == 0) {
				return ResultUtils.error(SystemCons.FAILURE, "借还清单不存在！");
			}
			borrowList = list.get(0);
			borrowList.setOperUid(user.getId());
			borrowList.setOperUname(user.getUserName());
			borrowList.setToolCode(toolCode);
			borrowList.setGoodsAllo(goodsAllo);
			borrowList.setUpdateTime(new Date());
			borrowList.setBorrowerStatus(1);//默认为未还状态
			borrowList.setBorrowerTime(DateUtils.getDateTime(borrowerTime, "yyyy-MM-dd"));
			borrowList.setEndTime(DateUtils.getDateTime(endTime, "yyyy-MM-dd"));
			if (null != borrowerStatus) {
				borrowList.setBorrowerStatus(borrowerStatus);
			}
			updateCount = borrowListDao.updateByPrimaryKeySelective(borrowList);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return updateCount > 0 ? ResultUtils.success(1,borrowList,"更新成功") : ResultUtils.error(SystemCons.FAILURE, "借还清单更新失败！");
	}

	@Override
	public Result deleteBorrowList(Integer uid, Integer borrowListId) {
		if (null == uid) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "用户id不能为空!");
		}
		LoginUser user = loginUserDao.selectByPrimaryKey(uid);
		if (null == user) {
			return ResultUtils.error(SystemCons.FAILURE,"用户不存在！");
		}
		Example example = new Example(BorrowList.class);
		example.createCriteria().andEqualTo("id", borrowListId).andEqualTo("status", OrderCons.STATUS_DEFAULT);
		int delCount = 0;
		try {
			
			List<BorrowList> list = borrowListDao.selectByExample(example);
			if (null == list || list.size() == 0) {
				return ResultUtils.error(SystemCons.FAILURE, "借还清单不存在！");
			}
			BorrowList borrowListDetail = list.get(0);
			borrowListDetail.setStatus(OrderCons.STATUS_DELETE);
			delCount = borrowListDao.updateByPrimaryKeySelective(borrowListDetail);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return delCount > 0 ? ResultUtils.success(delCount,"","删除借还清单成功!") : ResultUtils.error(SystemCons.FAILURE, "借还清单删除失败！");
	}

	@Override
	public Result getBorrowListDetail(Integer uid, Integer borrowListId) {
		if (null == uid) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "用户id不能为空!");
		}
		Example example = new Example(BorrowList.class);
		example.createCriteria().andEqualTo("id", borrowListId).andEqualTo("status", OrderCons.STATUS_DEFAULT);
		
		try {
			List<BorrowList> list = borrowListDao.selectByExample(example);
			if (null != list && list.size() > 0) {
				return ResultUtils.success(1, list.get(0));
			}
			return ResultUtils.failure("清单不存在!");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.error(SystemCons.FAILURE, "获取借还清单信息失败！");
	}

	@Override
	public Result getBorrowList(Integer uid, Integer borrowOrderId,int pageNum, int pageSize) {
		if (null == uid) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "用户id不能为空!");
		}
		if (null == borrowOrderId) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "借还单id不能为空!");
		}
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<BorrowList> pageInfo = null;
		try {
			Example example = new Example(BorrowList.class);
			example.createCriteria().andEqualTo("borrowOrderId", borrowOrderId).andEqualTo("status", OrderCons.STATUS_DEFAULT);
			List<BorrowList> list = borrowListDao.selectByExample(example);
			if (null != list) {
				pageInfo = new PageInfo<BorrowList>(list);
				return ResultUtils.success(pageInfo.getTotal(), list);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ResultUtils.error(SystemCons.FAILURE, "获取借还清单列表失败！");
	}

}
