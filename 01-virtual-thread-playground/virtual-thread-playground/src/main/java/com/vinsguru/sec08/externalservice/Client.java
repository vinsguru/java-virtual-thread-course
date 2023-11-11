package com.vinsguru.sec08.externalservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);
    private static final String PRODUCT_REQUEST_FORMAT = "http://localhost:7070/sec01/product/%d";
    private static final String RATING_REQUEST_FORMAT = "http://localhost:7070/sec01/rating/%d";

    public static String getProduct(int id){
        return callExternalService(PRODUCT_REQUEST_FORMAT.formatted(id));
    }

    public static Integer getRating(int id){
        return Integer.parseInt(
                callExternalService(RATING_REQUEST_FORMAT.formatted(id))
        );
    }

    private static String callExternalService(String url){
        log.info("calling {}", url);
        try(var stream = URI.create(url).toURL().openStream()){ // stream should be closed
            return new String(stream.readAllBytes()); // responsive size is small
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
