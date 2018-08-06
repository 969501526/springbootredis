package com.clj.springboot.mapper;

import com.clj.springboot.model.TbUser;

import java.util.List;

public interface TbUserMapper {
    List<TbUser> selectAll();

    TbUser selectById(Integer id);
}