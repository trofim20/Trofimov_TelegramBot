package ru.trofimov.Trofimov_TelegramBot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.Trofimov_TelegramBot.entity.DishEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.UserEntity;
import ru.trofimov.Trofimov_TelegramBot.repository.DishRepository;
import ru.trofimov.Trofimov_TelegramBot.repository.UserRepository;

import java.util.List;

/**
 * Реализация контроллера RestController
 */
@RestController
@RequestMapping("/custom")
public class CustomRestController {
    private final DishRepository dishRepository;
    private final UserRepository userRepository;

    @Autowired
    public CustomRestController(DishRepository dishRepository, UserRepository userRepository) {
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/dishes/findByRestaurantId")
    public List<DishEntity> getDishByRestaurantId(@RequestParam Long restaurantId) {
        return dishRepository.findByRestaurantId(restaurantId);
    }

    @GetMapping("users/findByName")
    public UserEntity findUserByName(@RequestParam String name) {
        return userRepository.findByName(name);
    }
}