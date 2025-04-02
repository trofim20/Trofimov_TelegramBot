package ru.trofimov.Trofimov_TelegramBot.repositoryCustom;

import ru.trofimov.Trofimov_TelegramBot.entity.DishEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.UserEntity;

import java.util.List;

/**
 * Кастомный репозиторий для написания CRITERIA
 */
public interface RepositoryCustom {
    List<UserEntity> findByName(String name);

    List<DishEntity> findByRestaurantId(Long restaurantId);
}
