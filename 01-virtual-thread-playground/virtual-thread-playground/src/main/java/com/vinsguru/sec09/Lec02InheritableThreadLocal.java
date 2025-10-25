package com.vinsguru.sec09;

import com.vinsguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.UUID;

/*
    A simple demo of inheritable thread local
 */
public class Lec02InheritableThreadLocal {

    private static final Logger log = LoggerFactory.getLogger(Lec02InheritableThreadLocal.class);
    private static final ThreadLocal<String> sessionTokenHolder = new InheritableThreadLocal<>();

    static void main(String[] args) {

        authFilter(() -> orderController());

        CommonUtils.sleep(Duration.ofSeconds(1));
    }

    // below code is just to demonstrate the workflow
    // WebFilter
    private static void authFilter(Runnable runnable){
        try{
            var token = authenticate();
            sessionTokenHolder.set(token);
            // request processing
            runnable.run();
        }finally {
            sessionTokenHolder.remove();
        }
    }

    // Security
    private static String authenticate(){
        var token = UUID.randomUUID().toString();
        log.info("token={}", token);
        return token;
    }

    // @Principal
    // POST /orders
    private static void orderController(){
        log.info("orderController: {}", sessionTokenHolder.get());
        orderService();
    }

    private static void orderService(){
        log.info("orderService: {}", sessionTokenHolder.get());
        Thread.ofVirtual().name("child-1").start(() -> callProductService());
        Thread.ofVirtual().name("child-2").start(() -> callInventoryService());
    }

    // This is a client to call product service
    private static void callProductService(){
        log.info("calling product-service with header. Authorization: Bearer {}", sessionTokenHolder.get());
    }

    // This is a client to call inventory service
    private static void callInventoryService(){
        log.info("calling inventory-service with header. Authorization: Bearer {}", sessionTokenHolder.get());
    }

}
