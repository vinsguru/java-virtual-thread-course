package com.vinsguru.sec06;

import com.vinsguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/*
    Quick demo to show few useful thread methods
*/
public class Lec02ThreadMethodsDemo {


    private static final Logger log = LoggerFactory.getLogger(Lec02ThreadMethodsDemo.class);

    public static void main(String[] args) throws InterruptedException {
        join();
        CommonUtils.sleep(Duration.ofSeconds(1));
    }

    /*
        To check if a thread is virtual
     */
    private static void isVirtual() {
        var t1 = Thread.ofVirtual().start(() -> CommonUtils.sleep(Duration.ofSeconds(2)));
        var t2 = Thread.ofPlatform().start(() -> CommonUtils.sleep(Duration.ofSeconds(2)));
        log.info("Is t1 virtual: {}", t1.isVirtual());
        log.info("Is t2 virtual: {}", t2.isVirtual());
        log.info("Is current thread virtual: {}", Thread.currentThread().isVirtual());
    }


    /*
        To offload multiple time-consuming I/O calls to Virtual threads and wait for them to complete
        Note: We can do better in the actual application which we will develop later.
        It is a simple thread.join() demo
     */
    private static void join() throws InterruptedException {
        var t1 = Thread.ofVirtual().start(() -> {
            CommonUtils.sleep(Duration.ofSeconds(2));
            log.info("called product service");
        });
        var t2 = Thread.ofVirtual().start(() -> {
            CommonUtils.sleep(Duration.ofSeconds(2));
            log.info("called pricing service");
        });
        t1.join();
        t2.join();
    }

    /*
        To interrupt / stop the thread execution
        in some cases, java will throw interrupted exception, IO exception, socket exception etc

        We can also check if the current thread is interrupted
        Thread.currentThread().isInterrupted() - returns a boolean

        while(!Thread.currentThread().isInterrupted()){
            continue the work
            ...
            ...
        }
     */
    private static void interrupt() {
        var t1 = Thread.ofVirtual().start(() -> {
            CommonUtils.sleep(Duration.ofSeconds(2));
            log.info("called product service");
        });
        log.info("is t1 interrupted: {}", t1.isInterrupted());
        t1.interrupt();
        log.info("is t1 interrupted: {}", t1.isInterrupted());
    }

}
