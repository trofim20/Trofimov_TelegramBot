package ru.trofimov.Trofimov_TelegramBot.service;

/**
 * Класс для хранения результатов выполнения задачи
 */
public class SimpleTaskResult<T> {
    private final long executionTime;
    private final T result;

    public SimpleTaskResult(T result) {
        this.executionTime = 0;
        this.result = result;
    }

    public SimpleTaskResult(long executionTime, T result) {
        this.executionTime = executionTime;
        this.result = result;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public T getResult() {
        return result;
    }
}
