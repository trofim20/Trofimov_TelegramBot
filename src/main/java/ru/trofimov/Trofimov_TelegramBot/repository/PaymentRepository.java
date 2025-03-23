package ru.trofimov.Trofimov_TelegramBot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.trofimov.Trofimov_TelegramBot.entity.PaymentEntity;

/**
 * Репозиторий для оплаты
 */
public interface PaymentRepository extends CrudRepository<PaymentEntity, Long> {
}
