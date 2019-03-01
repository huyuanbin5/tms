package com.wh.tms.constans;

public class NoticeCons {
	
	/**
	 * 通知类型:公告
	 */
	public static final Integer NOTICE_TYPE_ANNOUNCEMENT = 1;
	/**
	 * 通知类型:通知
	 */
	public static final Integer NOTICE_TYPE_NOTICE = 2;
	/**
	 * 通知类型:任务
	 */
	public static final Integer NOTICE_TYPE_ = 3;

	/**
	 * 消息状态:发送完毕
	 */
	public static final Integer NOTICE_STATUS_SENDEND= 0;
	/**
	 * 消息状态:撤回
	 */
	public static final Integer NTICE_STATUS_RECALL = 1;
	/**
	 * 消息状态:发送中
	 */
	public static final Integer NOTICE_STATUS_SEND = -1;
	/**
	 * 消息状态:删除
	 */
	public static final Integer NOTICE_STATUS_DELETE = -2;
	
	/**
	 * 消息接收人状态:正常
	 */
	public static final Integer NOTICE_PERSON_STATUS_NORMAL = 0;
	/**
	 * 消息接收人状态:删除
	 */
	public static final Integer NOTICE_PERSON_STATUS_DELETE = -1;
	/**
	 * 消息阅读状态:未读状态
	 */
	public static final Integer NOTICE_READ_STATUS_UNREAD = 0;
	/**
	 * 消息阅读状态：已读
	 */
	public static final Integer NOTICE_READ_STATUS_READ = 1;
}
