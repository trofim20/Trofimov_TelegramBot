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
    private final SimpleTaskRunner taskRunner;

    public ReportService(ReportRepository reportRepository,
                         UserRepository userRepository,
                         DishRepository dishRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
        this.taskRunner = new SimpleTaskRunner();
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
    private void generateReport(Long reportId) throws IOException {
        ReportEntity reportEntity = reportRepository.findById(reportId).orElseThrow();
        long startTime = System.currentTimeMillis();

        try {
            long[] userCountTime = {0};
            long[] dishesTime = {0};

            CompletableFuture<Long> userCountTask = CompletableFuture.supplyAsync(() -> {
                long start = System.currentTimeMillis();
                long count = userRepository.count();
                userCountTime[0] = System.currentTimeMillis() - start;
                return count;
            });

            CompletableFuture<String> dishesTask = CompletableFuture.supplyAsync(() -> {
                long start = System.currentTimeMillis();
                Iterable<DishEntity> dishes = dishRepository.findAll();
                StringBuilder dishList = new StringBuilder();
                for (DishEntity dishEntity : dishes) {
                    if (dishList.length() > 0) {
                        dishList.append(", ");
                    }
                    dishList.append(dishEntity.getName());
                }
                dishesTime[0] = System.currentTimeMillis() - start;
                return dishList.toString();
            });

            CompletableFuture.allOf(userCountTask, dishesTask).join();

            long userCount = userCountTask.get();
            String dishesList = dishesTask.get();

            String htmlTemplate = new String(Files.readAllBytes(
                    ResourceUtils.getFile("classpath:templates/report.html").toPath()
            ));

            long elapsed = System.currentTimeMillis() - startTime;

            String finalContent = htmlTemplate
                    .replace("${userCount}", String.valueOf(userCount))
                    .replace("${dishesList}", dishesList)
                    .replace("${userCountTime}", String.valueOf(userCountTime[0]))
                    .replace("${dishesTime}", String.valueOf(dishesTime[0]))
                    .replace("${totalTime}", String.valueOf(elapsed));

            reportEntity.setContent(finalContent);
            reportEntity.setStatus(ReportStatus.COMPLETED);
            reportRepository.save(reportEntity);

        } catch (Exception e) {
            reportEntity.setStatus(ReportStatus.ERROR);
            reportEntity.setContent("Произошла ошибка: " + e.getMessage());
            reportRepository.save(reportEntity);
            throw new RuntimeException(e);
        }
    }
}
