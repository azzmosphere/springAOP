package com.azzmosphere.springaop.springAOP.rest;

import com.azzmosphere.springaop.springAOP.domain.Hello;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface HelloController {

    @RequestMapping(value = "/hello")
    Hello execute();
}
