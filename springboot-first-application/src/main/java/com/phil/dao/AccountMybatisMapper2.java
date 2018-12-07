package com.phil.dao;

import com.phil.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AccountMybatisMapper2 {
    int update(@Param("money") double money, @Param("id") int id);

    Account getById(@Param("id") int id);
}