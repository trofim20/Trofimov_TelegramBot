package ru.trofimov.Trofimov_TelegramBot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.trofimov.Trofimov_TelegramBot.entity.DishEntity;
import ru.trofimov.Trofimov_TelegramBot.repository.DishRepository;
import ru.trofimov.Trofimov_TelegramBot.repository.RestaurantRepository;

import java.util.List;

/**
 * Сервис блюд, для удаления по идентификатору
 */
@Service
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public DishServiceImpl(DishRepository dishRepository, RestaurantRepository restaurantRepository, PlatformTransactionManager transactionManager) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void deleteDishByRestaurantId(Long id) {
        TransactionStatus status = transactionManager.getTransaction(new
                DefaultTransactionDefinition());
        try {
            List<DishEntity> dishes = dishRepository.findByRestaurantId(id);
            for (DishEntity dish : dishes) {
                dishRepository.delete(dish);
            }
            transactionManager.commit(status);
        } catch (DataAccessException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
