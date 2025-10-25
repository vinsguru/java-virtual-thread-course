package com.vinsguru.sec10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.StructuredTaskScope;

/*
    StructuredTaskScope propagates ScopedValues automatically
*/
public class Lec04ScopedValuePropagation {

    private static final Logger log = LoggerFactory.getLogger(Lec04ScopedValuePropagation.class);
    private static final ScopedValue<String> SESSION_TOKEN = ScopedValue.newInstance();

    static void main(String[] args) {

        authFilter(() -> orderController());

    }

    // below code is just to demonstrate the workflow
    // WebFilter
    private static void authFilter(Runnable runnable) {
        ScopedValue.where(SESSION_TOKEN, authenticate())
                   .run(runnable);
    }

    // Security
    private static String authenticate() {
        var token = UUID.randomUUID().toString();
        log.info("token={}", token);
        return token;
    }

    // @Principal
    // POST /orders
    private static void orderController() {
        log.info("orderController: {}", SESSION_TOKEN.get());
        orderService();
    }

    private static void orderService() {
        log.info("orderService: {}", SESSION_TOKEN.get());
        try(var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.allSuccessfulOrThrow())){
            scope.fork(Lec04ScopedValuePropagation::callProductService);
            scope.fork(Lec04ScopedValuePropagation::callInventoryService);

            scope.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // This is a client to call product service
    private static void callProductService() {
        log.info("calling product-service with header. Authorization: Bearer {}", SESSION_TOKEN.get());
    }

    // This is a client to call inventory service
    private static void callInventoryService() {
        log.info("calling inventory-service with header. Authorization: Bearer {}", SESSION_TOKEN.get());
    }

}
