package com.clj.springboot.controller;


import com.clj.springboot.service.TbUserService;
import com.clj.springboot.util.Msg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

//    @Autowired
//    private TbUserMapper tbUserMapper;

    @Autowired
    private TbUserService tbUserService;

//
//    @RequestMapping("hello")
//    public Object hello(@RequestParam(name="pageSize",defaultValue = "2") Integer pageSize,
//                        @RequestParam(name="pageNum",defaultValue = "1") Integer pageNum ){
//        PageHelper.startPage(pageNum,pageSize);
//      List<TbUser> list =  tbUserMapper.selectAll();
//        PageInfo<TbUser> info = new PageInfo<>(list);
//        return info;
//    }

    @RequestMapping("selectById")
   public Msg aopselectById(@RequestParam("id") Integer id){
        if(id==null){
            return Msg.error().add("error","id不能为空");
        }
        return Msg.success().add("success", tbUserService.selectById(id));

   }
}
