package com.phil.dao;

import com.phil.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaDao extends JpaRepository<Account, Integer> {

}