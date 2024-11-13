package com.learn.summer.aop.after;

import com.learn.summer.context.AnnotationConfigApplicationContext;
import com.learn.summer.io.PropertyResolver;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AfterProxyTest {
    @Test
    public void testAfter() {
        try(var ctx = new AnnotationConfigApplicationContext(
                AfterApplication.class, createPR())) {
            GreetingBean proxy = ctx.getBean(GreetingBean.class);
            assertEquals("Hello, Bob!", proxy.hello("Bob"));
            assertEquals("Morning, Alice!", proxy.morning("Alice"));
        }
    }

    PropertyResolver createPR() {
        Properties props = new Properties();
        PropertyResolver pr = new PropertyResolver(props);
        return pr;
    }
}
