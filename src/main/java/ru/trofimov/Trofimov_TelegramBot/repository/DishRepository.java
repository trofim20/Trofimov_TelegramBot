package ru.trofimov.Trofimov_TelegramBot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.trofimov.Trofimov_TelegramBot.entity.DishEntity;

import java.util.List;

/**
 * Репозиторий для блюд
 */
public interface DishRepository extends CrudRepository<DishEntity, Long> {
    @Query("SELECT dish FROM DishEntity dish WHERE dish.restaurant.id = :restaurantId")
    List<DishEntity> findByRestaurantId(@Param("restaurantId") Long restaurantId);
}
