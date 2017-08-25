package com.way.common.result;

import com.way.common.constant.ServiceResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * INFO: info
 * User: zhaokai
 * Date: 2016/8/17 - 10:05
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class ServiceResult<T> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public final static int SUCCESS_CODE = 0;
    public final static int ERROR_CODE = 1;
    // token失效--账号已经在其他设备登录
    public final static int TOKEN_EXPIRED_OTHERLOGIN = 2;
    // token失效--登录超时
    public final static int TOKEN_EXPIRED_OVERTIME = 3;
    public final static String SUCCESS_MSG = "操作成功";
    public final static String ERROR_MSG = "操作失败";

    //操作状态码 0：成功 1：失败 2: 处理中  其它：详见错误码枚举
    private Integer code;

    //描述：通常针对errcode有效
    private String message;

    //数据：通过用于code=0 成功情况下 数据绑定
    private T data;

    /**
     * 接口调用成功，不需要返回对象
     */
    public static <T> ServiceResult<T> newSuccess() {
        ServiceResult<T> result = new ServiceResult<>();
        result.setCode(SUCCESS_CODE);
        result.setMessage(SUCCESS_MSG);
        return result;
    }

    /**
     * 接口调用成功，不需要返回对象
     */
    public static <T> ServiceResult<T> newSuccessMessage(String message) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setCode(SUCCESS_CODE);
        result.setMessage(message);
        return result;
    }

    /**
     * 接口调用成功，有返回对象
     */
    public static <T> ServiceResult<T> newSuccess(T object) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setCode(SUCCESS_CODE);
        result.setMessage(SUCCESS_MSG);
        result.setData(object);
        return result;
    }

    /**
     * 接口调用失败，有错误码和描述，没有返回对象
     */
    public static <T> ServiceResult<T> newFailure(int code, String message) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 接口调用失败，有错误字符串码和描述，没有返回对象
     */
    public static <T> ServiceResult<T> newFailure() {
        ServiceResult<T> result = new ServiceResult<>();
        result.setCode(ERROR_CODE);
        result.setMessage(ERROR_MSG);
        return result;
    }

    /**
     * 接口调用失败，有错误字符串码和描述，没有返回对象
     */
    public static <T> ServiceResult<T> newFailure(String message) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setCode(ERROR_CODE);
        result.setMessage(message);
        return result;
    }

    /**
     * 接口调用失败，有错误字符串码和描述，没有返回对象
     */
    public static <T> ServiceResult<T> newByServiceResultCode(ServiceResultCode serviceResultCode) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setCode(serviceResultCode.getCode());
        result.setMessage(serviceResultCode.getMsg());
        return result;
    }

    /**
     * 判断返回结果是否成功
     */
    public boolean success() {
        return SUCCESS_CODE == code;
    }


}
