package com.clj.springboot.util;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class JavaWebToken {

    @Autowired
    private RedisTemplate redisTemplate;

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
        boolean keys = redisTemplate.hasKey(key);
        if (keys){
            System.out.println("=================true===========");
            System.out.println(vo.get(key));
        }else{
            System.out.println("=================false===========");
            vo.set(key, token,5,
                    TimeUnit.MINUTES);
        }
        return token;
    }
    /**
     * 检查token是否正确
     * @param token 令牌
     * @return true正确;false失败
     */
    public boolean checkToken(String token){
        ValueOperations<String,Object> vo = redisTemplate.opsForValue();
        //解析出userId和uuid
        if(token==null || "".equals(token)){
            return false;
        }
        String[] arr1 = token.split("_");
        if(arr1.length != 2){
            return false;
        }
        //根据redis进行检查
        String key = arr1[0]+"_token";
        String r_token = (String)redisTemplate.opsForValue().get(key);
        if(r_token==null){
            return false;
        }
        if(!token.equals(r_token)){
            return false;
        }
        //返回检测结果,更新token时间
        vo.set(key, token,
                5, TimeUnit.MINUTES);
        return true;
    }

}
