package com.learn.summer.aop.before;

import com.learn.summer.context.AnnotationConfigApplicationContext;
import com.learn.summer.io.PropertyResolver;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BeforeProxyTest {
    @Test
    public void testBefore() {
        try(var ctx = new AnnotationConfigApplicationContext(
                BeforeApplication.class, createPR())) {
            BusinessBean proxy = ctx.getBean(BusinessBean.class);
            assertEquals("Hello, Bob.", proxy.hello("Bob"));
            assertEquals("Morning, Alice.", proxy.morning("Alice"));
        }
    }

    PropertyResolver createPR() {
        Properties props = new Properties();
        PropertyResolver pr = new PropertyResolver(props);
        return pr;
    }
}
