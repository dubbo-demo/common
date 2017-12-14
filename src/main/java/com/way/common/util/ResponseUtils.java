package com.way.common.util;


import com.alibaba.fastjson.JSON;
import com.way.common.result.ServiceResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author xinpei.xu
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public final class ResponseUtils {
    public static final Logger log = LoggerFactory
            .getLogger(ResponseUtils.class);

    /**
     * 发送文本。使用UTF-8编码。
     *
     * @param response HttpServletResponse
     * @param text     发送的字符串
     */
    public static void renderText(HttpServletResponse response, String text) {
        render(response, "text/plain;charset=UTF-8", text);
    }

    /**
     * 发送json。使用UTF-8编码。
     *
     * @param response HttpServletResponse
     * @param text     发送的字符串
     */
    public static void renderJson(HttpServletResponse response, Object text) {
        render(response, "application/json;charset=UTF-8", JSON.toJSONString(text));
    }

    /**
     * 发送xml。使用UTF-8编码。
     *
     * @param response HttpServletResponse
     * @param text     发送的字符串
     */
    public static void renderXml(HttpServletResponse response, String text) {
        render(response, "text/xml;charset=UTF-8", text);
    }


    /**
     * 发送内容。使用UTF-8编码。
     *
     * @param response
     * @param contentType
     * @param text
     */
    public static void render(HttpServletResponse response, String contentType,
                              String text) {
        response.setContentType(contentType);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            //log.debug("响应消息格式", text);
            response.getWriter().write(text);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * @Title: toJson
     * @Description: 将返回值转换为json或者jsonP输出输出
     * @return: void
     * @param response
     * @param retcode
     * @param retinfo
     */
    public static void toJson(HttpServletResponse response, int retcode, String retinfo) {
        ServiceResult<String> serviceResult = ServiceResult.newFailure(retcode, retinfo);
        String fullContentType = null;
        try {
            // 返回Json格式
            fullContentType = "application/json;charset=UTF-8";
            response.setContentType(fullContentType);
            response.getWriter().write(JSON.toJSONString(serviceResult));
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Title: toJsonOrJsonP
     * @Description: 将返回值转换为json或者jsonP输出输出
     * @return: void
     * @param request
     * @param response
     * @param retcode
     * @param retinfo
     */
    public static void toJsonOrJsonP(HttpServletRequest request, HttpServletResponse response, int retcode, String retinfo) {
        ServiceResult<String> serviceResult = ServiceResult.newFailure(retcode, retinfo);
        String callback = request.getParameter("callback");
        String fullContentType = null;
        try {
            if(StringUtils.isNotBlank(callback)) {
                // 返回JsonP格式
                fullContentType = "application/javascript;charset=UTF-8";
                response.setContentType(fullContentType);
                response.getWriter().write(callback + "(" + JSON.toJSONString(serviceResult) + ")");
            } else {
                // 返回Json格式
                fullContentType = "application/json;charset=UTF-8";
                response.setContentType(fullContentType);
                response.getWriter().write(JSON.toJSONString(serviceResult));
            }
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Title: toJsonOrJsonP
     * @Description: 将返回值转换为json或者jsonp输出
     * @return: void
     */
    public static void toJsonP(HttpServletRequest request, ServletResponse response, ServiceResult serviceResult) {
        String callback = request.getParameter("callback");
        try {
            if(StringUtils.isNotBlank(callback)) {
                // 返回JsonP格式
                response.setContentType("application/javascript;charset=UTF-8");
                response.getWriter().write(callback + "(" + JSON.toJSONString(serviceResult) + ")");
            }
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * beeCloudResponse
     * @param response
     * @param message
     */
    public static void beeCloudResponse(HttpServletResponse response, String message) {
        try {
            // 返回JsonP格式
            response.setContentType("application/json");
            response.getWriter().write(JSON.toJSONString(message));
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

