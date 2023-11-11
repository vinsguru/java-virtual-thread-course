package com.vinsguru.sec09;

import com.vinsguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.UUID;

/*
    A simple demo of ScopedValues - PREVIEW feature
 */
public class Lec03ScopedValues {

    private static final Logger log = LoggerFactory.getLogger(Lec03ScopedValues.class);
    private static final ScopedValue<String> SESSION_TOKEN = ScopedValue.newInstance();

    public static void main(String[] args) {

        log.info("isBound={}", SESSION_TOKEN.isBound());
        log.info("value={}", SESSION_TOKEN.orElse("default value"));

        Thread.ofVirtual().name("1").start( () -> processIncomingRequest());
        Thread.ofVirtual().name("2").start( () -> processIncomingRequest());

        CommonUtils.sleep(Duration.ofSeconds(1));

    }

    // ** ---- below code is just to demonstrate the workflow --- **

    private static void processIncomingRequest(){
        var token = authenticate();

        ScopedValue.runWhere(SESSION_TOKEN, token, () -> controller());

        //controller();
    }

    private static String authenticate(){
        var token = UUID.randomUUID().toString();
        log.info("token={}", token);
        return token;
    }

    // @Principal
    private static void controller(){
        log.info("controller: {}", SESSION_TOKEN.get());
        service();
    }

    private static void service(){
        log.info("service: {}", SESSION_TOKEN.get());
        ScopedValue.runWhere(SESSION_TOKEN, "new-token-" + Thread.currentThread().getName(), () -> callExternalService());
    }

    // This is a client to call external service
    private static void callExternalService(){
        log.info("preparing HTTP request with token: {}", SESSION_TOKEN.get());
    }

}
