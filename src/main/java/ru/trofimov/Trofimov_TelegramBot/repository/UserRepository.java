package ru.trofimov.Trofimov_TelegramBot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.trofimov.Trofimov_TelegramBot.entity.UserEntity;

import java.util.List;

/**
 * Репозиторий для пользователя
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    List<UserEntity> findByName(String username);
}
