package com.way.common.exception;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author xinpei.xu
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SmsException extends BaseException {

	/** 
	 * @Fields serialVersionUID : TODO
	 */ 
	private static final long serialVersionUID = -4604294797441636883L;

	public SmsException() {

		super();

	}

	public SmsException(String message, Throwable cause) {

		super(message, cause);

	}

	public SmsException(String message) {

		super(message);

	}

	public SmsException(Throwable cause) {

		super(cause);

	}

}
