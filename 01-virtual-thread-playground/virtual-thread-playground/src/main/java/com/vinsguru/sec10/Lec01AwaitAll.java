package com.vinsguru.sec10;

import com.vinsguru.sec10.service.FlightPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.StructuredTaskScope;

/*
    Structured Concurrency / Structured Task Scope
    Joiner Strategy: Wait for all subtasks to complete - irrespective of the completion status
*/
public class Lec01AwaitAll {

    private static final Logger log = LoggerFactory.getLogger(Lec01AwaitAll.class);

    static void main(String[] args) {

        try(var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.awaitAll())){

            // submit the subtasks
            var subtask1 = scope.fork(FlightPriceService::getDeltaAirfare);
            var subtask2 = scope.fork(FlightPriceService::getFrontierAirfare);

            // wait for all the subtasks to complete
            scope.join();

            // check the state
            log.info("subtask1 state: {}", subtask1.state());
            log.info("subtask2 state: {}", subtask2.state());

            // get the result
            log.info("subtask1 result: {}", subtask1.get());
            log.info("subtask2 result: {}", subtask2.get());


        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

}
