package com.azzmosphere.springaop.springAOP.rest;

import com.azzmosphere.springaop.springAOP.domain.Hello;

public final class HelloControllerImpl implements HelloController {

    @Override
    public final Hello execute() {
        return Hello.builder().name("Hello World").build();
    }
}
