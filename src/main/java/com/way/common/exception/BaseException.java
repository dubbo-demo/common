package com.way.common.exception;

/**
 * User: zhaokai
 * Date: 2016/2/18 - 14:19
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class BaseException extends Exception {
	
	public BaseException() {
		super();
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}
}
