package com.learn.summer.aop.before;

import com.learn.summer.annotation.Bean;
import com.learn.summer.annotation.ComponentScan;
import com.learn.summer.annotation.Configuration;
import com.learn.summer.aop.AroundProxyBeanPostProcessor;

@Configuration
@ComponentScan
public class BeforeApplication {
    @Bean
    AroundProxyBeanPostProcessor createAop() {
        return new AroundProxyBeanPostProcessor();
    }
}
