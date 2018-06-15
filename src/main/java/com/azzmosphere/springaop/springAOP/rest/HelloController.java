package com.azzmosphere.springaop.springAOP.rest;

import com.azzmosphere.springaop.springAOP.domain.Hello;
import com.azzmosphere.springaop.springAOP.service.HelloServiceIface;
import com.azzmosphere.springaop.springAOP.service.ServiceIface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public interface HelloController  {
    void setService(HelloServiceIface service);

    @RequestMapping(value = "/hello")
    Hello execute();
}
