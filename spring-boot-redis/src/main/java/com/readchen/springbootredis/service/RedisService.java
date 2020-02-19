package com.readchen.springbootredis.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;


    public void set(String key,Object value){
//        设置key编码
        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        设置value编码
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(Object.class));
        redisTemplate.opsForValue().set(key,value);
    }

//

    /**
     *
     * @param key
     * @param value
     * @param time 设置过期时间
     * @param t 时间单位 TimeUnit.SECONDS
     */
    public void set(String key, Object value, Long time, TimeUnit t){

//        设置key编码
        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        设置value编码
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(Object.class));
        redisTemplate.opsForValue().set(key,value,time,t);
    }

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public boolean del(String key){
       return redisTemplate.delete(key);
    }


}
