package com.way.common.exception;

/**
 * INFO: info
 * User: zhaokai
 * Date: 2016/9/9 - 15:38
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
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
