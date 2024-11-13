package com.learn.summer.aop.around;

import com.learn.summer.context.AnnotationConfigApplicationContext;
import com.learn.summer.io.PropertyResolver;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AroundTest {
    @Test
    public void testAround() {
        Properties props = new Properties();
        props.put("consumer.name", "me");
        try(var ctx = new AnnotationConfigApplicationContext(
                AroundApplication.class, new PropertyResolver(props))) {
            OriginBean bean = ctx.getBean(OriginBean.class);
            assertEquals("Hello, me!", bean.hello());
        }
    }
}
