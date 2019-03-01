package com.wh.tms.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wh.tms.constans.NoticeCons;
import com.wh.tms.dao.notice.INoticeDAO;
import com.wh.tms.dao.notice.INoticePersonDAO;
import com.wh.tms.dao.notice.INoticePullDAO;
import com.wh.tms.dao.user.ILoginUserDAO;
import com.wh.tms.entity.po.LoginUser;
import com.wh.tms.entity.po.Notice;
import com.wh.tms.entity.po.NoticePerson;
import com.wh.tms.entity.po.NoticePull;
import com.wh.tms.entity.vo.NoticeVo;
import com.wh.tms.result.Result;
import com.wh.tms.service.INoticeService;
import com.wh.tms.util.DateUtils;
import com.wh.tms.util.EncodeUtils;
import com.wh.tms.util.ResultUtils;

@Service("noticeService")
public class NoticeServiceImpl implements INoticeService {
	
	private static final Log log = LogFactory.getLog(NoticeServiceImpl.class);
	
	@Autowired
	private INoticeDAO noticeDao;
	@Autowired
	private INoticePersonDAO noticePersonDao;
	@Autowired
	private ILoginUserDAO userDao;
	@Autowired
	private INoticePullDAO noticePullDao;

	@Override
	public Result deleteNotice(Integer uid,Integer noticeId) {
		if (null == uid || null == noticeId) {
			return ResultUtils.paramsEmpty("参数不能为空!");
		}
		LoginUser user = userDao.selectByPrimaryKey(uid);
		if (null == user) {
			return ResultUtils.failure("用户不存在!");
		}
		NoticePerson np = null;
		Example ex = new Example(NoticePerson.class);
		ex.createCriteria().andEqualTo("uid", uid)
		.andEqualTo("noticeId", noticeId);
		List<NoticePerson> list = noticePersonDao.selectByExample(ex);
		if (null == list || list.size() == 0) {
			//获取通知类型
			Notice notice = noticeDao.selectByPrimaryKey(noticeId);
			if (notice != null && NoticeCons.NOTICE_TYPE_ANNOUNCEMENT.equals(notice.getSourceType())) {
				//如果是公告,写入通知接收人并标记为已删除
				np = new NoticePerson();
				np.setNoticeId(noticeId);
				np.setInsertTime(System.currentTimeMillis());
				np.setStatus(NoticeCons.NOTICE_PERSON_STATUS_DELETE);
				np.setUid(uid);
				np.setUserName(user.getUserName());
				int insertCount = noticePersonDao.insertSelective(np);
				return insertCount > 0 ? ResultUtils.success(insertCount, null,"消息删除成功~") : ResultUtils.failure("删除失败!");
			}else{
				return ResultUtils.failure("消息不存在!");
			}
			
		}
		np = list.get(0);
		Integer status = np.getStatus();
		if (NoticeCons.NOTICE_PERSON_STATUS_DELETE.equals(status)) {
			return ResultUtils.success(null);
		}
		//标记为删除
		np.setStatus(NoticeCons.NOTICE_PERSON_STATUS_DELETE);
		int updateCount = noticePersonDao.updateByPrimaryKeySelective(np);
		return updateCount > 0 ? ResultUtils.success(updateCount, null,"消息删除成功~") : ResultUtils.failure("删除失败!");
	}

	@Override
	public Result recallNotice(Integer createrPuid, Integer noticeId) {
		if (null == createrPuid || null == noticeId) {
			return ResultUtils.paramsEmpty("参数不能为空!");
		}
		Example ex = new Example(Notice.class);
		ex.createCriteria().andEqualTo("createrUid", createrPuid)
		.andEqualTo("id", noticeId);
		List<Notice> list = noticeDao.selectByExample(ex);
		if (null == list || list.size() == 0) {
			return ResultUtils.failure("消息不存在!");
		}
		Notice notice = list.get(0);
		notice.setStatus(NoticeCons.NTICE_STATUS_RECALL);//标记已撤回
		int updateCount = noticeDao.updateByPrimaryKeySelective(notice);
		return updateCount > 0 ? ResultUtils.success(updateCount,null, "消息撤回成功~") : ResultUtils.failure("撤回失败!");
	}

	@Override
	public Result getNotice(Integer uid, Integer noticeId) {
		if (null == uid || null == noticeId) {
			return ResultUtils.paramsEmpty("参数不能为空!");
		}
		LoginUser user = userDao.selectByPrimaryKey(uid);
		if (null == user) {
			return ResultUtils.failure("用户不存在!");
		}
		//验证消息
		Notice notice = noticeDao.selectByPrimaryKey(noticeId);
		if (null == notice) {
			return ResultUtils.failure("消息不存在!");
		}
		NoticeVo noticeVo = NoticeVo.getInstance(notice);
		Integer noticeStatus = noticeVo.getStatus();
		if (null == noticeStatus) {
			return ResultUtils.failure("消息状态异常!");
		}
		//如果已撤回
		if (NoticeCons.NTICE_STATUS_RECALL.equals(noticeStatus)) {
			//是否是消息发送人
			Integer createrUid = noticeVo.getCreaterUid();
			if (!uid.equals(createrUid)) {
				return ResultUtils.failure("消息已撤回!");
			}
		}
		//是否是消息接收人
		Example ex = new Example(NoticePerson.class);
		ex.createCriteria().andEqualTo("uid", uid)
		.andEqualTo("noticeId", noticeId);
		List<NoticePerson> noticePersons = noticePersonDao.selectByExample(ex);
		//获取消息类型
		Integer sourceType = notice.getSourceType();
		NoticePerson np = null;
		//如果是公告
		if (NoticeCons.NOTICE_TYPE_ANNOUNCEMENT.equals(sourceType)) {
			if (null == noticePersons || noticePersons.size() == 0) {
				//写入消息接收人
				np = new NoticePerson();
				np.setNoticeId(noticeId);
				np.setInsertTime(System.currentTimeMillis());
				np.setIsRead(NoticeCons.NOTICE_READ_STATUS_READ);//标记为已读
				np.setReadTime(System.currentTimeMillis());
				np.setUid(uid);
				np.setUserName(user.getUserName());
				int savePersonCount = noticePersonDao.insertSelective(np);
				if (savePersonCount < 1) {
					ResultUtils.failure("获取消息失败！");
				}
				noticePersons = noticePersonDao.selectByExample(ex);
			}
		}
		if (null == np) {
			//验证是否是消息接收人
			if (null == noticePersons || noticePersons.size() == 0) {
				//是否是发送人
				if (!noticeVo.getCreaterUid().equals(uid)) {
					return ResultUtils.failure("消息不存在!");
				}else{
					return ResultUtils.success(noticeVo);
				}
			}else{
				np = noticePersons.get(0);
			}
		}
		//验证接收人状态
		Integer status = np.getStatus();
		//消息是否删除
		if (NoticeCons.NOTICE_PERSON_STATUS_DELETE.equals(status)) {
			return ResultUtils.failure("消息不存在!");
		}
		//如果是未读，标记为已读
		Integer isRead = np.getIsRead();
		if (!NoticeCons.NOTICE_READ_STATUS_READ.equals(isRead)) {
			np.setIsRead(NoticeCons.NOTICE_READ_STATUS_READ);//标记为已读
			np.setReadTime(System.currentTimeMillis());
			int uopdateCount = noticePersonDao.updateByPrimaryKeySelective(np);
			if (uopdateCount < 1) {
				ResultUtils.failure("获取消息失败！");
			}
		}
		noticeVo.setIsRead(np.getIsRead());
		noticeVo.setReadTime(DateUtils.format(np.getReadTime(), "yyyy-MM-dd HH:mm:ss"));
		return ResultUtils.success(noticeVo);
	}

	@Override
	public Result updateNotice(Integer noticeId, Integer createrUid,String title, String content) {
		if (null == noticeId || null == createrUid) {
			return ResultUtils.paramsEmpty("参数不能为空!");
		}
		if (StringUtils.isBlank(title) && StringUtils.isBlank(content)) {
			return ResultUtils.paramsEmpty("标题和内容不能同时为空!");
		}
		LoginUser user = userDao.selectByPrimaryKey(createrUid);
		if (null == user) {
			return ResultUtils.failure("用户不存在!");
		}
		//验证消息
		Notice notice = noticeDao.selectByPrimaryKey(noticeId);
		if (null == notice) {
			return ResultUtils.failure("消息不存在!");
		}
		if (!notice.getCreaterUid().equals(createrUid)) {
			return ResultUtils.failure("没有权限!");
		}
		if (StringUtils.isNotBlank(title)) {
			title = EncodeUtils.urlDecode(title);
		}
		if (StringUtils.isNotBlank(content)) {
			content = EncodeUtils.urlDecode(content);
		}
		notice.setUpdateTime(new Date());
		int updateCount = noticeDao.updateByPrimaryKeySelective(notice);
		return updateCount > 0 ? ResultUtils.success(updateCount, null,"消息更新成功~") : ResultUtils.failure("更新失败!");
	}

	@Override
	@Transactional
	public Result sendNotice(Integer createrUid, Integer sourceType,
			String title, String content, String recipient) {
		
		if (null == createrUid) {
			return ResultUtils.paramsEmpty("发送人id不能为空!");
		}
		if (null == sourceType) {
			return ResultUtils.paramsEmpty("消息类型不能为空!");
		}
		//通知类型不需要接收人
		if (!NoticeCons.NOTICE_TYPE_ANNOUNCEMENT.equals(sourceType) && StringUtils.isBlank(recipient)) {
			return ResultUtils.paramsEmpty("接收人不能为空!");
		}
		if (StringUtils.isBlank(title) && StringUtils.isBlank(content)) {
			return ResultUtils.paramsEmpty("标题和内容不能同时为空!");
		}
		
		LoginUser user = userDao.selectByPrimaryKey(createrUid);
		if (null == user) {
			return ResultUtils.failure("发送人信息不存在!");
		}
		if (StringUtils.isNotBlank(title)) {
			title = EncodeUtils.urlDecode(title);
		}
		if (StringUtils.isNotBlank(content)) {
			content = EncodeUtils.urlDecode(content);
		}
		
		Date date = new Date();
		Notice notice = new Notice();
		notice.setCreaterUid(createrUid);
		notice.setCreaterName(user.getUserName());
		notice.setTitle(title);
		notice.setContent(content);
		notice.setSourceType(sourceType);
		notice.setInsertTime(date);
		notice.setInsertLongTime(date.getTime());
		if (NoticeCons.NOTICE_TYPE_ANNOUNCEMENT.equals(sourceType)) {
			notice.setStatus(NoticeCons.NOTICE_STATUS_SENDEND);
		}else{
			notice.setStatus(NoticeCons.NOTICE_STATUS_SEND);
		}
		int saveCount = noticeDao.insertSelective(notice);
		if (saveCount < 1) {
			return ResultUtils.failure("发送失败！");
		}
		Set<Integer> uids = new HashSet<Integer>();
		uids.add(createrUid);
		//如果是公告，返回成功
		NoticeVo vo = null;
		if (StringUtils.isNotBlank(recipient)) {
			String[] users = recipient.split(",");
			if (null == users || users.length == 0) {
				return ResultUtils.paramsEmpty("接收人不能为空!");
			}
			for (String uid : users) {
				uids.add(Integer.parseInt(uid));
			}
			if (uids.size() == 0) {
				return ResultUtils.paramsEmpty("接收人不能为空!");
			}
		}
		Example ex = new Example(LoginUser.class);
		ex.createCriteria().andIn("id", uids);
		List<LoginUser> list = userDao.selectByExample(ex);
		if (null == list || list.size() == 0) {
			return ResultUtils.failure("未获取到消息接收人信息！");
		}
		NoticePerson np = null;
		JSONArray array = new JSONArray();
		JSONObject obj = null;
		for (LoginUser loginUser : list) {
			obj = new JSONObject();
			Integer uid = loginUser.getId();
			np = new NoticePerson();
			np.setNoticeId(notice.getId());
			np.setUid(uid);
			np.setUserName(loginUser.getUserName());
			np.setInsertTime(System.currentTimeMillis());
			if (createrUid.equals(uid)) {
				np.setIsReceive(1);//标记为接收人
				np.setIsRead(NoticeCons.NOTICE_READ_STATUS_READ);
				np.setReadTime(System.currentTimeMillis());
			}
			this.noticePersonDao.insertSelective(np);
			obj.put("uid", uid);
			obj.put("name", loginUser.getUserName());
			array.add(obj);
		}
		//标记通知发送完毕
		notice.setUsers(array.toJSONString());
		notice.setStatus(NoticeCons.NOTICE_STATUS_SENDEND);
		int updateCount = this.noticeDao.updateByPrimaryKeySelective(notice);
		vo = NoticeVo.getInstance(notice);
		return updateCount > 0 ? ResultUtils.success(updateCount, vo,"消息发送成功~") : ResultUtils.failure("发送失败!");
	}
	
	

	@Override
	@Transactional
	public Result getReadCount(Integer uid, Integer sourceType,Integer isread) {
		if (null == isread) {
			return ResultUtils.paramsEmpty("标识不能为空!");
		}
		if (null == uid) {
			return ResultUtils.paramsEmpty("用户id不能为空!");
		}
		LoginUser user = userDao.selectByPrimaryKey(uid);
		if (null == user) {
			return ResultUtils.failure("发送人信息不存在!");
		}
		//拉取公告
		Example ex = new Example(NoticePull.class);
		ex.createCriteria().andEqualTo("uid", uid);
		List<NoticePull> pulls = this.noticePullDao.selectByExample(ex);
		long prevPullTime = 0;
		boolean isUpdate = false;
		NoticePull pull = null;
		
		if (null != pulls && pulls.size() > 0) {
			pull = pulls.get(0);
		}
		if (null != pull) {
			isUpdate = true;
			prevPullTime = pull.getPrevPullTime();//上一次拉去的时间
		}else{
			pull = new NoticePull();
			pull.setUid(uid);
		}
		//查询上一次拉去后的公告
		ex = new Example(Notice.class);
		Example.Criteria query = ex.createCriteria();
		query.andEqualTo("status", NoticeCons.NOTICE_STATUS_SENDEND)
		.andEqualTo("sourceType", NoticeCons.NOTICE_TYPE_ANNOUNCEMENT)
		.andNotEqualTo("createrUid", uid);
		if (prevPullTime != 0) {
			query.andGreaterThan("insertLongTime", prevPullTime);
		}
		ex.orderBy("id").asc();
		List<Notice> notices = this.noticeDao.selectByExample(ex);
		if (null != notices && notices.size() > 0) {
			Set<Integer> noticeIds = new HashSet<Integer>();
			for (Notice notice : notices) {
				noticeIds.add(notice.getId());
			}
			//查询公告是否已存在
			ex = new Example(NoticePerson.class);
			ex.createCriteria().andEqualTo("uid", uid).andIn("noticeId", noticeIds);
			List<NoticePerson> noticePersons = this.noticePersonDao.selectByExample(ex);
			Map<Integer, NoticePerson> npMap = null;
			if (null != noticePersons && noticePersons.size() > 0) {
				npMap = new HashMap<Integer,NoticePerson>();
				for (NoticePerson noticePerson : noticePersons) {
					npMap.put(noticePerson.getNoticeId(), noticePerson);
				}
				if (null != npMap && !npMap.isEmpty()) {
					//移除已接收的公告
					ListIterator<Notice> it = notices.listIterator();
					while(it.hasNext()) {
						Notice notice = it.next();
						if (npMap.get(notice.getId()) != null) {
							it.remove();
						}
					}
				}
			}
			
		}
		int count = 0;
		if (null != notices) {
			count = notices.size();
		}
		if (count > 0) {
			Notice notice = notices.get(count - 1);
			pull.setPrevPullTime(notice.getInsertTime().getTime());
		}else{
			//设置拉取时间为本次拉取最后一条公告的时间
			pull.setPrevPullTime(System.currentTimeMillis());
		}
		if (isUpdate) {
			this.noticePullDao.updateByPrimaryKeySelective(pull);
		}else{
			this.noticePullDao.insertSelective(pull);
		}
		//如果有未拉去的公告，建立接收关系
		if (count > 0) {
			NoticePerson noticePerson = null;
			for (Notice notice : notices) {
				Integer createrUid = notice.getCreaterUid();
				noticePerson = new NoticePerson();
				noticePerson.setNoticeId(notice.getId());
				noticePerson.setUid(uid);
				noticePerson.setUserName(user.getUserName());
				noticePerson.setInsertTime(System.currentTimeMillis());
				if (createrUid.equals(uid)) {
					noticePerson.setIsReceive(1);//标记为接收人
					noticePerson.setIsRead(NoticeCons.NOTICE_READ_STATUS_READ);//标记为已读
					noticePerson.setReadTime(System.currentTimeMillis());
				}
				this.noticePersonDao.insertSelective(noticePerson);
			}
		}
		//获取消息已读未读数
		long noticeCount = this.noticePersonDao.selectNoticeCount(uid, sourceType, isread);
		return ResultUtils.success(noticeCount, null);
	}

	@Override
	public Result getMyNoticeList(Integer uid,Integer type,String createrName,String title,Integer sourceType,Integer isread,int pageNum,int pageSize) {
		if (null == uid) {
			return ResultUtils.paramsEmpty("用户id不能为空!");
		}
		if (StringUtils.isNotBlank(createrName)) {
			createrName = EncodeUtils.urlDecode(createrName);
		}
		if (StringUtils.isNotBlank(title)) {
			title = EncodeUtils.urlDecode(title);
		}
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<NoticeVo> pageInfo = null;
		long totalCount = 0;
		List<NoticeVo> list = null;
		try {
			list = this.noticePersonDao.selectMyNotice(type,uid,createrName, title, sourceType, isread);
			if (null != list) {
				pageInfo = new PageInfo<NoticeVo>(list);
				totalCount = pageInfo.getTotal();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return ResultUtils.success(totalCount, list, "获取消息列表成功！");
	}
}
