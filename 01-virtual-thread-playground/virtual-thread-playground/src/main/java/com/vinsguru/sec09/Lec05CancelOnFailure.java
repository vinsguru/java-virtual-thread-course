package com.vinsguru.sec09;

import com.vinsguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadLocalRandom;

/*
    A simple demo of Structured Concurrency where we want to cancel all the running subtasks when one of the subtasks fails
 */
public class Lec05CancelOnFailure {

    private static final Logger log = LoggerFactory.getLogger(Lec05CancelOnFailure.class);

    public static void main(String[] args) {

        try(var taskScope = new StructuredTaskScope.ShutdownOnFailure()){
            var subtask1 = taskScope.fork(Lec05CancelOnFailure::getDeltaAirfare);
            var subtask2 = taskScope.fork(Lec05CancelOnFailure::failingTask);

            taskScope.join();
            taskScope.throwIfFailed(ex -> new RuntimeException("something went wrong"));

            log.info("subtask1 state: {}", subtask1.state());
            log.info("subtask2 state: {}", subtask2.state());

//            log.info("subtask1 result: {}", subtask1.get());
//            log.info("subtask2 result: {}", subtask2.get());

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
