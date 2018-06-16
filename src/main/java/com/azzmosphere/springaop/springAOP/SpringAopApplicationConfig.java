package com.azzmosphere.springaop.springAOP;


import com.azzmosphere.springaop.springAOP.domain.Hello;
import com.azzmosphere.springaop.springAOP.domain.IP;
import com.azzmosphere.springaop.springAOP.interceptors.OnServiceCompleted;
import com.azzmosphere.springaop.springAOP.interceptors.TracingAspect;
import com.azzmosphere.springaop.springAOP.rest.*;
import com.azzmosphere.springaop.springAOP.service.*;
import lombok.extern.java.Log;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Log
@Configuration
public class SpringAopApplicationConfig {

    private final String[] advices = {
            "global_MethodBeforeAdvice",
            "global_onServiceCompleted"
    };

    private @Autowired ListableBeanFactory factory;

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
    @Order(value = 1)
    public ProxyFactoryBean globalOnServiceCompleted() {
        log.info("creating service complete advice");
        ProxyFactoryBean proxy = new ProxyFactoryBean();
        proxy.setTarget(new OnServiceCompleted());

        return proxy;
    }


    @Bean
    @Resource
    @Order(value = 2)
    public RestOperations restTemplate() {
        log.info("creating rest template");
        return new RestTemplate();
    }

    /*
     * Creates HelloService Bean
     */
    @Bean(name="helloService")
    @Resource
    @Order(value = 3)
    public ProxyFactoryBean helloServiceProxy() {
        log.info("creating hello proxy");
        return initProxy(new ProxyFactoryBean(), ServiceIface.class, new HelloService());
    }

    @Bean(name="ipService")
    @Resource
    @Autowired
    @Order(value = 4)
    public ProxyFactoryBean ipService(@Qualifier("restTemplate") RestOperations restOperations) {
        log.info("creating IP service");
        IPService ipService = new IPService();
        ipService.init(restOperations);
        return initProxy(new ProxyFactoryBean(), ServiceIface.class, ipService);
    }

    /**
     *
     * Proxy for the Hello Service Rest Controller.
     *
     * @return
     */
    @Bean
    @Autowired
    @Order(value = 5)
    public ProxyFactoryBean helloController(@Qualifier("helloService") HelloServiceIface service) {
        log.info("creating controller");
        ProxyFactoryBean proxy = new ProxyFactoryBean();
        HelloControllerImpl helloController = new HelloControllerImpl();
        helloController.setService(service);
        //injectService(helloController, "HelloService");

        return initProxy(proxy, HelloController.class, helloController);
    }

    @Bean
    @Autowired
    @Order(value = 6)
    public ProxyFactoryBean ipController(@Qualifier("ipService") IPServiceIface service) {
        log.info("creating ip controller");
        ProxyFactoryBean proxy = new ProxyFactoryBean();
        IPControllerImpl ipController = new IPControllerImpl();
        ipController.setService(service);
        //injectService(ipController, "IPService");
        return initProxy(proxy, IPController.class, ipController);
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

//    private void injectService(ControllerBase controller, String beanName) {
//            Object service = factory.getBean(beanName);
//           controller.setService((ServiceIface) factory.getBean(beanName));
//    }

}
