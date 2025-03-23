package ru.trofimov.Trofimov_TelegramBot.service;

import org.springframework.stereotype.Service;
import ru.trofimov.Trofimov_TelegramBot.entity.DishEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.OrderEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.PaymentEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.UserEntity;
import ru.trofimov.Trofimov_TelegramBot.repository.OrderRepository;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Сервис заказов
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void createOrder(Long id, String order, String orderDescription, String orderStatus,
                            UserEntity user, List<DishEntity> dishes, PaymentEntity payment) {
        if (id == null || user == null || order == null || orderDescription == null || orderStatus == null) {
            throw new IllegalArgumentException("Все поля заказа должны быть заполнены.");
        }

        if (orderRepository.read(id) != null) {
            throw new IllegalArgumentException("Заказ с ID " + id + " уже существует.");
        }
        OrderEntity createOrder = new OrderEntity(order, orderDescription, orderStatus,
                user, dishes, payment);
        orderRepository.create(createOrder);
    }

    @Override
    public OrderEntity findOrderById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID заказа не может быть null.");
        }
        OrderEntity order = orderRepository.read(id);
        if (order == null) {
            throw new NoSuchElementException("Заказ с ID " + id + " не найден.");
        }

        return order;
    }

    @Override
    public void deleteOrderById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID заказа не может быть null.");
        }
        if (orderRepository.read(id) == null) {
            throw new NoSuchElementException("Заказ с ID " + id + " не найден.");
        }
        orderRepository.delete(id);
    }

    @Override
    public void updateOrderStatus(Long id, String newStatus) {
        if (id == null || newStatus == null) {
            throw new IllegalArgumentException("ID заказа и новый статус не могут быть null.");
        }
        OrderEntity order = orderRepository.read(id);
        if (order == null) {
            throw new NoSuchElementException("Заказ с ID " + id + " не найден.");
        }
        order.setOrderStatus(newStatus);
        orderRepository.update(order);
    }
}