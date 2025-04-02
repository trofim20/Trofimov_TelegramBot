package ru.trofimov.Trofimov_TelegramBot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.trofimov.Trofimov_TelegramBot.entity.DishEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.RestaurantEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.UserEntity;
import ru.trofimov.Trofimov_TelegramBot.repository.DishRepository;
import ru.trofimov.Trofimov_TelegramBot.repository.RestaurantRepository;
import ru.trofimov.Trofimov_TelegramBot.repository.UserRepository;
import ru.trofimov.Trofimov_TelegramBot.repositoryCustom.RepositoryCustom;

import java.util.List;

/**
 * Тестирование методов репозитория
 */
@SpringBootTest
class RepositoryTest {
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    private final RepositoryCustom repositoryCustom;

    @Autowired
    RepositoryTest(UserRepository userRepository, DishRepository dishRepository, RestaurantRepository restaurantRepository, RepositoryCustom repositoryCustom) {
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
        this.repositoryCustom = repositoryCustom;
    }

    @AfterEach
    void tearDown() {
        dishRepository.deleteAll();
        userRepository.deleteAll();
        restaurantRepository.deleteAll();
    }

    @Test
    void findByRestaurantId() {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setName("Ресторан");
        restaurantRepository.save(restaurantEntity);

        DishEntity dish = new DishEntity();
        dish.setName("Борщ");
        dish.setRestaurant(restaurantEntity);
        dishRepository.save(dish);

        List<DishEntity> foundDishes = dishRepository.findByRestaurantId(restaurantEntity.getId());

        Assertions.assertFalse(foundDishes.isEmpty());
        DishEntity foundDish = foundDishes.getFirst();

        Assertions.assertEquals(restaurantEntity.getId(), foundDish.getRestaurant().getId());
        Assertions.assertEquals("Борщ", foundDish.getName());
    }

    @Test
    void findByUsername() {
        String username = "Никита Трофимов";
        UserEntity user = new UserEntity();
        user.setName(username);
        userRepository.save(user);

        List<UserEntity> foundUsers = userRepository.findByName(username);
        Assertions.assertFalse(foundUsers.isEmpty());
        UserEntity foundUser = foundUsers.getFirst();

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(user.getId(), foundUser.getId());
        Assertions.assertEquals(username, foundUser.getName());
    }

    @Test
    void findByNameWithCriteria() {
        String userName = "Никита Трофимов";
        UserEntity user = new UserEntity();
        user.setName(userName);
        userRepository.save(user);

        List<UserEntity> foundUsers = repositoryCustom.findByName(userName);

        Assertions.assertFalse(foundUsers.isEmpty());

        UserEntity foundUser = foundUsers.getFirst();

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(user.getId(), foundUser.getId());
        Assertions.assertEquals(userName, foundUser.getName());
    }

    @Test
    void findByRestaurantIdWithCriteria() {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setName("Ресторан");
        restaurantRepository.save(restaurantEntity);

        DishEntity dish = new DishEntity();
        dish.setName("Борщ");
        dish.setRestaurant(restaurantEntity);
        dishRepository.save(dish);

        List<DishEntity> foundDishes = repositoryCustom.findByRestaurantId(restaurantEntity.getId());
        Assertions.assertFalse(foundDishes.isEmpty());
        DishEntity foundDish = foundDishes.getFirst();

        Assertions.assertEquals(restaurantEntity.getId(), foundDish.getRestaurant().getId());
        Assertions.assertEquals("Борщ", foundDish.getName());
    }
}