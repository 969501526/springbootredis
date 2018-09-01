package com.clj.springboot.controller;

import com.clj.springboot.model.TbUser;
import com.clj.springboot.service.TbUserService;
import com.clj.springboot.util.JavaWebToken;
import com.clj.springboot.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private JavaWebToken javaWebToken;

    @Autowired
    private TbUserService tbUserService;

    @RequestMapping("login")
    public Msg login(@RequestBody TbUser tbUser){
        List<TbUser> list = tbUserService.login(tbUser);
        if(list.isEmpty()){
            return Msg.error().add("登陆失败","账号或密码错误");
        }
        Long id = list.get(0).getId();
       String token= javaWebToken.createToken(id.intValue());
        return Msg.success().add("登陆成功","token="+token);
    }

    @RequestMapping("success")
    public Msg aopsuccess(@RequestParam("token") String token){
        return Msg.success().add("success","ok");
    }
}
