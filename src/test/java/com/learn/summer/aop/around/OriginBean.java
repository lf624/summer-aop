package com.learn.summer.aop.around;

import com.learn.summer.annotation.Around;
import com.learn.summer.annotation.Component;
import com.learn.summer.annotation.Value;

@Component
@Around("aroundInvocationHandler")
public class OriginBean {
    @Value("${consumer.name}")
    public String name;

    @Polite
    public String hello() {
        return "Hello, " + name + ".";
    }

    public String morning() {
        return "Morning, " + name + ".";
    }
}
