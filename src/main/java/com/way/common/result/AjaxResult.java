package com.way.common.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author xinpei.xu
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class AjaxResult implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public final static int SUCCESS_CODE = 0;
    public final static int ERROR_CODE = 1;

    public final static int SESSION_OUT_ERROR_CODE = -1000;

    // 操作状态码 0：成功 1：失败 2: 处理中 其它：详见错误码枚举
    private Integer code;

    // 描述：通常针对errcode有效
    private String message;

    // 数据：通过用于code=0 成功情况下 数据绑定
    private Object data;

    public boolean isSuccess() {
        return SUCCESS_CODE == code;
    }

    public AjaxResult() {
    }

    public AjaxResult(Object data, String message, Integer responseCode) {
        this.message = message;
        this.data = data;
        this.code = responseCode;
    }

    public static final AjaxResult success() {
        return success(null, "请求成功");
    }

    public static final AjaxResult success(Object data) {
        return success(data, "请求成功");
    }


    public static final AjaxResult success(Object data, String msg) {
        return new AjaxResult(data, msg,SUCCESS_CODE);
    }

    public static final AjaxResult failed(Integer errorCode) {
        return failed("请求失败", errorCode);
    }

    public static final AjaxResult failed(String errorMsg) {
        return failed(errorMsg, ERROR_CODE);
    }

    public static final AjaxResult failed(String errorMsg, Integer responseCode) {
        return new AjaxResult(null, errorMsg, responseCode);
    }

    public static final AjaxResult formatFromServiceResult(ServiceResult result) {
        return new AjaxResult(result.getData(), result.getMessage(), result.getCode());
    }

    /* */

    /**
     * 判断返回结果是否成功
     */
    /*
     * public boolean isSuccess() { return SUCCESS_CODE == code; }
     */
    public static final AjaxResult newSessionOut() {
        return new AjaxResult(null, "会话失效，请重新登录", SESSION_OUT_ERROR_CODE);
    }
}
