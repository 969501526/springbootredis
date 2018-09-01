package com.clj.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class Test2 {

    private RedisTemplate redisTemplate = new RedisTemplate();

    /**
     * 生成一个令牌
     * @param userId 用户ID
     * @return 返回令牌
     */
    public  String createToken(int userId){
        ValueOperations<String,Object> vo = redisTemplate.opsForValue();
        //生成token
        UUID uuid = UUID.randomUUID();
        String token = userId+"_"
                +uuid.toString().replaceAll("-", "");
        //将token存入redis
        String key = userId+"_token";
        vo.set(key, token,5,
                TimeUnit.MINUTES);
        return token;
    }


    public static void  main(String[] args){
        Jedis jedis = new Jedis("192.168.128.128");
        jedis.set("myredis","ok");

    }

}
