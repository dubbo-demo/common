package com.way.common.redis.impl;

import com.way.common.log.WayLogger;
import com.way.common.redis.JedisAction;
import com.way.common.redis.JedisClient;
import com.way.common.spring.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author xinpei.xu
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class JedisClientImpl implements JedisClient {

    private static final Logger logger = LoggerFactory.getLogger(JedisClientImpl.class);
    
    private JedisPool jedisPool = null;
    
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    /**
     * 获取redis连接实例
     * @return
     */
    @Override
    public Jedis getJedis() {
        Jedis jedis = null;
        JedisPool jedisPool  = this.getJedisPool();
        if(null==jedisPool){
        	this.jedisPool =  (JedisPool) SpringContextHolder.getBean("jedisPool");
        }
        try {
            jedis = this.getJedisPool().getResource();
        } catch (JedisException e) {
            WayLogger.error(e, "");
            if (jedis != null) {
                this.getJedisPool().returnBrokenResource(jedis);
            }
        }
        if (jedis == null) {
            WayLogger.error(new Throwable("JedisPool is null，Please check redis status!"), "");
        }
        return jedis;
    }

    /**
     * 扔回到连接池
     */
    @Override
    public void returnResource(Jedis jedis) {

        if (jedis != null) {
            this.getJedisPool().returnResource(jedis);
        }
    }

    @Override
    public <T> T execute(JedisAction<T> action) {
        // 获取业务层面调用execute的类
        T result = null;
        try {
            result = executeAction(action);
        } catch (Exception e) {
            throw new RedisClientException(e);
        }
        return result;
    }

    /**
     * 功能描述: 执行<br>
     * 〈功能详细描述〉
     *
     * @param action
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private <T> T executeAction(JedisAction<T> action) {
        Jedis jedis = null;
        try {
            jedis =this.getJedis();
            return action.doAction(jedis);
        } catch (JedisConnectionException jex) {
            if (jedis != null) {
                try {
                    this.returnResource(jedis);
                } catch (Exception ex) {
                    logger.warn("Can not return broken resource.", ex);
                }
                jedis = null;
            }
            throw jex;
        } finally {
            if (jedis != null) {
                try {
                	this.returnResource(jedis);
                } catch (Exception ex) {
                    logger.warn("Can not return resource.", ex);
                }
            }
        }
    }

}
