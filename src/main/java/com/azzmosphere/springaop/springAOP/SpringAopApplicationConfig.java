package com.azzmosphere.springaop.springAOP;


import com.azzmosphere.springaop.springAOP.domain.Hello;
import com.azzmosphere.springaop.springAOP.interceptors.OnServiceCompleted;
import com.azzmosphere.springaop.springAOP.interceptors.TracingAspect;
import com.azzmosphere.springaop.springAOP.rest.HelloController;
import com.azzmosphere.springaop.springAOP.rest.HelloControllerImpl;
import com.azzmosphere.springaop.springAOP.service.HelloService;
import com.azzmosphere.springaop.springAOP.service.ServiceIface;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAopApplicationConfig {

    @Bean(name = "methodBeforeAdvice")
    MethodBeforeAdvice methodBeforeAdvice() {
        return new TracingAspect();
    }

    @Bean(name = "onServiceCompleted")
    OnServiceCompleted onServiceCompleted() {
        return new OnServiceCompleted();
    }


    /*
     * Creates HelloService Bean
     */
    @Bean
    ProxyFactoryBean helloServiceProxy() {
        ProxyFactoryBean proxy = new ProxyFactoryBean();

        proxy.setInterfaces(ServiceIface.class);
        proxy.setTarget(new HelloService());
        proxy.setInterceptorNames("methodBeforeAdvice",
                "onServiceCompleted");
        return proxy;
    }

    /**
     *
     * Proxy for the Hello Service Rest Controller.
     *
     * @param service
     * @return
     */
    @Bean
    @Autowired
    ProxyFactoryBean helloController(ServiceIface<Hello, Hello> service) {
        ProxyFactoryBean proxy = new ProxyFactoryBean();
        HelloControllerImpl helloController = new HelloControllerImpl();

        helloController.setService(service);
        proxy.setInterfaces(HelloController.class);
        proxy.setTarget(helloController);
        proxy.setInterceptorNames("methodBeforeAdvice",
                "onServiceCompleted");

        return proxy;
    }
}
