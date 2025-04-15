package ru.trofimov.Trofimov_TelegramBot.controller;

import org.springframework.web.bind.annotation.*;
import ru.trofimov.Trofimov_TelegramBot.service.ReportService;

/**
 * Контроллер для формирования отчетов
 */
@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public Long createReport() {
        Long id = reportService.createReport();
        reportService.getReportContentAsync(id);
        return id;
    }

    @GetMapping("/{id}")
    public String getReport(@PathVariable Long id) {
        return reportService.getReportContent(id);
    }
}
