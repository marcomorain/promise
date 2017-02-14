package com.marcomorain;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.Callable;

public class Futures {

    private final static AtomicLong threadCounter = new AtomicLong(0);
    private final static ExecutorService executor = Executors.newCachedThreadPool(
        new ThreadFactory() {
		public Thread newThread(Runnable runnable) {
			Thread thread = new Thread(runnable);
			thread.setName(String.format("future-thread-pool-%d", threadCounter.getAndIncrement()));
			return thread;
		}
	});

    public static <T> Future<T> submit(Callable<T> task) {
		return executor.submit(task);
	}

}
