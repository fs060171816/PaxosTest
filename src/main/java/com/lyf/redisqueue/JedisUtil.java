package com.lyf.redisqueue;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis操作类
 * Created by lyf on 2017/12/2.
 */
public class JedisUtil {
    private static String JEDIS_IP;
    private static int JEDIS_PORT;
    private static String JEDIS_PASSWORD;
    private static JedisPool jedisPool;
    static{
        Configuration conf = Configuration.getInstance();
        JEDIS_IP = conf.getString("jedis.ip","127.0.0.1");
        JEDIS_PORT = conf.getInt("jedis.port",6379);
        JEDIS_PASSWORD = conf.getString("jedis.password",null);

        // 初始化JedisPool
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(256);
        config.setMaxWaitMillis(5);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        config.setTestWhileIdle(true);

        jedisPool = new JedisPool(config,JEDIS_IP,JEDIS_PORT,60000);
    }

    public static String get(String key) {
        String value = null;
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            jedis.close();
            e.printStackTrace();
        }

        return value;
    }

    public static byte[] get(byte[] key) {
        byte[] value = null;
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis != null)jedis.close();
        }

        return value;
    }

    public static void set(byte[] key,byte[] value,int time){
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            jedis.set(key,value);
            jedis.expire(key,time);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis != null)jedis.close();
        }
    }

    public static void hset(String key,String field,String value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hset(key,field,value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis != null)jedis.close();
        }
    }

    public static String hget(String key,String field){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(key,field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis != null)jedis.close();
        }
        return null;
    }

    public static byte[] hget(byte[] key,byte[] field){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(key,field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis != null)jedis.close();
        }
        return null;
    }

    public static void hdel(byte[] key,byte[] field){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hdel(key,field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis != null)jedis.close();
        }
    }

    /***
     * 存储redis队列，顺序存储
     * @param key
     * @param value
     */
    public static void lpush(byte[] key,byte[] value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.lpush(key,value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis != null)jedis.close();
        }
    }

    /***
     * 存储redis队列，反向存储
     * @param key
     * @param value
     */
    public static void rpush(byte[] key,byte[] value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.rpush(key,value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis != null)jedis.close();
        }
    }
}
