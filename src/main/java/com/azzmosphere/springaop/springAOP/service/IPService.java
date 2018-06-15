package com.azzmosphere.springaop.springAOP.service;

import com.azzmosphere.springaop.springAOP.domain.IP;
import org.springframework.web.client.RestOperations;

public final class IPService implements IPServiceIface {

    private RestOperations restOperations;
    private final String requestURL = "http://ip.jsontest.com/";

    public final void init(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @Override
    public final IP execute(IP request) {

        IP ip = restOperations.getForObject(requestURL, IP.class);

        // Note the execute method can be used here if POST or other types
        // of request are needed.

        return ip;
    }
}
