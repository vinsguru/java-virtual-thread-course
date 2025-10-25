package com.vinsguru.sec10;

import com.vinsguru.sec10.service.FlightPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.StructuredTaskScope;

/*
    Structured Concurrency / Structured Task Scope
    Joiner Strategy: Return the first successful response.
*/
public class Lec03AnySuccessful {

    private static final Logger log = LoggerFactory.getLogger(Lec03AnySuccessful.class);

    static void main(String[] args) {

        try(var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.anySuccessfulResultOrThrow())){

            // submit the subtasks
            var subtask1 = scope.fork(FlightPriceService::getDeltaAirfare);
            var subtask2 = scope.fork(FlightPriceService::getUnitedAirfare);

            // return the first successful response
            log.info("response: {}", scope.join());

            // check the state
            log.info("subtask1 state: {}", subtask1.state());
            log.info("subtask2 state: {}", subtask2.state());

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

}
