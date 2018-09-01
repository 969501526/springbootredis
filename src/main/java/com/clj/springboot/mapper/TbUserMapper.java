package com.clj.springboot.mapper;

import com.clj.springboot.model.TbUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbUserMapper {
    List<TbUser> login(TbUser tbUser);

    TbUser selectById(Integer id);

    Integer createTable(@Param("tableName") String tableName);
}