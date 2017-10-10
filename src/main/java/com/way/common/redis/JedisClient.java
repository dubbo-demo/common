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
public interface JedisClient {

    public Jedis getJedis();

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     *//*
    public void destroy();*/

    void returnResource(Jedis jedis);

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param action
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public <T> T execute(JedisAction<T> action);

}
