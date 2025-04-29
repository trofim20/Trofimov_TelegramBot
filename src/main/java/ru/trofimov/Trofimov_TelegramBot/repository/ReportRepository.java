package ru.trofimov.Trofimov_TelegramBot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.trofimov.Trofimov_TelegramBot.entity.ReportEntity;

/**
 * Репозиторий отчетов
 */
@Repository
public interface ReportRepository extends CrudRepository<ReportEntity, Long> {
}
