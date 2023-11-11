package com.vinsguru.sec05;

import com.vinsguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/*
    Demo: Virtual Thread Pinning
 */
public class Lec03SynchronizationWithIO {

    private static final Logger log = LoggerFactory.getLogger(Lec03SynchronizationWithIO.class);

    // Use this to check if virtual threads are getting pinned in your application
    static {
        System.setProperty("jdk.tracePinnedThreads", "short");
    }

    public static void main(String[] args) {

        Runnable runnable = () -> log.info("*** Test Message ***");

// We will not see this issue wit Platform threads
//        demo(Thread.ofPlatform());
//        Thread.ofPlatform().start(runnable);

        demo(Thread.ofVirtual());
        Thread.ofVirtual().start(runnable);

        CommonUtils.sleep(Duration.ofSeconds(15));

    }

    private static void demo(Thread.Builder builder){
        for (int i = 0; i < 50; i++) {
           builder.start(() -> {
                log.info("Task started. {}", Thread.currentThread());
                ioTask();
                log.info("Task ended. {}", Thread.currentThread());
            });
        }
    }

    private static synchronized void ioTask(){
        CommonUtils.sleep(Duration.ofSeconds(10));
    }

}
