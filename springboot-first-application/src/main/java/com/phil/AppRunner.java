package com.phil;

import com.phil.bean.UserInfo;
import com.phil.control.GitHubLookupService;
import com.phil.service.AccountMybatisService2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

//    @Autowired
//    AccountMybatisService2 accountService2;
//
//    public AppRunner(AccountMybatisService2 accountService2) {
//        this.accountService2 = accountService2;
//    }


//    @Override
//    public void run(String... args) throws Exception {
//        logger.info(".... Fetching account");
//        logger.info("findById-1 -->" + accountService2.testCache(1).getName());
//        logger.info("findById-1 -->" + accountService2.testCache(1).getName());
//        logger.info("findById-1 -->" + accountService2.testCache(1).getName());
//        logger.info("findById-1 -->" + accountService2.testCache(1).getName());
//        logger.info("findById-1 -->" + accountService2.testCache(1).getName());
//        logger.info("findById-1 -->" + accountService2.testCache(1).getName());
//    }

    @Autowired
    private final GitHubLookupService gitHubLookupService;
    
    public AppRunner(GitHubLookupService gitHubLookupService) {
        this.gitHubLookupService = gitHubLookupService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        Future<UserInfo> page1 = gitHubLookupService.findUser("PivotalSoftware");
        Future<UserInfo> page2 = gitHubLookupService.findUser("CloudFoundry");
        Future<UserInfo> page3 = gitHubLookupService.findUser("Spring-Projects");
        Future<UserInfo> page4 = gitHubLookupService.findUser("PivotalSoftware");
        Future<UserInfo> page5 = gitHubLookupService.findUser("CloudFoundry");
        Future<UserInfo> page6 = gitHubLookupService.findUser("Spring-Projects");

        // Wait until they are all done
        while (!(page1.isDone() && page2.isDone() && page3.isDone() && page4.isDone() && page5.isDone() && page6.isDone())) {
            Thread.sleep(10); //10-millisecond pause between each check
        }

        // Print results, including elapsed time
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("--> " + page1.get());
        logger.info("--> " + page2.get());
        logger.info("--> " + page3.get());
        logger.info("--> " + page4.get());
        logger.info("--> " + page5.get());
        logger.info("--> " + page6.get());
    }

}