package ru.trofimov.Trofimov_TelegramBot.entity;

/**
 * Статусы формирования отчета
 */
public enum ReportStatus {
    CREATED("Создан"),
    COMPLETED("Завершен"),
    ERROR("Ошибка");

    private final String status;

    ReportStatus(String status) {
        this.status = status;
    }
}
