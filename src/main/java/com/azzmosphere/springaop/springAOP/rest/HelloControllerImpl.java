package com.azzmosphere.springaop.springAOP.rest;

import com.azzmosphere.springaop.springAOP.domain.Hello;
import com.azzmosphere.springaop.springAOP.service.HelloServiceIface;

public final class HelloControllerImpl implements HelloController {

    private HelloServiceIface service;

    @Override
    public final void setService(HelloServiceIface service) {
        this.service = service;
    }

    @Override
    public final Hello execute() {
        return service.execute(Hello.builder().build());
    }
}
