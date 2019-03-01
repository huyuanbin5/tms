package com.wh.tms.constans;

/**
 * 系统订单常量
 * @author Jesson
 *
 */
public class OrderCons {

	/**
	 * 订单状态:正常
	 */
	public static final Integer STATUS_DEFAULT = 0;
	/**
	 * 订单状态:已删除
	 */
	public static final Integer STATUS_DELETE = -1;
	/**
	 * 操作状态:未提交
	 */
	public static final Integer OPER_STATUS_NOT_SUBMIT = 0;
	/**
	 * 操作状态:未处理
	 */
	public static final Integer OPER_STATUS_NOT_PROCESS = 1;
	/**
	 * 操作状态:已处理
	 */
	public static final Integer OPER_STATUS_PROCESSED = 2;
	/**
	 * 操作状态:已失效
	 */
	public static final Integer OPER_STATUS_INVALIDATION = 3;
	/**
	 * 借还单状态:未完成
	 */
	public static final Integer BORROW_ORDER_STATUS_UNFINISHED = 0;
	/**
	 * 借还单状态:完成
	 */
	public static final Integer BORROW_ORDER_STATUS_COMPLATE= 0;
}
