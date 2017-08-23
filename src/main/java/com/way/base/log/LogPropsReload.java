package com.way.base.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * INFO: info
 * User: zhaokai
 * Date: 2016/8/23 - 13:51
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */

public class LogPropsReload {

    private static final Logger LOG = LoggerFactory.getLogger(LogPropsReload.class);
    /*@PostConstruct
    private void tt() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        refreshLogProperties();
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        // 不允许停止监控
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e1) {
                            // just ignore
                        }
                        LOG.error("LogThread error:"
                                + e.getMessage());
                    }
                }
            }
        }.start();
    }*/
    private static final String LOG4J_HIDEPARAMS = "log4j.hideParams";
    private static final String LOG4J_RELOAD_INTERVAL = "log4j.reload.interval";
    private static final String LOG4J_DEBUGLOGGER_ISOPEN = "log4j.DebugLogger.isopen";
    private static final String LOG4J_ACCESSLOGGER_ISOPEN = "log4j.AccessLogger.isopen";
    private static final String LOG4J_OUTINTERFACELOGGER_ISOPEN = "log4j.OutInterfaceLogger.isopen";
    private static final String LOG4J_EXCEPTIONLOGGER_ISOPEN = "log4j.ExceptionLogger.isopen";
    private static final String LOG4J_SYSTEMLOGGER_ISOPEN = "log4j.SystemLogger.isopen";

    //定义类变量初始化属性
    // 刷新间隔
    private static long interval = -1l;
    // 出入参隐藏
    protected static boolean hideParams = true;
    /**
     * 上次修改时间
     */
    protected static Date lastUpdateDate = new Date();

    // 调试日志开关
    protected static boolean isDebugEnabled = false;
    // 访问日志开关
    protected static boolean isAccessEnabled = false;
    // 外部访问日志开关
    protected static boolean isInterfaceEnabled = false;
    // 异常日志开关
    protected static boolean isExceptionEnabled = false;
    // 系统日志开关
    protected static boolean isSystemEnabled = false;

    private static long default_interval = 60000L;

    /**
     * 本方法用于 当刷新log.properties日志获取最新参数出现异常情况下，默认赋予的运行配置参数
     */
    private static void initDefault() {
        // 刷新间隔
        interval = default_interval;
        // 出入参隐藏
        hideParams = true;

        lastUpdateDate = new Date();
        // 调试日志开关
        isDebugEnabled = false;
        // 访问日志开关
        isAccessEnabled = false;
        // 外部访问日志开关
        isInterfaceEnabled = false;
        // 异常日志开关
        isExceptionEnabled = true;
        // 系统日志开关
        isSystemEnabled = true;

        LOG.debug("LogThread fileid value is interval {},hideParams {},lastUpdateDate {},isDebugEnabled {}," +
                        "isAccessEnabled {},isInterfaceEnabled {},isExceptionEnabled {},isSystemEnabled:{}", interval, hideParams, lastUpdateDate, isDebugEnabled,
                isAccessEnabled, isInterfaceEnabled, isExceptionEnabled, isSystemEnabled);
    }

    /**
     * @author jun.wang
     * @Description: 整点刷新
     * @date 2014-9-16 上午11:28:25
     */
    public static void refreshLogProperties() {
        LOG.debug("new Date().getTime():{} - lastUpdateDate.getTime():{} to：{} > interval:{}", new Date().getTime(), lastUpdateDate.getTime(), new Date().getTime() - lastUpdateDate.getTime(), interval);
        // 间隔时间
        if (new Date().getTime() - lastUpdateDate.getTime() > interval) {
            ClassPathResource config = new ClassPathResource("/config/log.properties");
            if (!config.exists()) {
                LOG.error("日志刷新服务异常，配置文件log.properties不存在,将使用默认配置");
                initDefault();
            } else {
                Properties pros = new Properties();
                InputStream in = null;
                try {
                    in = config.getInputStream();
                    pros.load(in);
                } catch (IOException ex) {
                    LOG.error("日志刷新服务异常，配置文件log.properties不存在");
                    initDefault();
                } finally {
                    if (null != in) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            LOG.error("日志刷新服务异常，配置文件log.properties不存在");
                            initDefault();
                        }
                    }
                }
                // 重新加载时间间隔
                interval = Long.parseLong(pros.getProperty(LOG4J_RELOAD_INTERVAL));
                // 参数
                hideParams = Boolean.parseBoolean(pros.getProperty(LOG4J_HIDEPARAMS));
                // 调试日志开关
                try {
                    isDebugEnabled = Boolean.parseBoolean(pros.getProperty(LOG4J_DEBUGLOGGER_ISOPEN));
                } catch (Exception e) {
                    LOG.error("日志刷新服务异常，配置文件log.properties格式错误：{}", LOG4J_DEBUGLOGGER_ISOPEN);
                }
                // 访问日志开关
                try {
                    isAccessEnabled = Boolean.parseBoolean(pros.getProperty(LOG4J_ACCESSLOGGER_ISOPEN));
                } catch (Exception e) {
                    LOG.error("日志刷新服务异常，配置文件log.properties格式错误：{}", LOG4J_ACCESSLOGGER_ISOPEN);
                }
                // 接口日志
                try {
                    isInterfaceEnabled = Boolean.parseBoolean(pros.getProperty(LOG4J_OUTINTERFACELOGGER_ISOPEN));
                } catch (Exception e) {
                    LOG.error("日志刷新服务异常，配置文件log.properties格式错误：{}", LOG4J_OUTINTERFACELOGGER_ISOPEN);
                }
                // 异常日志
                try {
                    isExceptionEnabled = Boolean.parseBoolean(pros.getProperty(LOG4J_EXCEPTIONLOGGER_ISOPEN));
                } catch (Exception e) {
                    LOG.error("日志刷新服务异常，配置文件log.properties格式错误：{}", LOG4J_EXCEPTIONLOGGER_ISOPEN);
                }
                // 系统日志
                try {
                    isSystemEnabled = Boolean.parseBoolean(pros.getProperty(LOG4J_SYSTEMLOGGER_ISOPEN));
                } catch (Exception e) {
                    LOG.error("日志刷新服务异常，配置文件log.properties格式错误：{}", LOG4J_SYSTEMLOGGER_ISOPEN);
                }
                // 更新时间
                lastUpdateDate = new Date();
                config = null;

                LOG.debug("LogThread fileid value is interval {},hideParams {},lastUpdateDate {},isDebugEnabled {}," +
                                "isAccessEnabled {},isInterfaceEnabled {},isExceptionEnabled {},isSystemEnabled:{}", interval, hideParams, lastUpdateDate, isDebugEnabled,
                        isAccessEnabled, isInterfaceEnabled, isExceptionEnabled, isSystemEnabled);
            }
        }
    }


}
