package com.learn.summer.aop;

import com.learn.summer.annotation.Around;
import com.learn.summer.context.ApplicationContextUtils;
import com.learn.summer.context.BeanDefinition;
import com.learn.summer.context.BeanPostProcessor;
import com.learn.summer.context.ConfigurableApplicationContext;
import com.learn.summer.exception.AopConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class AnnotationProxyBeanPostProcessor<A extends Annotation> implements BeanPostProcessor {
    final Logger logger = LoggerFactory.getLogger(getClass());

    Map<String, Object> originBeans = new HashMap<>();

    Class<A> annotationClass;

    public AnnotationProxyBeanPostProcessor() {
        this.annotationClass = getParameterizedType();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Class<?> beanClass = bean.getClass();
        A anno = beanClass.getAnnotation(annotationClass);
        if(anno != null) {
            String handlerName;
            try {
                handlerName = (String) anno.annotationType()
                        .getMethod("value").invoke(anno);
            }catch (ReflectiveOperationException e) {
                throw new AopConfigException("@%s must have value() returned String type."
                        .formatted(this.annotationClass.getSimpleName()), e);
            }
            Object proxy = createProxy(beanClass, bean, handlerName);
            originBeans.put(beanName, bean);
            return proxy;
        }else {
            return bean;
        }
    }

    Object createProxy(Class<?> beanClass, Object bean, String handlerName) {
        ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) ApplicationContextUtils.getRequiredApplicationContext();
        BeanDefinition def = ctx.findBeanDefinition(handlerName);
        if(def == null)
            throw new AopConfigException("proxy handler '%s' not found.".formatted(handlerName));
        Object handlerBean = def.getInstance();
        if(handlerBean == null)
            handlerBean = ctx.createBeanAsEarlySingleton(def);
        if(handlerBean instanceof InvocationHandler handler) {
            return ProxyResolver.getInstance().createProxy(bean, handler);
        }else {
            logger.debug("bean {} have handler type: {}", beanClass.getName(), handlerBean.getClass().getName());
            throw new AopConfigException("handler '%s' is not type of %s.".formatted(
                    handlerName, InvocationHandler.class.getName()));
        }
    }

    @Override
    public Object postProcessOnSetProperty(Object bean, String beanName) {
        Object origin = this.originBeans.get(beanName);
        return origin == null ? bean : origin;
    }

    // 得到 A 的具体类型
    @SuppressWarnings("unchecked")
    private Class<A> getParameterizedType() {
        Type type = getClass().getGenericSuperclass();
        if(!(type instanceof ParameterizedType))
            throw new IllegalArgumentException("Class " + getClass().getName() +
                    " does not have parameterized type.");
        ParameterizedType pt = (ParameterizedType) type;
        Type[] types = pt.getActualTypeArguments();
        if(types.length != 1)
            throw new IllegalArgumentException("Class " + getClass().getName() +
                    "have more than 1 parameterized types.");
        Type r = types[0];
        if(!(r instanceof Class<?>))
            throw new IllegalArgumentException("Class " + getClass().getName() +
                    " does not have parameterized type of class.");
        return (Class<A>) r;
    }
}
