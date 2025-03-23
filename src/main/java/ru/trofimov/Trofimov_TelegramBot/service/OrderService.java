package ru.trofimov.Trofimov_TelegramBot.service;

import ru.trofimov.Trofimov_TelegramBot.entity.DishEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.OrderEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.PaymentEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.UserEntity;

import java.util.List;

/**
 * Интерфейс сервиса заказов
 */
public interface OrderService {

    void createOrder(Long id, String order, String orderDescription, String orderStatus,
                     UserEntity user, List<DishEntity> dishes, PaymentEntity payment);

    OrderEntity findOrderById(Long id);

    void deleteOrderById(Long id);

    void updateOrderStatus(Long id, String newStatus);
}