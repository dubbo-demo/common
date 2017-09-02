package com.way.common.exception;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author xinpei.xu
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ForbiddenException extends Exception {
    public ForbiddenException() {

        super();

    }

    public ForbiddenException(String message, Throwable cause) {

        super(message, cause);

    }

    public ForbiddenException(String message) {

        super(message);

    }

    public ForbiddenException(Throwable cause) {

        super(cause);

    }
}
