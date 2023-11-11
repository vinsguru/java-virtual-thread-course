package com.vinsguru.sec07.concurrencylimit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.*;

public class ConcurrencyLimiter implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(ConcurrencyLimiter.class);

    private final ExecutorService executor;
    private final Semaphore semaphore;
    private final Queue<Callable<?>> queue;

    public ConcurrencyLimiter(ExecutorService executor, int limit) {
        this.executor = executor;
        this.semaphore = new Semaphore(limit);
        this.queue = new ConcurrentLinkedQueue<>();
    }

    public <T> Future<T> submit(Callable<T> callable){
        this.queue.add(callable);
        return executor.submit(() -> executeTask() );
    }

    private <T> T executeTask(){
        try{
            semaphore.acquire();
            return (T) this.queue.poll().call();
        }catch (Exception e){
            log.error("error", e);
        }finally {
            semaphore.release();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        this.executor.close();
    }
}
