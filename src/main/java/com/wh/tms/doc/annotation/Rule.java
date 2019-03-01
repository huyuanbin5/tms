package com.wh.tms.doc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(value=RetentionPolicy.RUNTIME)
/**
 * 规则定义
* @ClassName: Rule 
* @Description: TODO 
* @author Huqk
* @date 2018年3月5日 下午10:23:08
 */
public @interface Rule {
	
	/**
	 * 规则名称
	 * @return
	 */
	String name() default "";
	
	/**
	 * 数据类型
	 * @return
	 */
	String type() default "string";
	
	/**
	 * 规则说明
	 * @return
	 */
	String desc() default "";
	
}
