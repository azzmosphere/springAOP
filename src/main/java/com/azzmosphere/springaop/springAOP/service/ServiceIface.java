package com.azzmosphere.springaop.springAOP.service;

public interface ServiceIface<T, R> {
    R execute(T request);
}
