package ru.trofimov.Trofimov_TelegramBot.service;

import ru.trofimov.Trofimov_TelegramBot.entity.OrderEntity;

/**
 * Интерфейс сервиса заказов
 */
public interface OrderService {

    void createOrder(Long id, Long userId, String order, String orderDescription, String orderStatus);

    OrderEntity findOrderById(Long id);

    void deleteOrderById(Long id);

    void updateOrderStatus(Long id, String newStatus);
}