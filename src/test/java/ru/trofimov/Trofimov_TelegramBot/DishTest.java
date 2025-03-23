package ru.trofimov.Trofimov_TelegramBot;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.trofimov.Trofimov_TelegramBot.entity.DishEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.RestaurantEntity;
import ru.trofimov.Trofimov_TelegramBot.repository.DishRepository;
import ru.trofimov.Trofimov_TelegramBot.repository.RestaurantRepository;
import ru.trofimov.Trofimov_TelegramBot.service.DishService;

import java.util.List;
import java.util.Optional;

/**
 * Тестирование транзакционного метода
 */
@SpringBootTest
class DishTest {
    private final DishService dishService;
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public DishTest(DishService dishService,
                    DishRepository dishRepository,
                    RestaurantRepository restaurantRepository) {
        this.dishService = dishService;
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Test
    void testDeleteDishByIdInTx() {
        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setName("Ресторан");
        restaurantRepository.save(restaurant);

        DishEntity dish1 = new DishEntity();
        dish1.setName("Борщ");
        dish1.setRestaurant(restaurant);
        dishRepository.save(dish1);

        DishEntity dish2 = new DishEntity();
        dish2.setName("Суп");
        dish2.setRestaurant(restaurant);
        dishRepository.save(dish2);

        dishService.deleteDishById(restaurant.getId());

        List<DishEntity> foundDishes = dishRepository.findByRestaurantId(restaurant.getId());
        Assertions.assertTrue(foundDishes.isEmpty());

        Optional<RestaurantEntity> foundRestaurant = restaurantRepository.findById(restaurant.getId());
        Assertions.assertTrue(foundRestaurant.isPresent());
    }
}