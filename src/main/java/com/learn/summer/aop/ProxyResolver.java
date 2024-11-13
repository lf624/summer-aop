package com.learn.summer.aop;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;

public class ProxyResolver {
    final Logger logger = LoggerFactory.getLogger(getClass());

    final ByteBuddy byteBuddy = new ByteBuddy();

    private static ProxyResolver INSTANCE = null;

    // 只能通过 getInstance() 获取实例
    private ProxyResolver() {}

    public static ProxyResolver getInstance() {
        if(INSTANCE == null)
            INSTANCE = new ProxyResolver();
        return INSTANCE;
    }

    // 传入原始Bean、拦截器，返回代理后的实例
    @SuppressWarnings("unchecked")
    public <T> T createProxy(T bean, InvocationHandler handler) {
        Class<?> targetClass = bean.getClass();
        logger.atDebug().log("create proxy for bean {} @{}",
                targetClass.getName(), Integer.toHexString(bean.hashCode()));
        Class<?> proxyClass = this.byteBuddy
                // 子类用默认无参数构造方法
                .subclass(targetClass, ConstructorStrategy.Default.DEFAULT_CONSTRUCTOR)
                // 拦截所有 public 方法
                .method(ElementMatchers.isPublic())
                .intercept(InvocationHandlerAdapter.of(
                        // 新拦截器实例，将方法调用代理至原始 Bean
                        (proxy, method, args) -> handler.invoke(bean, method, args)))
                // 生成字节码
                .make()
                // 加载字节码
                .load(targetClass.getClassLoader())
                .getLoaded();
        // 创建 Proxy 实例
        Object proxy;
        try {
            proxy = proxyClass.getConstructor().newInstance();
        }catch (RuntimeException e) {
            throw e;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return (T)proxy;
    }
}
