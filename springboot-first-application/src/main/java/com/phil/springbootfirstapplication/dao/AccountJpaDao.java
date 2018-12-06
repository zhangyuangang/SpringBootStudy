package com.phil.springbootfirstapplication.dao;

import com.phil.springbootfirstapplication.pojo.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaDao extends JpaRepository<Account, Integer> {
}