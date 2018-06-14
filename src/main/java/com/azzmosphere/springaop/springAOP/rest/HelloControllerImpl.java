package com.azzmosphere.springaop.springAOP.rest;

import com.azzmosphere.springaop.springAOP.domain.Hello;
import com.azzmosphere.springaop.springAOP.service.ServiceIface;

public final class HelloControllerImpl implements HelloController {

    private ServiceIface<Hello, Hello> service;

    @Override
    public final void setService(ServiceIface<Hello, Hello> service) {
        this.service = service;
    }

    @Override
    public final Hello execute() {
        return service.execute(Hello.builder().build());
    }
}
