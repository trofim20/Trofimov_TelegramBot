package ru.trofimov.Trofimov_TelegramBot.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ru.trofimov.Trofimov_TelegramBot.entity.DishEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.ReportEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.ReportStatus;
import ru.trofimov.Trofimov_TelegramBot.repository.DishRepository;
import ru.trofimov.Trofimov_TelegramBot.repository.ReportRepository;
import ru.trofimov.Trofimov_TelegramBot.repository.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;

/**
 * Сервис для работы с отчетами
 */
@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;

    public ReportService(ReportRepository reportRepository,
                         UserRepository userRepository,
                         DishRepository dishRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
    }

    /**
     * Создает новый отчет в системе
     */
    public Long createReport() {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setStatus(ReportStatus.CREATED);
        reportEntity.setContent("Создается отчет");
        reportEntity = reportRepository.save(reportEntity);
        return reportEntity.getId();
    }

    /**
     * Получает содержимое отчета по его идентификатору
     */
    public String getReportContent(Long reportId) {
        return reportRepository.findById(reportId)
                .map(reportEntity -> {
                    if (reportEntity.getStatus() == ReportStatus.CREATED) {
                        return "Отчет формируется";
                    } else if (reportEntity.getStatus() == ReportStatus.ERROR) {
                        return "Произошла ошибка";
                    }
                    return reportEntity.getContent();
                }).orElse("Отчет не найден");
    }

    /**
     * Запускает асинхронный процесс генерации отчета
     */
    public CompletableFuture<Void> getReportContentAsync(Long reportId) {
        return CompletableFuture.runAsync(() -> {
            try {
                generateReport(reportId);
            } catch (Exception e) {
                ReportEntity reportEntity = reportRepository.findById(reportId).orElseThrow();
                reportEntity.setStatus(ReportStatus.ERROR);
                reportEntity.setContent(e.getMessage());
                reportRepository.save(reportEntity);
            }
        });
    }

    /**
     * Генерирует отчет, включая статистику пользователей и список блюд
     */
    private void generateReport(Long reportId) throws InterruptedException, IOException {
        ReportEntity reportEntity = reportRepository.findById(reportId).orElseThrow();

        long startTime = System.currentTimeMillis();
        long[] userCountTime = {0};
        long[] dishesTime = {0};

        Thread userCountThread = new Thread(() -> {
            long start = System.currentTimeMillis();
            long count = userRepository.count();
            userCountTime[0] = System.currentTimeMillis() - start;
            reportEntity.setContent("Количество пользователей: " + count
                    + " время выполнения: " + userCountTime[0] + " ms");
            reportRepository.save(reportEntity);
        });

        Thread dishesThread = new Thread(() -> {
            long start = System.currentTimeMillis();
            Iterable<DishEntity> count = dishRepository.findAll();
            StringBuilder dishList = new StringBuilder();
            for (DishEntity dishEntity : count) {
                dishList.append(dishEntity.getName()).append("\n");
            }
            dishesTime[0] = System.currentTimeMillis() - start;
            reportRepository.save(reportEntity);
        });

        userCountThread.start();
        dishesThread.start();

        userCountThread.join();
        dishesThread.join();

        long elapsed = System.currentTimeMillis() - startTime;

        String htmlTemplate = new String(Files.readAllBytes(
                ResourceUtils.getFile("classpath:templates/report.html").toPath()
        ));

        StringBuilder dishesListBuilder = new StringBuilder();
        for (DishEntity dish : dishRepository.findAll()) {
            if (dishesListBuilder.length() > 0) {
                dishesListBuilder.append(", ");
            }
            dishesListBuilder.append(dish.getName());
        }
        String dishesList = dishesListBuilder.toString();

        String finalContent = htmlTemplate
                .replace("${userCount}", String.valueOf(userRepository.count()))
                .replace("${dishesList}", dishesList)
                .replace("${userCountTime}", String.valueOf(userCountTime[0]))
                .replace("${dishesTime}", String.valueOf(dishesTime[0]))
                .replace("${totalTime}", String.valueOf(elapsed));

        reportEntity.setContent(finalContent);
        reportEntity.setStatus(ReportStatus.COMPLETED);
        reportRepository.save(reportEntity);
    }
}
