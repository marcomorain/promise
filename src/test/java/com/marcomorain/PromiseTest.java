package com.marcomorain;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class PromiseTest {
    @Test
    public void deliver() throws Exception {
        Promise<Integer> p = Promise.newPromise();
        p.deliver(4);
        assertEquals(4, p.get().longValue());
    }

    @Test
    public void waiting() throws Exception {
        final Promise<Integer> p = Promise.newPromise();

        Thread t = new Thread() {
            public void run() {

                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                } finally {
                    p.deliver(32);
                }

            }
        };
        t.start();
        assertEquals(32, p.get().longValue());
    }

}
