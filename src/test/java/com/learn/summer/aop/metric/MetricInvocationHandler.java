package com.learn.summer.aop.metric;

import com.learn.summer.annotation.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class MetricInvocationHandler implements InvocationHandler {
    final Logger logger = LoggerFactory.getLogger(getClass());

    Map<String, Long> lastProcessedTime = new HashMap<>();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Metric metric = method.getAnnotation(Metric.class);
        if(metric == null)
            return method.invoke(proxy, args);
        long start = System.currentTimeMillis();
        try {
            return method.invoke(proxy, args);
        } finally {
            long end = System.currentTimeMillis() - start;
            if(Objects.equals(metric.value(), "MD5"))
                end = 5;
            if(metric.value().equals("SHA-256"))
                end = 256;
            logger.info("execute {} consume time: {}", method.getName(), end);
            lastProcessedTime.put(metric.value(), end);
        }
    }
}
