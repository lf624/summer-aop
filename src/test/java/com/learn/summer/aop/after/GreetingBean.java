package com.learn.summer.aop.after;

import com.learn.summer.annotation.Around;
import com.learn.summer.annotation.Component;

@Component
@Around("afterInvocationHandler")
public class GreetingBean {
    public String hello(String name) {
        return "Hello, " + name + ".";
    }

    public String morning(String name) {
        return "Morning, " + name + ".";
    }
}
