package ru.trofimov.Trofimov_TelegramBot.exception;

public class DishNotFound extends RuntimeException {
    public DishNotFound(String message) {
        super(message);
    }
}