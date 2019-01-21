package com.phil.configuration;

public class HelloService {
    private String msg;

    public String sayHello() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}