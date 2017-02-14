package com.marcomorain;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.security.cert.PKIXRevocationChecker.Option;
import java.util.Optional;



import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class PromiseTest {
    @Test
    public void deliver() throws Exception {
        Promise<Integer> p = Promise.newPromise();
        p.deliver(4);
        assertEquals(4, p.get().longValue());
    }

    <T> void deliverLater(final Promise<T> promise, final T value, final long millis) {
        Futures.submit(new Callable<Void>() {
            public Void call() throws Exception {
                Thread.sleep(millis);
                promise.deliver(value);
                return null;
            }
        });
    }

    @Test
    public void timeout() throws Exception {
        final Promise<Boolean> p = Promise.newPromise();
        final Optional<Boolean> result = p.get(2, TimeUnit.MILLISECONDS);
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void waiting() throws Exception {
        final Promise<Integer> p = Promise.newPromise();
        deliverLater(p, 32, 100);
        assertEquals(32, p.get().longValue());
    }
}
