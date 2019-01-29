package com.wrpower.pjc_project.controller;

import com.wrpower.pjc_project.entity.UserInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @RequestMapping("/testController")
    public String testController() {
        return " hello Spring boot";
    }

    @RequestMapping("/testUserInfo")
    public String testUserInfo(@RequestBody UserInfo userInfo) {
        return userInfo.toString();
    }
}
