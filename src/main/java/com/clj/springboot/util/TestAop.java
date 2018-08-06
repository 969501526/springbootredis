package com.clj.springboot.util;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Configuration
public class TestAop {

//    private Integer id;
//
//    private String token;

    @Pointcut("execution(* com.clj.springboot.controller..*.*(..))")
    public void excudeService(){}

    @Before("execution(* aop*(..)) &&"+"args(id,..)")
    public Msg asp(Integer id){
        Map<String,Object> m = new HashMap<String,Object>();
        m.put("id",1);
       String str= JavaWebToken.createJavaWebToken(m);
       System.out.println(str);
        return null;
    }
}
