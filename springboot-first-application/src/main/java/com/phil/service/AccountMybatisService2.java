package com.phil.service;

import com.phil.dao.AccountMybatisMapper2;
import com.phil.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AccountMybatisService2 {

    @Autowired
    AccountMybatisMapper2 accountMapper2;

    @Transactional
    public void transfer() throws RuntimeException{
        accountMapper2.update(90,1);//用户1减10块 用户2加10块
        int i=1/0;
        accountMapper2.update(110,2);
    }

    //@Cacheable("account")
    public Account testCache(int id) throws RuntimeException{
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return accountMapper2.getById(id);
    }
}