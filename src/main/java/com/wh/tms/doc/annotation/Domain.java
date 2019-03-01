package com.wh.tms.doc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
/**
 * 定义域名
* @ClassName: Domain 
* @Description: TODO 
* @author Huqk
* @date 2018年3月5日 下午10:22:51
 */
public @interface Domain {
	
	/**
	 * 所在子域名（或模块名）
	 * @return
	 */
	String value() default "";
	
	/**
	 * 别名
	 * @return
	 */
	String alias() default "";
	/**
	 * 说明
	 * @return
	 */
	String desc() default "";
	
}
