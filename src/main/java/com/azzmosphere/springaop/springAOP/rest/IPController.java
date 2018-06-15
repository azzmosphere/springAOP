package com.azzmosphere.springaop.springAOP.rest;

import com.azzmosphere.springaop.springAOP.domain.IP;
import com.azzmosphere.springaop.springAOP.service.IPServiceIface;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface IPController  {

    void setService(IPServiceIface service);

    @RequestMapping(value = "/ip")
    IP execute();
}
