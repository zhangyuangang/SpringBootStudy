package com.phil.springbootfirstapplication.dao;

import com.phil.springbootfirstapplication.pojo.Account;

import java.util.List;

public interface IAccountDAO {
    int add(Account account);

    int update(Account account);

    int delete(int id);

    Account findAccountById(int id);

    List<Account> findAccountList();
}