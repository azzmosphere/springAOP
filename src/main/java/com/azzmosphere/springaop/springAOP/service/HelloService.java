package com.azzmosphere.springaop.springAOP.service;

import com.azzmosphere.springaop.springAOP.domain.Hello;

public final class HelloService implements HelloServiceIface {
    @Override
    public final Hello execute(Hello request) {
        return Hello.builder().name("Hello World").build();
    }
}
