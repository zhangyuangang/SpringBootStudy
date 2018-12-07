package com.phil.control;

import com.phil.bean.ConfigBean;
import com.phil.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableConfigurationProperties({ConfigBean.class, User.class})
public class PropertiesController {

    @Value("${my.name}")
    private String name;
    @Value("${my.age}")
    private int age;

    @RequestMapping(value = "/miya")
    public String miya() {
        return name + ":" + age;
    }

    @Autowired
    ConfigBean configBean;

    @RequestMapping(value = "/lucy")
    public String lucy() {
        return configBean.getGreeting() + " >>>>" + configBean.getName() + " >>>>" + configBean.getUuid() + " >>>>" + configBean.getMax();
    }

    @Autowired
    User user;

    @RequestMapping(value = "/user")
    public String user() {
        return user.getName() + user.getAge();
    }

}