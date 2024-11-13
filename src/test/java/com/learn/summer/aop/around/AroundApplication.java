package com.learn.summer.aop.around;

import com.learn.summer.annotation.Bean;
import com.learn.summer.annotation.ComponentScan;
import com.learn.summer.annotation.Configuration;
import com.learn.summer.aop.AroundProxyBeanPostProcessor;

@Configuration
@ComponentScan
public class AroundApplication {
    @Bean
    AroundProxyBeanPostProcessor createAroundProcessor() {
        return new AroundProxyBeanPostProcessor();
    }
}
