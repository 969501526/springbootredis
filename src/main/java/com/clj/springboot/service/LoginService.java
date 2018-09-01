package com.clj.springboot.service;

import com.clj.springboot.util.JavaWebToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private JavaWebToken javaWebToken;

    public void login(){
       javaWebToken.createToken(1);
    }
}
