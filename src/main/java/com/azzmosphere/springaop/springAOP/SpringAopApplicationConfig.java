package com.azzmosphere.springaop.springAOP;


import com.azzmosphere.springaop.springAOP.rest.HelloController;
import com.azzmosphere.springaop.springAOP.rest.HelloControllerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAopApplicationConfig {

    @Bean
    public HelloController helloController() {
        return new HelloControllerImpl();
    }
}
