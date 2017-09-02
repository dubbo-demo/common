package com.way.common.exception;


/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author xinpei.xu
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
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
