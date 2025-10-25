package com.vinsguru.sec07;

import com.vinsguru.sec07.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Gatherers;
import java.util.stream.IntStream;

// JDK 24+
// What about nested concurrent calls? - Check out https://www.udemy.com/course/java-stream-gatherers
public class Lec08MapConcurrent {

    private static final Logger log = LoggerFactory.getLogger(Lec08MapConcurrent.class);

    static void main() {

       var list = IntStream.rangeClosed(1, 50)
                .boxed()
                .gather(Gatherers.mapConcurrent(50, Lec08MapConcurrent::getProductName))
                .toList();

        log.info("size: {}", list.size());
    }

    // 3rd party service
    // contract: 3 concurrent calls are allowed
    private static String getProductName(int id){
        var product = Client.getProduct(id);
        log.info("{} => {}", id, product);
        return product;
    }

}
