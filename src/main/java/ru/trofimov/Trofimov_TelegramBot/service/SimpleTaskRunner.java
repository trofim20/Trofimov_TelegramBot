package ru.trofimov.Trofimov_TelegramBot.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SimpleTaskRunner {

    public <T> SimpleTaskResult<T> runTask(Callable<T> task) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        long startTime = System.currentTimeMillis();
        T result = null;

        try {
            Future<T> future = executor.submit(task);
            result = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        long executionTime = System.currentTimeMillis() - startTime;
        return new SimpleTaskResult<>(executionTime, result);
    }
}