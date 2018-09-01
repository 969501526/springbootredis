package com.clj.springboot.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Configuration
public class TestAop {

    @Autowired
    private JavaWebToken javaWebToken;

    @Pointcut("execution(* com.clj.springboot.controller.*.aop*(..))")
    public void excudeService(){}

    @Around("excudeService()")
    public Msg asp(ProceedingJoinPoint pj) throws Throwable{
         Msg msg = null;
        Object[] object= pj.getArgs();
        String token = (String)object[0];
        boolean flag = javaWebToken.checkToken(token);
        if(flag==false){
            return Msg.error().add("error","令牌错误");
        }
            msg=(Msg)pj.proceed();

        return msg;
    }
}
