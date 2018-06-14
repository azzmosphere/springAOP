package com.azzmosphere.springaop.springAOP;


import com.azzmosphere.springaop.springAOP.domain.Hello;
import com.azzmosphere.springaop.springAOP.interceptors.OnServiceCompleted;
import com.azzmosphere.springaop.springAOP.interceptors.TracingAspect;
import com.azzmosphere.springaop.springAOP.rest.HelloController;
import com.azzmosphere.springaop.springAOP.rest.HelloControllerImpl;
import com.azzmosphere.springaop.springAOP.service.HelloService;
import com.azzmosphere.springaop.springAOP.service.ServiceIface;
import lombok.extern.java.Log;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Log
@Configuration
public class SpringAopApplicationConfig {

    private final String[] advices = {
            "global_MethodBeforeAdvice",
            "global_onServiceCompleted"
    };

    /**
     * important note is that you should not add a interface for
     * advice routines.  This does some bad stuff, I have not idea why.
     *
     * @return
     */
    @Bean(name = "global_MethodBeforeAdvice")
    @Order(value = 0)
    public ProxyFactoryBean globalMethodBeforeAdvice() {
        log.info("creating global before advice");
        ProxyFactoryBean proxy = new ProxyFactoryBean();
        proxy.setTarget(new TracingAspect());

        return proxy;
    }

    @Bean(name = "global_onServiceCompleted")
    @Order(value = 0)
    public ProxyFactoryBean globalOnServiceCompleted() {
        log.info("creating service complete advice");
        ProxyFactoryBean proxy = new ProxyFactoryBean();
        proxy.setTarget(new OnServiceCompleted());

        return proxy;
    }


    /*
     * Creates HelloService Bean
     */
    @Bean
    @Order(value = 1)
    public ProxyFactoryBean helloServiceProxy() {
        log.info("creating hello proxy");
        return initProxy(new ProxyFactoryBean(), ServiceIface.class, new HelloService());
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
    @Order(value = 1)
    public ProxyFactoryBean helloController(ServiceIface<Hello, Hello> service) {
        log.info("creating controller");
        ProxyFactoryBean proxy = new ProxyFactoryBean();
        HelloControllerImpl helloController = new HelloControllerImpl();
        helloController.setService(service);
        return initProxy(proxy, HelloController.class, helloController);
    }

    /*
     * Inject standard advice into proxy object and perform boiler plate tasks
     * to set up proxy.
     */
    private ProxyFactoryBean initProxy(ProxyFactoryBean proxy, Class iface, Object target) {
        proxy.setInterfaces(iface);
        proxy.setTarget(target);

        /* by appending the interceptor name with '*' the prefix will look for all
          advices that match the name. However the problem here is that the beans
          have to exist before the they can take effect. Also I can't seem to get
          the bugger to work.  reference: https://docs.spring.io/spring-framework/docs/3.0.0.M4/reference/html/ch08s05.html
          section 8.5.6 it does this all in XML but it should work in Config class, so a little lost with
          it. The code talks about a ListableBeanFactory thing that each of the beans should be
          registered too, but I do not know how to do that. */
        // proxy.setInterceptorNames("global*");

        proxy.setInterceptorNames(advices);

        return proxy;
    }

}
