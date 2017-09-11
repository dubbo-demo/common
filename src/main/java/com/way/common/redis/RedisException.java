package com.way.common.redis;

/**
 * 功能描述：
 *
 * @ClassName RedisException
 * @Author：xinpei.xu
 * @Date：2017/08/17 16:16
 */
public class RedisException extends Exception {
    private String message;

    public RedisException() {
        this.message = "";
    }

    public RedisException(String message) {
        this.message = message;
    }

    public RedisException(String message, Throwable t) {
        super(message, t);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}