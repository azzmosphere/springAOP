package com.azzmosphere.springaop.springAOP.interceptors;


import lombok.extern.java.Log;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

@Log
public final class TracingAspect implements MethodBeforeAdvice {

    @Override
    public final void before(Method method, Object[] args, @Nullable Object target) throws Throwable {
        log.info("about to execute " + method.getDeclaringClass().getCanonicalName() + "."  + method.getName());
    }
}
