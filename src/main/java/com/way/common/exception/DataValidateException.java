package com.way.common.exception;


/**
 * User: zhaokai
 * Date: 2016/2/18 - 14:19
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class DataValidateException extends BaseException {
    private Integer errCode = 1000;
    private String message;

    public DataValidateException() {
        this.message = "";
    }

    public DataValidateException(String message) {
        this.message = message;
    }

    public DataValidateException(String message, Integer errCode) {
        this.message = message;
        this.errCode = errCode;
    }

    public DataValidateException(String message, Throwable t) {
        super(message, t);
        this.message = message;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getMessage() {
        return message;
    }

}
