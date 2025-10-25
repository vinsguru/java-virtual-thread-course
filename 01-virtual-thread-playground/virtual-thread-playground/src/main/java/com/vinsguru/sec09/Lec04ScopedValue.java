package com.vinsguru.sec09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// JDK 25+
public class Lec04ScopedValue {

    private static final Logger log = LoggerFactory.getLogger(Lec04ScopedValue.class);
    private static final ScopedValue<String> SESSION_TOKEN = ScopedValue.newInstance();

    static void main() {
        ScopedValue.where(SESSION_TOKEN, "session-1")
                .run(() -> checkBinding());
        checkBinding();
    }

    // check if ScopedValue is bound
    private static void checkBinding() {
        log.info("is bound? : {}", SESSION_TOKEN.isBound());
        log.info("value     : {}", SESSION_TOKEN.orElse("dummy token"));
    }

}
