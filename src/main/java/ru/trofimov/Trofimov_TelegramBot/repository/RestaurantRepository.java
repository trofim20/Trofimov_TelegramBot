package ru.trofimov.Trofimov_TelegramBot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.trofimov.Trofimov_TelegramBot.entity.RestaurantEntity;

import java.util.List;

/**
 * Репозиторий для ресторана
 */
@RepositoryRestResource(path = "restaurant")
public interface RestaurantRepository extends CrudRepository<RestaurantEntity, Long> {
    List<RestaurantEntity> findRestaurantById(Long id);
}
