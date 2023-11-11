package com.vinsguru.sec09;

import com.vinsguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadLocalRandom;

/*
    Scoped Values inheritance using Structured Task Scope
 */
public class Lec07ScopedValuesWithStructuredTaskScope {

    private static final Logger log = LoggerFactory.getLogger(Lec07ScopedValuesWithStructuredTaskScope.class);
    private static final ScopedValue<String> SESSION_TOKEN = ScopedValue.newInstance();

    public static void main(String[] args) {

        ScopedValue.runWhere(SESSION_TOKEN, "token-123", Lec07ScopedValuesWithStructuredTaskScope::task);

    }

    private static void task(){
        try(var taskScope = new StructuredTaskScope<>()){

            log.info("token: {}", SESSION_TOKEN.get());

            var subtask1 = taskScope.fork(Lec07ScopedValuesWithStructuredTaskScope::getDeltaAirfare);
            var subtask2 = taskScope.fork(Lec07ScopedValuesWithStructuredTaskScope::getFrontierAirfare);

            taskScope.join();

            log.info("subtask1 state: {}", subtask1.state());
            log.info("subtask2 state: {}", subtask2.state());

            log.info("subtask1 result: {}", subtask1.get());
            log.info("subtask2 result: {}", subtask2.get());

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private static String getDeltaAirfare(){
        var random = ThreadLocalRandom.current().nextInt(100, 1000);
        log.info("delta: {}", random);
        log.info("token: {}", SESSION_TOKEN.get());
        CommonUtils.sleep("delta", Duration.ofSeconds(1));
        return "Delta-$" + random;
    }

    private static String getFrontierAirfare(){
        var random = ThreadLocalRandom.current().nextInt(100, 1000);
        log.info("frontier: {}", random);
        log.info("token: {}", SESSION_TOKEN.get());
        CommonUtils.sleep("frontier", Duration.ofSeconds(2));
        return "Frontier-$" + random;
    }

    private static String failingTask(){
        throw new RuntimeException("oops");
    }

}
