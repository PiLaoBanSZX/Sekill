package com.plb.jedis.util;


import redis.clients.jedis.Jedis;

/**
 * @Auther: 老痞不会Java
 * @Date: 2022/7/16-07-16-14:03
 * @Description: com.plb.jedis.util
 * @version: 1.0
 */
public class RedisUtil {

    private RedisUtil(){}

    public static Jedis jedis;


    static {

        jedis = new Jedis("192.168.0.13",6379);

        jedis.auth("123456");

    }
}
