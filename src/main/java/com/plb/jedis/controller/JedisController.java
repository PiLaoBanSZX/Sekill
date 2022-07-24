package com.plb.jedis.controller;

import com.plb.jedis.util.JedisPoolUtil;
import com.plb.jedis.util.RedisUtil;
import com.plb.jedis.util.SecKill_redisByScript;
import com.plb.jedis.util.UserUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: 老痞不会Java
 * @Date: 2022/7/16-07-16-13:58
 * @Description: com.plb.jedis.controller
 * @version: 1.0
 */
@RestController
@RequestMapping("jedis")
@SuppressWarnings("ConstantConditions")
public class JedisController {

    /*
    //事务 乐观锁
    @GetMapping("/sekill")
    public HashMap seKill(@RequestParam("spid") String spid,@RequestParam("uid") String uid) {

        HashMap<String, String> map = new HashMap<>();

        if (spid == null) {
            map.put("msg","\"非法参数\"");
            return map;
        }

        // 拼接商品库存key
        String ckKey = "sk:" + spid + ":qt";
        // 拼接用户表key
        String usrKey = "sk:" + spid + ":user";

        JedisPool jedisPoolInstance = JedisPoolUtil.getJedisPoolInstance();
        Jedis jedis = jedisPoolInstance.getResource();
        jedis.auth("123456");
        // 获取库存
        String s = jedis.get(ckKey);

        if (s == null){
            jedis.close();
            System.out.println("秒杀未开始");
            map.put("msg","秒杀未开始");
            return map;
        }
        Boolean sismember = jedis.sismember(usrKey, uid);

        if (sismember){
            System.out.println("抢购成功无需重复抢购");
            jedis.close();
            map.put("msg","抢购成功无需重复抢购");
            return map;
        }

        String cks = jedis.get(ckKey);
        if (Integer.parseInt(cks) < 1 ){
            jedis.close();
            System.out.println("商品已经抢完了");
            map.put("msg","商品已经抢完了");
            return map;
        }

        jedis.watch(ckKey);

        // 解决并发超卖问题
        Transaction multi = jedis.multi();

        multi.decr(ckKey);
        multi.sadd(usrKey,uid);

        List<Object> exec = multi.exec();
        if (exec == null || exec.size() == 0 ){
            map.put("msg", "失败");
            jedis.close();
            return map;
        }
//        // 库存 -1
//        jedis.decr(ckKey);
//        // 添加用户清单
//        jedis.sadd(usrKey,uid);
        jedis.close();
        map.put("msg","成功");
        return map;
    }  */

    /**
     * lua脚本 解决库存遗留问题
     * @param spid
     * @param uid
     * @return
     */
    @GetMapping("/sekill")
    public HashMap seKill(@RequestParam("spid") String spid,@RequestParam("uid") String uid) {
        HashMap<String, String> map = new HashMap<>();
        try {
            boolean b = SecKill_redisByScript.doSecKill(spid, uid);
            System.out.println("master");
            System.out.println("master");
            System.out.println("master");
            System.out.println("master");
            System.out.println("dev");
            System.out.println("dev");
            System.out.println("dev");
            map.put("msg", b ? "成功" : "失败" );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @GetMapping("test")
    public HashMap test(){
        HashMap<String, String> map = new HashMap<>();
        map.put("msg","test");
        return map;
    }
}
