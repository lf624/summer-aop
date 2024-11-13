package com.learn.summer.aop.after;

import com.learn.summer.annotation.Component;
import com.learn.summer.aop.AfterInvocationHandlerAdapter;

import java.lang.reflect.Method;

@Component
public class AfterInvocationHandler extends AfterInvocationHandlerAdapter {
    @Override
    public Object after(Object proxy, Object returnValue, Method method, Object[] args) {
        if(returnValue instanceof String s) {
            return s.substring(0, s.length() - 1) + "!";
        }
        return returnValue;
    }
}
