package com.vinsguru.sec03;

import com.vinsguru.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/*
    A simple demo to show that Virtual threads are NOT faster compared to Platform.
 */
public class CPUTaskDemo {

    private static final Logger log = LoggerFactory.getLogger(CPUTaskDemo.class);
    private static final int TASKS_COUNT = 3 * Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        log.info("Tasks Count: {}", TASKS_COUNT);
        for (int i = 0; i < 3; i++) {
            var totalTimeTaken = CommonUtils.timer(() -> demo(Thread.ofVirtual()));
            log.info("Total time taken with virtual {} ms", totalTimeTaken);
            totalTimeTaken = CommonUtils.timer(() -> demo(Thread.ofPlatform()));
            log.info("Total time taken with platform {} ms", totalTimeTaken);
        }

    }

    private static void demo(Thread.Builder builder){
        var latch = new CountDownLatch(TASKS_COUNT);
        for (int i = 1; i <= TASKS_COUNT; i++) {
            builder.start(() ->{
                Task.cpuIntensive(45);
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
