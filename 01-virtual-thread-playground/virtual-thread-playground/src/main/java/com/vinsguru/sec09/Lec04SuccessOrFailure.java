package com.vinsguru.sec09;

import com.vinsguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadLocalRandom;

/*
    A simple demo of Structured Concurrency
 */
public class Lec04SuccessOrFailure {

    private static final Logger log = LoggerFactory.getLogger(Lec04SuccessOrFailure.class);

    public static void main(String[] args) {

        try(var taskScope = new StructuredTaskScope<>()){
            var subtask1 = taskScope.fork(Lec04SuccessOrFailure::getDeltaAirfare);
            var subtask2 = taskScope.fork(Lec04SuccessOrFailure::failingTask);

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
        CommonUtils.sleep("delta", Duration.ofSeconds(1));
        return "Delta-$" + random;
    }

    private static String getFrontierAirfare(){
        var random = ThreadLocalRandom.current().nextInt(100, 1000);
        log.info("frontier: {}", random);
        CommonUtils.sleep("frontier", Duration.ofSeconds(2));
        return "Frontier-$" + random;
    }

    private static String failingTask(){
        throw new RuntimeException("oops");
    }

}
