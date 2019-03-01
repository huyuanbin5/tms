package com.wh.tms.web.controller.notice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wh.tms.doc.annotation.Api;
import com.wh.tms.doc.annotation.Auth;
import com.wh.tms.doc.annotation.Demo;
import com.wh.tms.doc.annotation.Domain;
import com.wh.tms.doc.annotation.Rule;
import com.wh.tms.entity.po.LoginUser;
import com.wh.tms.result.Result;
import com.wh.tms.service.INoticeService;
import com.wh.tms.util.ResultUtils;
import com.wh.tms.web.controller.BaseController;

@Controller
@RequestMapping("/notice")
@Domain(desc="消息管理")
public class NoticeController extends BaseController {
	
	@Autowired
	private INoticeService noticeService;
	
	
	/**
	 * 发送消息
	* @Title: sendNoticeList 
	* @Description: TODO
	* @param @param title
	* @param @param content
	* @param @param sourceType
	* @param @param recipient
	* @param @param request
	* @param @return 
	* @return Result
	* @throws
	 */
	@Api(author="Jesson",
			createtime="2018-03-30",
			name="发消息",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="title",desc="标题(utf-8编码)"),
					@Rule(name="content",desc="内容(utf-8编码)"),
					@Rule(name="sourceType",desc="消息类型，1:公告,2:通知,3:任务"),
					@Rule(name="recipient",desc="消息接收人,如果是发公告此参数为空")
			},
			demo = @Demo(param="title=test&content=123&sourceType=2&recipient=2",success="{'code':0,'count':1,'data':{'content':'456',"
					+ "'createrName':'何欢','createrUid':1,'id':4,'insertTime':'2018-04-01 21:08:04','isRead':null,'rTime':null,'readTime':null,'receiveTime':null,"
					+ "'sourceType':2,'status':0,'title':'test123','updateTime':null,'userName':null,"
					+ "'users':[{'uid':2,'name':'沈朝辉'}]},'msg':'消息发送成功~'}"))
	@RequestMapping(value = "/send.json",method = RequestMethod.POST)
	public @ResponseBody Result sendNoticeList(
			@RequestParam(value = "title",required = true) String title,
			@RequestParam(value = "content",required = true) String content,
			@RequestParam(value = "sourceType",required = true) Integer sourceType,
			@RequestParam(value = "recipient",required = false) String recipient,
			HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.failure("请登录!");
		}
		try {
			return noticeService.sendNotice(user.getId(), sourceType, title, content, recipient);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultUtils.failure("消息发送失败！");
	}
	/**
	 * 撤回消息
	* @Title: recallNotice 
	* @Description: TODO
	* @param @param noticeId
	* @param @param request
	* @param @return 
	* @return Result
	* @throws
	 */
	@Api(author="Jesson",
			createtime="2018-03-30",
			name="撤回消息",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="noticeId",desc="消息id")
			},
			demo = @Demo(param="noticeId=4",success="{'code':0,'count':1,'data':null,'msg':'消息撤回成功~'}"))
	@RequestMapping(value = "/recall.json" ,method = RequestMethod.POST)
	public @ResponseBody Result recallNotice(
			@RequestParam(value = "noticeId",required = true) Integer noticeId,
			HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.failure("请登录!");
		}
		try {
			return noticeService.recallNotice(user.getId(), noticeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultUtils.failure("消息发送失败！");
	}
	/**
	 * 更新消息
	* @Title: updateNotice 
	* @Description: TODO
	* @param @param noticeId
	* @param @param title
	* @param @param content
	* @param @param request
	* @param @return 
	* @return Result
	* @throws
	 */
	@Api(author="Jesson",
			createtime="2018-04-01",
			name="更新消息",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="noticeId",desc="消息id"),
					@Rule(name="title",desc="标题(utf-8编码)"),
					@Rule(name="content",desc="内容(utf-8编码)")
			},
			demo = @Demo(param="noticeId=1&title=666&content=789",success="{'code':0,'count':1,'data':'','msg':'消息更新成功~'}"))
	@RequestMapping(value = "/update.json",method = RequestMethod.POST)
	public @ResponseBody Result updateNotice(
			@RequestParam(value = "noticeId",required = true) Integer noticeId,
			@RequestParam(value = "title",required = true) String title,
			@RequestParam(value = "content",required = true) String content,
			HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.failure("请登录!");
		}
		try {
			return noticeService.updateNotice(noticeId, user.getId(), title, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultUtils.failure("消息发送失败！");
	}
	/**
	 * 消息数量
	* @Title: getReadCount 
	* @Description: TODO
	* @param @param sourceType
	* @param @param isread
	* @param @param request
	* @param @return 
	* @return Result
	* @throws
	 */
	@Api(author="Jesson",
			createtime="2018-04-01",
			name="消息数量",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="sourceType",desc="消息类型，1:公告,2:通知,3:任务"),
					@Rule(name="read",desc="是否已读标识，0：未读，1：已读")
			},
			demo = @Demo(param="sourceType=1&read=0",success="{'code':0,'count':2,'data':null,'msg':'成功'}"))
	@RequestMapping(value = "/count.json",method = RequestMethod.POST)
	public @ResponseBody Result getReadCount(
			@RequestParam(value = "sourceType",required = true) Integer sourceType,
			@RequestParam(value = "read",required = true) Integer read,
			HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.failure("请登录!");
		}
		try {
			return noticeService.getReadCount(user.getId(), sourceType, read);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultUtils.failure("消息发送失败！");
	}
	/**
	 * 删除消息
	* @Title: delNotice 
	* @Description: TODO
	* @param @param noticeId
	* @param @param request
	* @param @return 
	* @return Result
	* @throws
	 */
	@Api(author="Jesson",
			createtime="2018-04-01",
			name="删除消息",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="noticeId",desc="消息id")
			},
			demo = @Demo(param="delete.json?noticeId=4",success="{'code':0,'count':1,'data':'消息删除成功~','msg':'成功'}"))
	@RequestMapping(value = "/delete.json",method = RequestMethod.POST)
	public @ResponseBody Result delNotice(
			@RequestParam(value = "noticeId",required = true) Integer noticeId,
			HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.failure("请登录!");
		}
		try {
			return noticeService.deleteNotice(user.getId(), noticeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultUtils.failure("消息发送失败！");
	}
	/**
	 * 通知详情
	* @Title: sendNoticeList 
	* @Description: TODO
	* @param @param title
	* @param @param content
	* @param @param sourceType
	* @param @param recipient
	* @param @param request
	* @param @return 
	* @return Result
	* @throws
	 */
	@Api(author="Jesson",
			createtime="2018-04-01",
			name="消息详情",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="noticeId",desc="消息id")
			},
			demo = @Demo(param="noticeId=2",success="{'code':0,'count':1,'data':{'content':'456','createrName':'何欢',"
					+ "'createrUid':1,'id':2,'insertTime':'2018-04-01 21:37:32','isRead':1,'rTime':null,'readTime':'2018-04-01 21:46:14','receiveTime':null,"
					+ "'sourceType':2,'status':0,'title':'test','updateTime':null,'userName':null,'users':[{'uid':2,'name':'沈朝辉'}]},'msg':'成功'}"))
	@RequestMapping(value = "/get.json",method = RequestMethod.POST)
	public @ResponseBody Result getNotice(
			@RequestParam(value = "noticeId",required = true) Integer noticeId,
			HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.failure("请登录!");
		}
		try {
			return noticeService.getNotice(user.getId(), noticeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultUtils.failure("获取消息详情失败！");
	}
	/**
	 * 消息列表
	* @Title: getNoticeList 
	* @Description: TODO
	* @param @param type
	* @param @param createrName
	* @param @param title
	* @param @param sourceType
	* @param @param read
	* @param @param page
	* @param @param limit
	* @param @param request
	* @param @return 
	* @return Result
	* @throws
	 */
	@Api(author="Jesson",
			createtime="2018-04-01",
			name="消息列表",
			auth = {
				@Auth(checkEnc = true)
			},
			params={
					@Rule(name="type",desc="1:接收的,2:发送的,3:全部。不传默认为全部"),
					@Rule(name="createrName",desc="发送人名称"),
					@Rule(name="title",desc="消息标题"),
					@Rule(name="sourceType",desc="消息类型，1:公告,2:通知,3:任务"),
					@Rule(name="read",desc="是否已读标识，0：未读，1：已读"),
					@Rule(name="page",desc="起始页数"),
					@Rule(name="limit",desc="每页显示的条数")
			},
			demo = @Demo(param="type=1&sourceType=1",success="{'code':0,'count':1,'data':[{'content':'6666','createrName':'何欢',"
					+ "'createrUid':1,'id':4,'insertTime':'2018-04-01 21:40:35.0','isRead':0,"
					+ "'isReceive':0,'rTime':null,'readTime':null,'receiveTime':1522590052004,"
					+ "'sourceType':1,'status':0,'title':'t8','uid':2,'updateTime':null,"
					+ "'userName':'沈朝辉','users':null},],'msg':'获取消息列表成功！'}"))
	@RequestMapping(value = "/list.json",method = RequestMethod.POST)
	public @ResponseBody Result getNoticeList(@RequestParam(value = "type",required = false,defaultValue = "3") Integer type,
			@RequestParam(value = "createrName" ,required = false) String createrName,
			@RequestParam(value = "title",required = false) String title,
			@RequestParam(value = "sourceType",required = false) Integer sourceType,
			@RequestParam(value = "read" ,required = false) Integer read,
			@RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
			@RequestParam(value = "limit",required = false,defaultValue = "15") Integer limit,
			HttpServletRequest request) {
		LoginUser user = getSessionUser(request);
		if (null == user) {
			return ResultUtils.failure("请登录!");
		}
		try {
			return noticeService.getMyNoticeList(user.getId(), type, createrName, title, sourceType, read, page, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultUtils.failure("获取消息列表失败！");
	}

	
}
