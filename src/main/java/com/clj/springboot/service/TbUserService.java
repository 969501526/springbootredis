package com.clj.springboot.service;

import com.clj.springboot.mapper.TbUserMapper;
import com.clj.springboot.model.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TbUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbUserMapper tbUserMapper;

  public TbUser selectById(Integer id){
      ValueOperations<String,TbUser> vo = redisTemplate.opsForValue();
      String key = "user"+id;
      System.out.println(key);
      boolean hashKey = redisTemplate.hasKey(key);

      if(hashKey){
          TbUser tb = vo.get(key);
          System.out.println("==========从缓存中获得数据=========");
          return tb;
      }else{
          TbUser tbUser = tbUserMapper.selectById(id);

          System.out.println("==========从数据表中获得数据=========");

          vo.set(key,tbUser,5, TimeUnit.MINUTES);

          return tbUser;
      }
  }
}
