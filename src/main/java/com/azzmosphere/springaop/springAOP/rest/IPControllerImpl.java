package com.azzmosphere.springaop.springAOP.rest;

import com.azzmosphere.springaop.springAOP.domain.IP;
import com.azzmosphere.springaop.springAOP.service.IPServiceIface;

public final class IPControllerImpl implements IPController {

    private IPServiceIface service;

    @Override
    public final void setService(IPServiceIface service) {
        this.service = service;
    }

    @Override
    public final IP execute() {
        return service.execute(IP.builder().build());
    }
}
