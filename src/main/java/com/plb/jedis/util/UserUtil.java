package com.plb.jedis.util;

import java.util.Random;

/**
 * @Auther: 老痞不会Java
 * @Date: 2022/7/16-07-16-14:15
 * @Description: com.plb.jedis.util
 * @version: 1.0
 */
public class UserUtil {

    public static String getRandom(){
        Random random = new Random();
        String id ="";
        for (int i = 0; i < 6; i++) {
            id += String.valueOf(random.nextInt(10));
        }
        return id;
    }

}
