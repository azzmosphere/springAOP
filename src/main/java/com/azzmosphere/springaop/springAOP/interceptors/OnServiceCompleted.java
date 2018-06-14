package com.azzmosphere.springaop.springAOP.interceptors;

import lombok.extern.java.Log;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

@Log
public final class OnServiceCompleted implements AfterReturningAdvice {
    @Override
    public final void afterReturning(@Nullable Object returnValue, Method method, Object[] args, @Nullable Object target) throws Throwable {
        log.info("method " + method.getDeclaringClass() + "." + method.getName() + " with return value " + returnValue.toString());
    }
}
