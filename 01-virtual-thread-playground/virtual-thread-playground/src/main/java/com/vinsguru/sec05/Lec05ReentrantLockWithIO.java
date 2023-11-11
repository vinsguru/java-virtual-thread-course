package com.vinsguru.sec05;

import com.vinsguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
    Virtual Threads are indented for I/O tasks.
    To avoid pinning with synchronized, use ReentrantLock
 */
public class Lec05ReentrantLockWithIO {

    private static final Logger log = LoggerFactory.getLogger(Lec05ReentrantLockWithIO.class);
    private static final Lock lock = new ReentrantLock();

    static {
        System.setProperty("jdk.tracePinnedThreads", "short");
    }

    public static void main(String[] args) {

        Runnable runnable = () -> log.info("*** Test Message ***");

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

    private static void ioTask(){
        try{
            lock.lock();
            CommonUtils.sleep(Duration.ofSeconds(10));
        }catch (Exception e){
            log.error("error", e);
        }finally {
            lock.unlock();
        }
    }

}
