package com.way.common.cache;

import java.util.concurrent.TimeUnit;

/**
 * 功能描述：
 *
 * @ClassName RedisRootNameSpace
 * @Author：xinpei.xu
 * @Date：2017/08/17 16:39
 */
public interface RedisRootNameSpace {
    public static final String CACHE_ROOT = "way";
    public static final String NAMESPACE_SEPARATOR = ":";

    enum UnitEnum {
        //长久
        FOREVER(-1L, null),
        //1个月
        ONE_MONTH(30L, TimeUnit.DAYS),
        //WEEK
        ONE_WEEK(7L, TimeUnit.DAYS),
        //1天
        ONE_DAY(1L, TimeUnit.DAYS),
        //1个小时
        ONE_HOUR(1L, TimeUnit.HOURS),
        //30分钟
        THIRTY_MIN(30L, TimeUnit.MINUTES),
        //15分钟
        FIFTEEN_MIN(15L, TimeUnit.MINUTES),
        //5分钟
        FIVE_MIN(5L, TimeUnit.MINUTES),
        //1分钟
        ONE_MIN(1L, TimeUnit.MINUTES),
        //10秒
        TEN_SEC(10L, TimeUnit.SECONDS),
        //5秒
        FIVE_SEC(5L, TimeUnit.SECONDS),
        //1秒
        ONE_SEC(1L, TimeUnit.SECONDS);

        //存储时间
        private Long stortageTime;
        //存储时间单位
        private TimeUnit timeUnit;

        private UnitEnum(Long stortageTime, TimeUnit timeUnit) {
            this.stortageTime = stortageTime;
            this.timeUnit = timeUnit;
        }

        public Long getStortageTime() {
            return stortageTime;
        }

        public TimeUnit getTimeUnit() {
            return timeUnit;
        }
    }

}
