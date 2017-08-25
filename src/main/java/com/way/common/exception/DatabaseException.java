package com.way.common.exception;

/**
 * User: zhaokai
 * Date: 2016/2/18 - 14:19
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class DatabaseException extends BaseException {

    public DatabaseException() {
        super();
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }
}
