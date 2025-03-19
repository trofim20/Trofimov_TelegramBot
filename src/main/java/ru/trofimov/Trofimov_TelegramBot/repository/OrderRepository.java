package ru.trofimov.Trofimov_TelegramBot.repository;

import org.springframework.stereotype.Component;
import ru.trofimov.Trofimov_TelegramBot.entity.OrderEntity;

import java.util.Map;

/**
 * Репозиторий заказов
 */
@Component
public class OrderRepository implements CrudRepository<OrderEntity, Long> {

    private final Map<Long, OrderEntity> orderContainer;

    public OrderRepository(Map<Long, OrderEntity> orderContainer) {
        this.orderContainer = orderContainer;
    }

    @Override
    public void create(OrderEntity order) {
        orderContainer.put(order.getId(), order);
    }

    @Override
    public OrderEntity read(Long id) {
        return orderContainer.get(id);
    }

    @Override
    public void update(OrderEntity order) {
        orderContainer.put(order.getId(), order);
    }

    @Override
    public void delete(Long id) {
        orderContainer.remove(id);
    }
}