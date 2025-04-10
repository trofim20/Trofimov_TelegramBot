package ru.trofimov.Trofimov_TelegramBot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.trofimov.Trofimov_TelegramBot.entity.UserEntity;

import java.util.List;

/**
 * Репозиторий для пользователя
 */
@RepositoryRestResource(path = "user")
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    List<UserEntity> findByName(String username);
}
