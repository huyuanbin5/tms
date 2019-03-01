package com.wh.tms.doc.annotation;

/**
 * 返回结果示例
* @ClassName: Demo 
* @Description: TODO 
* @author Huqk
* @date 2018年3月5日 下午10:22:31
 */
public @interface Demo {
	/**
	 * 请求示例
	 * @return
	 */
	String param() default "";
	/**
	 * 返回失败结果示例
	 * @return
	 */
	String error() default "";
	/**
	 * 请求失败成功示例
	 * @return
	 */
	String success() default "";
	
}
