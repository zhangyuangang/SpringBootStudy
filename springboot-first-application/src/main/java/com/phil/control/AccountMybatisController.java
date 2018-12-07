package com.phil.control;

import com.phil.entity.Account;
import com.phil.service.AccountMybatisService;
import com.phil.service.AccountMybatisService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mybatisAccount")
public class AccountMybatisController {

    @Autowired
    AccountMybatisService accountService;

    // 基于注解的方式，开启声明式事务
    @Autowired
    AccountMybatisService2 accountService2;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Account> getAccounts() {
        return accountService.findAccountList();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Account getAccountById(@PathVariable("id") int id) {
        return accountService.findAccount(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String updateAccount(@PathVariable("id") int id, @RequestParam(value = "name", required = true) String name,
                                @RequestParam(value = "money", required = true) double money) {
        int t = accountService.update(name, money, id);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable(value = "id") int id) {
        int t = accountService.delete(id);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postAccount(@RequestParam(value = "name") String name,
                              @RequestParam(value = "money") double money) {

        int t = accountService.add(name, money);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }

    }

    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    public void transfer() {
        accountService2.transfer();
    }

}