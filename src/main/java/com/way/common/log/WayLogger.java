package com.way.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * INFO: info
 * User: xinpei.xu
 * Date: 2017/7/1 - 11:30 
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class WayLogger {

    private final static String AccessLoggerName = "way.log.access";
    private final static String ExceptionLoggerName = "way.log.exception";
    private final static String SystemLoggerName = "way.log.system";
    private final static String DebugLoggerName = "way.log.debug";
    private final static String OutInterfaceLoggerName = "way.log.outInterface";


    public static final String LOG_SEPARATOR = "|";

    /**
     * sfl4j的logger
     */
    private final static Logger accessLogger = LoggerFactory.getLogger(AccessLoggerName);
    private final static Logger exceptionLogger = LoggerFactory.getLogger(ExceptionLoggerName);
    private final static Logger systemLogger = LoggerFactory.getLogger(SystemLoggerName);
    private final static Logger debugLogger = LoggerFactory.getLogger(DebugLoggerName);
    private final static Logger outLogger = LoggerFactory.getLogger(OutInterfaceLoggerName);


    //----------------------------------------------------------- Logger method start

    /**
     * Debug等级日志，小于Info<br>
     * 由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用！！
     *
     * @param message 输出消息
     */
    public static void debug(String message) {
        // 刷新参数
        LogPropsReload.refreshLogProperties();
        if (LogPropsReload.isDebugEnabled) {
            debugLogger.info(message);
        }
    }

    /**
     * Debug等级日志，小于Info<br>
     * 由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用！！
     *
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void debug(String format, Object... arguments) {
        LogPropsReload.refreshLogProperties();
        if (LogPropsReload.isDebugEnabled) {
            debugLogger.info(format, arguments);
        }
    }

    /**
     * @param e
     * @param message
     */
    public static void debug(Throwable e, String message) {
        LogPropsReload.refreshLogProperties();
        if (LogPropsReload.isDebugEnabled) {
            debugLogger.info(message, e);
        }
    }

    /**
     * @param e
     * @param format    格式文本，{} 代表变量
     * @param arguments
     */
    public static void debug(Throwable e, String format, Object... arguments) {
        LogPropsReload.refreshLogProperties();
        if (LogPropsReload.isDebugEnabled) {
            debugLogger.info(format(format, arguments), e);
        }
    }

    /**
     * @param message 输出消息
     */
    public static void access(String message) {
        LogPropsReload.refreshLogProperties();
        if (LogPropsReload.isAccessEnabled) {
            accessLogger.info(message);
        }
    }

    /**
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void access(String format, Object... arguments) {
        LogPropsReload.refreshLogProperties();
        if (LogPropsReload.isAccessEnabled) {
            accessLogger.info(format, arguments);
        }
    }
    
    /**
     * @param message 输出消息
     */
    public static void outInterface(String message) {
        LogPropsReload.refreshLogProperties();
        if (LogPropsReload.isInterfaceEnabled) {
        	outLogger.info(message);
        }
    }

    /**
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void outInterface(String format, Object... arguments) {
        LogPropsReload.refreshLogProperties();
        if (LogPropsReload.isInterfaceEnabled) {
        	outLogger.info(format, arguments);
        }
    }

    /**
     * @param message 输出消息
     */
    public static void info(String message) {
        LogPropsReload.refreshLogProperties();
        if (LogPropsReload.isSystemEnabled) {
            systemLogger.info(message);
        }
    }

    /**
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void info(String format, Object... arguments) {
        LogPropsReload.refreshLogProperties();
        if (LogPropsReload.isSystemEnabled) {
            systemLogger.info(format, arguments);
        }
    }

    /**
     * @param e
     * @param message
     */
    public static void info(Throwable e, String message) {
        LogPropsReload.refreshLogProperties();
        if (LogPropsReload.isSystemEnabled) {
            systemLogger.info(message, e);
        }
    }

    /**
     * @param e
     * @param format    格式文本，{} 代表变量
     * @param arguments
     */
    public static void info(Throwable e, String format, Object... arguments) {
        LogPropsReload.refreshLogProperties();
        if (LogPropsReload.isSystemEnabled) {
            systemLogger.info(format(format, arguments), e);
        }
    }


    /**
     * @param message 输出消息
     */
    public static void error(String message) {
        LogPropsReload.refreshLogProperties();
        if (LogPropsReload.isExceptionEnabled) {
            exceptionLogger.info(message);
        }
    }

    /**
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void error(String format, Object... arguments) {
        LogPropsReload.refreshLogProperties();
        if (LogPropsReload.isExceptionEnabled) {
            exceptionLogger.info(format, arguments);
        }
    }

    /**
     * @param e
     * @param message
     */
    public static void error(Throwable e, String message) {
        LogPropsReload.refreshLogProperties();
        if (LogPropsReload.isExceptionEnabled) {
            exceptionLogger.info(message, e);
        }
    }

    /**
     * @param e
     * @param format    格式文本，{} 代表变量
     * @param arguments
     */
    public static void error(Throwable e, String format, Object... arguments) {
        LogPropsReload.refreshLogProperties();
        if (LogPropsReload.isExceptionEnabled) {
            exceptionLogger.info(format(format, arguments), e);
        }
    }
//----------------------------------------------------------- Private method start

    /**
     * 格式化文本
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param values   参数值
     * @return 格式化后的文本
     */
    private static String format(String template, Object... values) {
        return String.format(template.replace("{}", "%s"), values);
    }


}
