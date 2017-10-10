package com.way.common.redis;

import redis.clients.jedis.Jedis;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author xinpei.xu
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface JedisAction<T> {

    public T doAction(Jedis jedis);

}
