/**
 * 
 */
package com.way.common.rom.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述：分页注解，给于PaginationInterceptor用
 *
 * @ClassName Page
 * @Author：xinpei.xu
 * @Date：2017/08/14 20:20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Page {
	/**
	 * @return page index
	 */
	String page() default "pageIndex";

	/**
	 * @return page size
	 */
	String rows() default "pageSize";

	/**
	 * @return
	 */
	String total() default "total";

}
