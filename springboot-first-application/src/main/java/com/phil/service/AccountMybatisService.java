package com.phil.service;


import com.phil.dao.AccountMybatisMapper;
import com.phil.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountMybatisService {
    @Autowired
    private AccountMybatisMapper accountMybatisMapper;

    public int add(String name, double money) {
        return accountMybatisMapper.add(name, money);
    }

    public int update(String name, double money, int id) {
        return accountMybatisMapper.update(name, money, id);
    }

    public int delete(int id) {
        return accountMybatisMapper.delete(id);
    }

    public Account findAccount(int id) {
        return accountMybatisMapper.findAccount(id);
    }

    public List<Account> findAccountList() {
        return accountMybatisMapper.findAccountList();
    }
}