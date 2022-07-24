package com.plb.jedis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Auther: 老痞不会Java
 * @Date: 2022/7/16-07-16-17:50
 * @Description: com.plb.jedis.util
 * @version: 1.0
 */
public class JedisPoolUtil {
    private static volatile JedisPool jedisPool = null;

    private JedisPoolUtil() {
    }

    public static JedisPool getJedisPoolInstance() {
        if (null == jedisPool) {
            synchronized (JedisPoolUtil.class) {
                if (null == jedisPool) {

                    JedisPoolConfig poolConfig = new JedisPoolConfig();
                    poolConfig.setMaxTotal(200);
                    poolConfig.setMaxIdle(32);
                    poolConfig.setMaxWaitMillis(100*1000);
                    poolConfig.setBlockWhenExhausted(true);
                    poolConfig.setTestOnBorrow(true);  // ping  PONG，判断是否还存在

                    jedisPool = new JedisPool(poolConfig, "192.168.0.13", 6379, 60000,"123456");
                }
            }
        }
        return jedisPool;
    }

//    public static void release(JedisPool jedisPool, Jedis jedis) {
//        if (null != jedis) {
//            jedisPool.returnResource(jedis);
//        }
//    }

}

