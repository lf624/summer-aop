package com.learn.summer.aop.metric;

import com.learn.summer.annotation.Bean;
import com.learn.summer.annotation.ComponentScan;
import com.learn.summer.annotation.Configuration;

@Configuration
@ComponentScan
public class MetricApplication {
    @Bean
    MetricProxyBeanPostProcessor createMetricProcessor() {
        return new MetricProxyBeanPostProcessor();
    }
}
