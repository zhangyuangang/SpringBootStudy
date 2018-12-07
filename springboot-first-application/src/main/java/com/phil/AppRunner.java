package com.phil;

import com.phil.service.AccountMybatisService2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    @Autowired
    AccountMybatisService2 accountService2;

    public AppRunner(AccountMybatisService2 accountService2) {
        this.accountService2 = accountService2;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(".... Fetching account");
        logger.info("findById-1 -->" + accountService2.testCache(1).getName());
        logger.info("findById-1 -->" + accountService2.testCache(1).getName());
        logger.info("findById-1 -->" + accountService2.testCache(1).getName());
        logger.info("findById-1 -->" + accountService2.testCache(1).getName());
        logger.info("findById-1 -->" + accountService2.testCache(1).getName());
        logger.info("findById-1 -->" + accountService2.testCache(1).getName());
    }

}