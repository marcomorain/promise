package com.marcomorain;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


public class PromiseTest {
    @Test
    public void deliver() throws Exception {
        Promise<Integer> p = new Promise<Integer>();
        p.deliver(4);
        assertEquals(4, p.get().longValue());
    }

}