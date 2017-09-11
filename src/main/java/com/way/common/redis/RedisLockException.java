package com.way.common.redis;

/**
 * 功能描述：
 *
 * @ClassName RedisLockException
 * @Author：xinpei.xu
 * @Date：2017/08/17 16:12
 */
public class RedisLockException extends RedisException {
    private String message;

    public RedisLockException() {
        this.message = "";
    }

    public RedisLockException(String message) {
        this.message = message;
    }

    public RedisLockException(String message, Throwable t) {
        super(message, t);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}