package com.way.common.redis;

import com.alibaba.fastjson.JSON;
import com.way.common.cache.RedisRootNameSpace;
import com.way.common.log.WayLogger;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述：
 *
 * @ClassName CacheService
 * @Author：xinpei.xu
 * @Date：2017/08/17 16:09
 */
@Component
public class CacheService {

    @Resource(name = "stringRedisTemplate")
    private RedisTemplate redisTemplate;

    public static final String NAMESPACE_SEPARATOR = ":";

    private static Log logger = LogFactory.getLog(CacheService.class);
    public static final RedisSerializer stringSerializer = new StringRedisSerializer();
    /**
     *
     */
    private static CacheService instance;

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        instance = new CacheService();
        instance.redisTemplate = this.redisTemplate;
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param flagId
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String key(String key, String flagId) {
        StringBuilder sb = new StringBuilder();
        return sb.append(key).append(flagId).toString();
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param flagId
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String key(String key, String flagId, String flagId2) {
        StringBuilder sb = new StringBuilder();
        return sb.append(key).append(flagId).append(":").append(flagId2).toString();
    }

    public static String generateCacheKey(String namespace, Object... keys) {
        StringBuffer out = new StringBuffer();
        out.append(namespace);
        if (keys != null && keys.length > 0) {
            out.append(NAMESPACE_SEPARATOR);
            for (int i = 0; i < keys.length; i++) {
                out.append(keys[i]);
                if (i != keys.length - 1) {
                    out.append("_");
                }
            }
        }

        return out.toString();
    }

    /**
     * ======================================== http://doc.redisfans.com 以下方法主要涉及操作KEY的基础方法
     * =========================================
     */
    public static class KeyBase {

        /**
         * 判断可以是否存在
         *
         * @param redisKey
         * @return
         */
        public static Boolean isExistsKey(String redisKey) {
            try {
                return instance.redisTemplate.hasKey(redisKey);

            } catch (Throwable e) {
                logger.error("isExistsKey key:" + redisKey + "  error:", e);
                return false;
            }
        }

        /**
         * 删除redis某个key的缓存
         *
         * @param redisKey
         */
        public static void delete(String redisKey) {
            try {
                instance.redisTemplate.delete(redisKey);
            } catch (Throwable e) {
                logger.error("delete KEY:" + redisKey + " error:", e);
            }
        }
        /**
         * 删除redis某个key的缓存
         *
         * @param redisKey
         */
        public static void deleteByParent(String redisKey) {
            try {
                Set<String> keys = instance.redisTemplate.keys(redisKey+"*");
                for (String key : keys) {
                    WayLogger.info("删除缓存KEY【"+key+"】");
                    instance.redisTemplate.delete(key);
                }
            } catch (Throwable e) {
                logger.error("delete KEY:" + redisKey + " error:", e);
            }
        }

        /**
         * 查询 key 剩余的ttl时间
         *
         * @param redisKey
         * @return
         */
        public static Long getExprise(String redisKey) {
            try {
                if (isNotEmpty(redisKey)) {
                    BoundValueOperations operations = instance.redisTemplate.boundValueOps(redisKey);
                    return operations.getExpire();
                }
            } catch (Throwable e) {
                logger.error("getExprise key:" + redisKey + "  error:", e);
            }
            return 0l;
        }

    }

    /**
     * ======================================== http://doc.redisfans.com 以下方法主要涉及操作StringKEY的基础方法
     * =========================================
     */
    public static class StringKey {

        /**
         * 将 key 的值设为 value ，当且仅当 key 不存在。
         * <p>
         * 若给定的 key 已经存在，则 SETNX 不做任何动作 key默认存储24小时
         *
         * @param key
         * @param value
         * @return
         */
        public static Boolean setIfAbsent(final String key, final Object value,
                final RedisRootNameSpace.UnitEnum unitEnum) {
            return (Boolean) instance.redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection) {
                    byte[] lockBytes = stringSerializer.serialize(key);
                    boolean locked = connection.setNX(lockBytes, stringSerializer.serialize(JSON.toJSONString(value)));

                    if (!RedisRootNameSpace.UnitEnum.FOREVER.equals(unitEnum)) {
                        connection.expire(lockBytes,
                                TimeoutUtils.toSeconds(unitEnum.getStortageTime(), unitEnum.getTimeUnit()));
                    }
                    if (locked) {
                        return true;
                    }
                    return false;
                }
            });
        }

        /**
         * 将 key 的值设为 value ，当且仅当 key 不存在。
         * <p>
         * 若给定的 key 已经存在，则 SETNX 不做任何动作
         *
         * @param key
         * @param value
         * @return
         */
        public static Boolean setIfAbsent(final String key, final Object value) {
            return setIfAbsent(key, value, RedisRootNameSpace.UnitEnum.ONE_DAY);
        }

        /**
         * 添加redis缓存， 如果原来存在，则覆盖
         *
         * @param redisKey
         * @param object
         */
        public static void set(String redisKey, Object object) {
            set(redisKey, object, RedisRootNameSpace.UnitEnum.ONE_DAY);

        }

        /**
         * 添加redis缓存 如果原来存在，则覆盖
         *
         * @param redisKey
         * @param object
         */
        public static void set(String redisKey, Object object, RedisRootNameSpace.UnitEnum unitEnum) {
            try {
                if (isNotEmpty(object)) {
                    BoundValueOperations operations = instance.redisTemplate.boundValueOps(redisKey);
                    if ((null == unitEnum) || (-1 == unitEnum.getStortageTime())) {
                        operations.set(JSON.toJSONString(object));
                    } else {
                        operations.set(JSON.toJSONString(object), unitEnum.getStortageTime(), unitEnum.getTimeUnit());
                    }

                }
            } catch (Throwable e) {
                logger.error("set key:" + redisKey + "  error:", e);
            }

        }

        /**
         * 获取redis 非list缓存 通常和set方法配套使用，即set存放的为单对象，可通过本方法get出对象
         *
         * @param redisKey
         * @param clazz
         * @return
         */
        public static <T> T getObject(String redisKey, Class<T> clazz) {
            try {
                String objectJson = (String) instance.redisTemplate.opsForValue().get(redisKey);
                if (StringUtils.isBlank(objectJson)) {
                    return null;
                }
                return JSON.parseObject(objectJson, clazz);
            } catch (Throwable e) {
                logger.error("getvalue:" + redisKey + " error:", e);
            }
            return null;
        }
        
        public static Object getCached(final byte[] redisKey){
            try {
            return instance.redisTemplate.execute(new RedisCallback<Object>() {
                    public Object doInRedis(RedisConnection connection)
                            throws DataAccessException {
                        byte[] bs = connection.get(redisKey);
                        return deserialize(bs);
                    }
                });
            } catch (Exception e) {
                logger.error("getCached:" + redisKey + " error:", e);
            }
            return null;
        }

        /**
         * 获取redis的list缓存
         *
         * @param redisKey
         * @param clazz
         * @return
         */
        public static <T> List<T> getList(String redisKey, Class<T> clazz) {
            try {
                String objectJson = (String) instance.redisTemplate.opsForValue().get(redisKey);
                if (StringUtils.isBlank(objectJson)) {
                    return new ArrayList<T>();
                }
                return JSON.parseArray(objectJson, clazz);
            } catch (Throwable e) {
                logger.error("getList:" + redisKey + " error:", e);
            }

            return new ArrayList<T>();
        }

        /**
         * 将 key 所储存的值加上增量 increment 。
         * <p>
         * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
         *
         * @param redisKey
         * @param num
         */
        public static long incrby(String redisKey, long num) {
            try {
                BoundValueOperations boundHashOperations = instance.redisTemplate.boundValueOps(redisKey);
                return boundHashOperations.increment(num);
            } catch (Throwable e) {
                logger.error("incrby key:" + redisKey + " error:", e);
                return -1l;
            }
        }

    }

    /**
     * 判断对象是否为空 null -> true "" -> true " " -> true "null" -> true empty Collection -> true empty Array -> true others
     * -> false
     *
     * @param object
     * @return
     */
    private static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof String) {
            return ((String) object).trim().equals("") || ((String) object).trim().equals("null");
        } else if (object instanceof Collection<?>) {
            return ((Collection<?>) object).isEmpty();
        } else if (object instanceof Map<?, ?>) {
            return ((Map<?, ?>) object).isEmpty();
        } else if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        } else {
            return false;
        }
    }

    private static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }
    
    
    private static Object deserialize(byte[] bytes) {
        
        Object result = null;
        if (isEmpty(bytes)) {
            return null;
        }
        try {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
                try {
                    result = objectInputStream.readObject();
                }
                catch (ClassNotFoundException ex) {
                    throw new Exception("Failed to deserialize object type", ex);
                }
            }
            catch (Throwable ex) {
                throw new Exception("Failed to deserialize", ex);
            }
        } catch (Exception e) {
            logger.error("Failed to deserialize",e);
        }
        return result;
    }

    /**
     * ======================================== http://doc.redisfans.com/hash/index.html 以下方法主要涉及操作HASH类型的数据方法
     * =========================================
     */
    public static class HashKey {
        /**
         * 将哈希表 key 中的域 field 的值设为 value 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。 如果域 field 已经存在于哈希表中，旧值将被覆盖
         *
         * @param redisKey
         * @param filedKey
         * @param object 对象默认存放有效期24小时
         */
        public static void hset(String redisKey, String filedKey, Object object) {
            hset(redisKey, filedKey, object, RedisRootNameSpace.UnitEnum.ONE_DAY);
        }

        /**
         * 添加redis的map缓存，设置超时时间 此超时时间会覆盖掉前一个超时时间
         *
         * @param redisKey 缓存key
         * @param filedKey
         * @param object 缓存对象
         * @param unitEnum 超时时间/单位 enum
         */
        public static void hset(String redisKey, String filedKey, Object object, RedisRootNameSpace.UnitEnum unitEnum) {
            try {
                if (isNotEmpty(object)) {
                    BoundHashOperations boundHashOperations = instance.redisTemplate.boundHashOps(redisKey);
                    boundHashOperations.put(filedKey, JSON.toJSONString(object));
                    if (!RedisRootNameSpace.UnitEnum.FOREVER.equals(unitEnum)) {
                        boundHashOperations.expire(unitEnum.getStortageTime(), unitEnum.getTimeUnit());
                    }
                }
            } catch (Throwable e) {
                logger.error("hset key:" + redisKey + " fieldkey:" + filedKey + " error:", e);
            }
        }

        /**
         * 返回哈希表 key 中域的数量
         *
         * @param redisKey
         * @return
         */
        public static Long hlength(String redisKey) {
            try {
                BoundHashOperations boundHashOperations = instance.redisTemplate.boundHashOps(redisKey);
                return boundHashOperations.size();
            } catch (Throwable e) {
                logger.error("hlength key:" + redisKey);
                return null;
            }
        }

        /**
         * 获取redis的map缓存
         *
         * @param redisKey
         * @param filedKey
         * @param clazz
         * @return
         */
        public static <T> T hget(String redisKey, String filedKey, Class<T> clazz) {
            try {
                BoundHashOperations boundHashOperations = instance.redisTemplate.boundHashOps(redisKey);
                String objectJson = (String) boundHashOperations.get(filedKey);
                if (StringUtils.isBlank(objectJson)) {
                    return null;
                }
                return JSON.parseObject(objectJson, clazz);
            } catch (Throwable e) {
                logger.error("hget key:" + redisKey + " fieldkey:" + filedKey + " error:", e);
            }
            return null;
        }

        /**
         * 获取哈希表的的list缓存
         *
         * @param redisKey
         * @param filedKey
         * @param clazz
         * @return
         */
        public static <T> List<T> hgetList(String redisKey, String filedKey, Class<T> clazz) {
            try {
                BoundHashOperations boundHashOperations = instance.redisTemplate.boundHashOps(redisKey);
                String objectJson = (String) boundHashOperations.get(filedKey);
                if (StringUtils.isBlank(objectJson)) {
                    return new ArrayList<T>();
                }
                return JSON.parseArray(objectJson, clazz);
            } catch (Throwable e) {
                logger.error("hgetList key:" + redisKey + " fieldkey:" + filedKey + " error:", e);
            }
            return new ArrayList<T>();
        }

        /**
         * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略
         *
         * @param redisKey
         * @param filedKey
         */
        public static void hdelete(String redisKey, String filedKey) {
            try {
                BoundHashOperations boundHashOperations = instance.redisTemplate.boundHashOps(redisKey);
                boundHashOperations.delete(filedKey);
            } catch (Throwable e) {
                logger.error("delete KEY:" + redisKey + " fileid key:" + filedKey + " error:", e);
            }
        }

        /**
         * 返回哈希表 key 中，所有的域和值。
         * <p>
         * 哈希表数据结构 INFO-- BASE,EXTENDS
         *
         * @param redisKey INFO
         * @return BASE, EXTENDS
         */
        public static Map<String, String> hgetall(String redisKey) {
            try {
                HashOperations<String, String, String> opt = instance.redisTemplate.opsForHash();
                return opt.entries(redisKey);
            } catch (Throwable e) {
                logger.error("hgetall KEY:" + redisKey + " error:", e);
            }
            return null;
        }

        /**
         * 根据 批量的redisKEY 返回对象下结果集 注意： 该方法是使用redis的Pipelined机制，批量查询效率对比 循环单个 查询的机制，效率要高 如 ： INFO* __BASE,NAME,EXTENDS
         * <p/>
         * 传入INFO0,INFO1 返回 MAP< KEY0: BASE|NAME|EXTENDS KEY1: BASE|NAME|EXTENDS
         *
         * @param redisKeys
         * @return
         */
        public static List<Map<String, String>> hgetallBatch(final List<String> redisKeys) {
            try {
                RedisCallback callback = new RedisCallback() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        for (String redisKey : redisKeys) {
                            connection.hGetAll(redisKey.getBytes());
                        }
                        return null;
                    }
                };
                return (List<Map<String, String>>) instance.redisTemplate.executePipelined(callback,
                        new StringRedisSerializer());
            } catch (Throwable e) {
                logger.error("hgetallBatch KEY:" + ArrayUtils.toString(redisKeys) + " error:", e);
            }
            return null;
        }

        public static Long hincrby(String redisKey, String hashKey, long initAddNum) {
            try {
                Long after = instance.redisTemplate.opsForHash().increment(redisKey, hashKey, initAddNum);
                return after;
            } catch (Throwable e) {
                logger.error("hincrby key:" + redisKey + " fieldkey:" + hashKey + " error:", e);
            }
            return null;
        }

        public static boolean hexists(String redisKey, String hashKey) {
            return instance.redisTemplate.opsForHash().hasKey(redisKey, hashKey);
        }
    }

    /**
     * ======================================== http://doc.redisfans.com/set/index.html 以下方法主要涉及操作SET类型的数据方法
     * =========================================
     */
    public static class SetKey {

        /**
         * SADD key member [member ...]
         * <p/>
         * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
         * <p/>
         * 假如 key 不存在，则创建一个只包含 member 元素作成员的集合。
         * <p/>
         * 当 key 不是集合类型时，返回一个错误。 RedisSetCommands
         * <p/>
         * redis的set数据结构的数据增加。
         *
         * @param key redis key
         * @param values values
         * @return 增加的记录数
         */
        public static Long sadd(String key, List<? extends Object> values) {
            return sadd(key, values, RedisRootNameSpace.UnitEnum.ONE_DAY);
        }

        public static Long sadd(String key, List<? extends Object> values, RedisRootNameSpace.UnitEnum unitEnum) {
            try {
                BoundSetOperations boundSetOperations = instance.redisTemplate.boundSetOps(key);
                List<String> jsonList = new ArrayList<String>(values.size());
                String jsonStr = null;
                for (Object obj : values) {
                    jsonStr = JSON.toJSONString(obj);
                    jsonList.add(jsonStr);
                }
                Long result = boundSetOperations.add(jsonList.toArray());

                if (!RedisRootNameSpace.UnitEnum.FOREVER.equals(unitEnum)) {
                    boundSetOperations.expire(unitEnum.getStortageTime(), unitEnum.getTimeUnit());
                }
                return result;
            } catch (Throwable e) {
                logger.error("sAdd KEY:" + key + " error:", e);
            }
            return 0L;
        }

        /**
         * 返回集合 key 的基数(集合中元素的数量)
         *
         * @param key redis key
         * @return set的记录数
         */
        public static Long scard(String key) {
            try {
                BoundSetOperations boundSetOperations = instance.redisTemplate.boundSetOps(key);
                return boundSetOperations.size();
            } catch (Throwable e) {
                logger.error("sCard KEY:" + key + "  error:", e);
            }
            return 0L;
        }

        /**
         * 移除并返回集合中的一个随机元素
         *
         * @param redisKey redis key
         * @return set value
         */
        public static <T> T spop(String redisKey, Class<T> clazz) {
            try {
                BoundSetOperations boundSetOperations = instance.redisTemplate.boundSetOps(redisKey);
                Object objectJson = boundSetOperations.pop();
                if (objectJson == null || StringUtils.isBlank(objectJson.toString())) {
                    return null;
                }
                return JSON.parseObject(objectJson.toString(), clazz);
            } catch (Throwable e) {
                logger.error("spop KEY:" + redisKey + "  error:", e);
            }
            return null;
        }

        /**
         * 返回集合中的一个随机元素
         * <p>
         * SRANDMEMBER 命令接受可选的 count 参数： 如果 count 为正数，且小于集合基数，那么命令返回一个包含 count 个元素的数组，数组中的元素各不相同。如果 count
         * 大于等于集合基数，那么返回整个集合。 如果 count 为负数，那么命令返回一个数组，数组中的元素可能会重复出现多次，而数组的长度为 count 的绝对值。 该操作和 SPOP 相似，但 SPOP
         * 将随机元素从集合中移除并返回，而 SRANDMEMBER 则仅仅返回随机元素，而不对集合进行任何改动
         * </p>
         *
         * @param redisKey
         * @param clazz
         * @param count
         * @param <T>
         * @return
         */
        public static <T> List<T> srandmember(String redisKey, Class<T> clazz, long count) {
            try {
                if (count < 0) {
                    BoundSetOperations boundSetOperations = instance.redisTemplate.boundSetOps(redisKey);
                    List objectJson = boundSetOperations.randomMembers(count * -1);
                    if (objectJson == null || StringUtils.isBlank(objectJson.toString())) {
                        return null;
                    }
                    return JSON.parseArray(objectJson.toString(), clazz);
                } else {
                    BoundSetOperations boundSetOperations = instance.redisTemplate.boundSetOps(redisKey);
                    Set objectJson = boundSetOperations.distinctRandomMembers(count);
                    if (objectJson == null || StringUtils.isBlank(objectJson.toString())) {
                        return null;
                    }
                    return JSON.parseArray(objectJson.toString(), clazz);
                }

            } catch (Throwable e) {
                logger.error("srandmember KEY:" + redisKey + "  error:", e);
            }
            return null;
        }

        /**
         * 判断 member 元素是否集合 key 的成员。
         *
         * @param key
         * @param member
         * @return
         */
        public static boolean sIsMember(String key, String member) {
            try {
                return instance.redisTemplate.boundSetOps(key).isMember(member);
            } catch (Throwable e) {
                logger.error("sIsMember KEY:" + key + " member:" + member + "  error:", e);
            }
            return false;
        }

        /**
         * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略
         *
         * @param key
         * @param member
         * @return 被成功移除的元素的数量，不包括被忽略的元素
         */
        public static Long srem(String key, String member) {
            try {
                return instance.redisTemplate.boundSetOps(key).remove(member);
            } catch (Throwable e) {
                logger.error("srem KEY:" + key + " member:" + member + "  error:", e);
            }
            return 0l;
        }
    }

    /**
     * ======================================== http://doc.redisfans.com/sorted_set/index.html
     * 以下方法主要涉及操作SortedSet类型的数据方法 =========================================
     */
    public static class SortedSetKey {
        // ============================================以下定义 SortedSet（有序集合）的操作方法==========================
        public static final double POSITIVE_INFINITY = 1.0 / 0.0;

        /**
         * A constant holding the negative infinity of type {@code double}. It is equal to the value returned by
         * {@code Double.longBitsToDouble(0xfff0000000000000L)}.
         */
        public static final double NEGATIVE_INFINITY = -1.0 / 0.0;

        /**
         * 返回有序集 key 的基数。
         *
         * @param redisKey
         * @return
         */
        public static Long zcard(String redisKey) {
            try {
                return instance.redisTemplate.boundZSetOps(redisKey).zCard();
            } catch (Throwable e) {
                logger.error("zcard KEY" + redisKey + " error:", e);
            }
            return 0L;
        }

        /**
         * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略。
         * <p/>
         * 当 key 存在但不是有序集类型时，返回一个错误。
         *
         * @param key
         * @param member
         * @return 被成功移除的成员的数量，不包括被忽略的成员
         */
        public static Long zrem(String key, String member) {
            try {
                BoundZSetOperations<String, String> opt = instance.redisTemplate.boundZSetOps(key);
                Long result = opt.remove(member);
                return result;
            } catch (Throwable e) {
                logger.error("zrem KEY" + key + "member:" + member + " error:", e);
            }
            return 0l;
        }

        /**
         * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中 示范 Set cinemaIds = new LinkedHashSet<ZSetOperations.TypedTuple
         * <String>>();
         *
         * @param key
         * @param tuples TypedTuple:对象参照org.springframework.data.redis.core.DefaultTypedTuple new DefaultTypedTuple
         *            <String>(apiView.getSignId().toString(),bigDecimal.doubleValue())
         * @return
         */
        public static Long zadd(String key, List<DefaultTypedTuple<String>> tuples) {
            return zadd(key, tuples, RedisRootNameSpace.UnitEnum.ONE_DAY);
        }

        public static Long zadd(String key, List<DefaultTypedTuple<String>> tuples,
                RedisRootNameSpace.UnitEnum unitEnum) {
            try {
                BoundZSetOperations<String, String> opt = instance.redisTemplate.boundZSetOps(key);
                Long result = opt.add(new LinkedHashSet<ZSetOperations.TypedTuple<String>>(tuples));

                if (!RedisRootNameSpace.UnitEnum.FOREVER.equals(unitEnum)) {
                    opt.expire(unitEnum.getStortageTime(), unitEnum.getTimeUnit());
                }

                return result;
            } catch (Throwable e) {
                logger.error("zadd key:" + key + " error:", e);
            }
            return null;
        }

        /**
         * @param key
         * @param value
         * @param valueSrore
         */
        public static void zaddOne(String key, String value, Double valueSrore) {
            try {
                BoundZSetOperations<String, String> opt = instance.redisTemplate.boundZSetOps(key);
                Boolean result = opt.add(value, valueSrore);
            } catch (Throwable e) {
                logger.error("zadd key:" + key + " value:" + value + " score:" + valueSrore + " error:", e);
            }
        }

        /**
         * 返回有序集 key 中，指定区间内的成员。 其中成员的位置按 score 值递增(从小到大)来排序。 解析返回结果 for (ZSetOperations.TypedTuple<String> t : set) {
         * t.getValue() + t.getScore() }
         *
         * @param redisKey
         * @param start 0 表示有序集第一个成员
         * @param end
         * @return TypedTuple :key 和value一起
         */
        public static Set<ZSetOperations.TypedTuple<String>> zrange(String redisKey, int start, int end) {
            try {
                ZSetOperations<String, String> opt = instance.redisTemplate.opsForZSet();

                return opt.rangeWithScores(redisKey, Long.valueOf(start), Long.valueOf(end));

            } catch (Throwable e) {
                logger.error("zrange error:", e);
            }
            return null;
        }

        /**
         * 返回有序集 key 中，指定区间内的成员。 其中成员的位置按 score 值递减(从大到小)来排列。。 解析返回结果 for (ZSetOperations.TypedTuple<String> t : set) {
         * t.getValue() + t.getScore() }
         *
         * @param redisKey
         * @param start 0 表示有序集第一个成员
         * @param end
         * @return TypedTuple :key 和value一起
         */
        public static Set<ZSetOperations.TypedTuple<String>> zrevrange(String redisKey, int start, int end) {
            try {
                ZSetOperations<String, String> opt = instance.redisTemplate.opsForZSet();

                return opt.reverseRangeWithScores(redisKey, Long.valueOf(start), Long.valueOf(end));

            } catch (Throwable e) {
                logger.error("zrange error:", e);
            }
            return null;
        }

        /**
         * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。 有序集成员按 score 值递增(从小到大)次序排列。
         * <p>
         * min 和 max 可以是 -inf 和 +inf ，这样一来，你就可以在不知道有序集的最低和最高 score 值的情况下，使用 ZRANGEBYSCORE 这类命令。
         *
         * @param redisKey
         * @param min
         * @param max
         * @return
         */
        public static Set<ZSetOperations.TypedTuple<String>> zrangeByScore(String redisKey, Double min, Double max) {
            try {
                ZSetOperations<String, String> opt = instance.redisTemplate.opsForZSet();
                return opt.rangeByScoreWithScores(redisKey, (min == null) ? NEGATIVE_INFINITY : min,
                        (max == null) ? POSITIVE_INFINITY : max);
            } catch (Throwable e) {
                logger.error("zrangeByScore KEY:" + redisKey + " error:", e);
            }
            return null;
        }

        /**
         * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。 有序集成员按 score 值递减(从大到小)的次序排列。
         * <p>
         * min 和 max 可以是 -inf 和 +inf ，这样一来，你就可以在不知道有序集的最低和最高 score 值的情况下，使用 ZRANGEBYSCORE 这类命令。
         *
         * @param redisKey
         * @param min >=
         * @param max <=
         * @return
         */
        public static Set<ZSetOperations.TypedTuple<String>> zrevrangeByScore(String redisKey, Double min, Double max) {
            try {
                ZSetOperations<String, String> opt = instance.redisTemplate.opsForZSet();
                return opt.reverseRangeByScoreWithScores(redisKey, (min == null) ? NEGATIVE_INFINITY : min,
                        (max == null) ? POSITIVE_INFINITY : max);
            } catch (Throwable e) {
                logger.error("zrangeByScore KEY:" + redisKey + " error:", e);
            }
            return null;
        }

        /**
         * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。
         * <p>
         * 排名以 0 为底，也就是说， score 值最小的成员排名为 0
         *
         * @param key
         * @param member
         * @return
         */
        public static Long zrank(String key, String member) {
            try {
                BoundZSetOperations<String, String> opt = instance.redisTemplate.boundZSetOps(key);
                return opt.rank(member);
            } catch (Throwable e) {
                logger.error("zrank KEY:" + key + " error:", e);
            }
            return null;
        }

        /**
         * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序。。
         * <p>
         * 排名以 0 为底，也就是说， score 值最小的成员排名为 0
         *
         * @param key
         * @param member
         * @return
         */
        public static Long zrevrank(String key, String member) {
            try {
                BoundZSetOperations<String, String> opt = instance.redisTemplate.boundZSetOps(key);
                return opt.reverseRank(member);
            } catch (Throwable e) {
                logger.error("zrevrank KEY:" + key + " error:", e);
            }
            return null;
        }

        /**
         * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略
         *
         * @param key
         * @param member
         * @return 被成功移除的成员的数量，不包括被忽略的成员
         */
        public static Long zrem(String key, String... member) {
            try {
                BoundZSetOperations<String, String> opt = instance.redisTemplate.boundZSetOps(key);
                return opt.remove(member);
            } catch (Throwable e) {
                logger.error("zrevrank KEY:" + key + " error:", e);
            }
            return null;
        }
    }

    /**
     * ======================================== http://doc.redisfans.com/list/index.html 以下方法主要涉及操作LIST类型的数据方法
     * =========================================
     */
    public static class ListKey {

        /**
         * list lpush操作:将一个或多个值 value 插入到列表 key 的表头 注意： LPUSH mylist a b c ，列表的值将是 c b a 保存24小时
         *
         * @param key
         * @param list
         */
        public static void lpush(String key, List<? extends Object> list) {
            lpush(key, list, RedisRootNameSpace.UnitEnum.ONE_DAY);

        }

        /**
         * list lpush操作:将一个或多个值 value 插入到列表 key 的表头 注意： LPUSH mylist a b c ，列表的值将是 c b a
         *
         * @param key
         * @param list
         */
        public static void lpush(String key, List<? extends Object> list, RedisRootNameSpace.UnitEnum unitEnum) {
            try {
                BoundListOperations<String, String> boundListOperations = instance.redisTemplate.boundListOps(key);
                String[] s = new String[list.size()];
                int index = 0;
                for (Object T : list) {
                    s[index] = JSON.toJSONString(T);
                    index++;
                }
                boundListOperations.rightPushAll(s);
                if (!RedisRootNameSpace.UnitEnum.FOREVER.equals(unitEnum)) {
                    boundListOperations.expire(unitEnum.getStortageTime(), unitEnum.getTimeUnit());
                }
            } catch (Throwable e) {
                logger.error("lpush error:", e);
            }

        }

        /**
         * list rpush操作:将一个或多个值 value 插入到列表 key 的表尾(最右边)。
         *
         * @param key
         * @param list
         */
        public static void rpush(String key, List<? extends Object> list) {
            rpush(key, list, RedisRootNameSpace.UnitEnum.ONE_DAY);
        }

        /**
         * list rpush操作:将一个或多个值 value 插入到列表 key 的表尾(最右边)。
         *
         * @param key
         * @param list
         */
        public static void rpush(String key, List<? extends Object> list, RedisRootNameSpace.UnitEnum unitEnum) {
            try {
                BoundListOperations<String, String> boundListOperations = instance.redisTemplate.boundListOps(key);
                String[] s = new String[list.size()];
                int index = 0;
                for (Object T : list) {
                    s[index] = JSON.toJSONString(T);
                    index++;
                }
                boundListOperations.rightPushAll(s);
                if (!RedisRootNameSpace.UnitEnum.FOREVER.equals(unitEnum)) {
                    boundListOperations.expire(unitEnum.getStortageTime(), unitEnum.getTimeUnit());
                }
            } catch (Throwable e) {
                logger.error("rpush error:", e);
            }

        }

        /**
         * 截取指定区域元素，区域外的删除
         *
         * @param key
         * @param start
         * @param stop
         */
        public static void ltrim(String key, int start, int stop) {
            try {
                BoundListOperations<String, String> boundListOperations = instance.redisTemplate.boundListOps(key);
                boundListOperations.trim(start, stop);
            } catch (Throwable e) {
                logger.error("ltrim error:", e);
            }
        }

        /**
         * 截取指定区域元素，区域外的删除
         *
         * @param key
         * @param start
         * @param stop
         */
        public static <T> List<T> lrange(String key, int start, int stop, Class<T> clazz) {
            try {
                BoundListOperations<String, String> boundListOperations = instance.redisTemplate.boundListOps(key);
                List<String> list = boundListOperations.range(start, stop);
                List<T> tlist = new ArrayList<T>();
                for (String jsonStr : list) {
                    tlist.add(JSON.parseObject(jsonStr, clazz));
                }
                return tlist;
            } catch (Throwable e) {
                logger.error("lrange error:", e);
                return null;
            }
        }

        /**
         * 移除并且返回 key 对应的 list 的第一个元素。
         *
         * @param key
         * @param clazz
         * @param <T>
         * @return
         */
        public static <T> T lpop(String key, Class<T> clazz) {
            try {
                BoundListOperations<String, String> boundListOperations = instance.redisTemplate.boundListOps(key);
                String popStr = boundListOperations.leftPop();
                return JSON.parseObject(popStr, clazz);
            } catch (Throwable e) {
                logger.error("lpush error:", e);
            }
            return null;
        }

        public static long llen(String key) {
            try {
                BoundListOperations<String, String> boundListOperations = instance.redisTemplate.boundListOps(key);
                return boundListOperations.size();
            } catch (Throwable e) {
                logger.error("llen error:", e);
            }
            return 0;
        }
    }

    /**
     * ======================================== 设计分布式锁 以下方法主要涉及操作HASH类型的数据方法 =========================================
     */
    public static class WatchLock {

        private static final Long LOCK_TIME = 3000L;

        /**
         * 使用redis 锁 （三秒钟间隔) 简单模式
         *
         * @param lockKey
         * @return
         * @throws RedisLockException
         */
        public static boolean addLock(Object lockKey) throws RedisLockException {
            // 重写锁的机制 使用redis机制
            long expires = System.currentTimeMillis() + LOCK_TIME + 1;
            String expiresStr = String.valueOf(expires); // 锁到期时间
            boolean isSucess = instance.redisTemplate.boundValueOps(lockKey).setIfAbsent(expiresStr);

            if (isSucess) {// 说明原来没有值 可以插入
                return true;
            }
            throw new RedisLockException("操作太快，请稍后重新尝试!");

        }

        /**
         * 使用redis 锁 （三秒钟间隔) 简单模式
         *
         * @param lockKey
         * @return
         * @throws RedisException
         */
        public static boolean addLock(Object lockKey, Long lockTime) throws RedisLockException {
            // 重写锁的机制 使用redis机制
            boolean isSucess = instance.redisTemplate.boundValueOps(lockKey).setIfAbsent("lock");
            if (isSucess) {// 说明原来没有值 可以插入
                instance.redisTemplate.boundValueOps(lockKey).expire(lockTime, TimeUnit.SECONDS);
                return true;
            }
            throw new RedisLockException("操作太快，请稍后重新尝试!");

        }

        /**
         * 使用redis 锁 （三秒钟间隔) 需要考虑 避免死锁的问题 加强模式：
         *
         * @param lockKey
         * @return
         * @throws RedisException
         */
        public static boolean newLock(Object lockKey) throws RedisException {
            // 重写锁的机制 使用redis机制

            long expires = System.currentTimeMillis() + LOCK_TIME + 1;
            String expiresStr = String.valueOf(expires); // 锁到期时间
            boolean isSucess = instance.redisTemplate.boundValueOps(lockKey).setIfAbsent(expiresStr);

            if (isSucess) {// 说明原来没有值 可以插入
                return true;
            }

            // 针对 删除锁 可能出现的没有删除的异常情况 注意是unLock()方法没有被成功执行。
            Object currentValueObj = instance.redisTemplate.boundValueOps(lockKey).get(); // redis里的时间
            if (currentValueObj != null) {
                String currentValueStr = String.valueOf(currentValueObj);
                if (Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                    // 判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的
                    // lock is expired 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
                    String oldValueStr = (String) instance.redisTemplate.boundValueOps(lockKey).getAndSet(expiresStr);
                    // 获取上一个锁到期时间，并设置现在的锁到期时间，
                    // 只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
                    if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                        // 如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
                        // lock acquired
                        return true;
                    }
                }

            }
            throw new RedisException("操作太快，请稍后重新尝试!");

        }

        public static void unLock(String lockKey) {
            instance.redisTemplate.delete(lockKey);

        }
    }
}
