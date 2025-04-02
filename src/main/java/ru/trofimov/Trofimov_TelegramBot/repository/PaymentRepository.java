package ru.trofimov.Trofimov_TelegramBot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.trofimov.Trofimov_TelegramBot.entity.PaymentEntity;

/**
 * Репозиторий для оплаты
 */
@RepositoryRestResource(path = "payment")
public interface PaymentRepository extends CrudRepository<PaymentEntity, Long> {
}
