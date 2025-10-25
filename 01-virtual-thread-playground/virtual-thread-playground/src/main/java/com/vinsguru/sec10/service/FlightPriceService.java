package com.vinsguru.sec10.service;

import com.vinsguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

public class FlightPriceService {

    private static final Logger log = LoggerFactory.getLogger(FlightPriceService.class);

    public static String getDeltaAirfare(){
        log.info("calling delta");
        CommonUtils.sleep("delta", Duration.ofSeconds(1));
        return "Delta-$" + ThreadLocalRandom.current().nextInt(100, 1000);
    }

    public static String getFrontierAirfare(){
        log.info("calling frontier");
        CommonUtils.sleep("frontier", Duration.ofSeconds(2));
        return "Frontier-$" + ThreadLocalRandom.current().nextInt(100, 1000);
    }

    public static String getUnitedAirfare(){
        log.info("calling united");
        throw new RuntimeException("503: Service Unavailable");
    }

}
