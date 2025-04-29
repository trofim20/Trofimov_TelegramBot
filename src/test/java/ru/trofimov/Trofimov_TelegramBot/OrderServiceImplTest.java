package ru.trofimov.Trofimov_TelegramBot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.trofimov.Trofimov_TelegramBot.entity.DishEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.OrderEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.UserEntity;
import ru.trofimov.Trofimov_TelegramBot.repository.OrderRepository;
import ru.trofimov.Trofimov_TelegramBot.service.OrderServiceImpl;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    Long orderId = 1L;
    String orderTitle = "Order Title";
    String orderDescription = "Order Description";
    String status = "Order Status";
    UserEntity userEntity = new UserEntity();
    DishEntity dishEntity = new DishEntity();

    @Test
    void createOrder() {
        when(orderRepository.read(orderId)).thenReturn(null);

        orderService.createOrder(orderId, orderTitle, orderDescription, status, userEntity, dishEntity);

        verify(orderRepository, times(1)).create(any(OrderEntity.class));
    }

    @Test
    void createOrderWithExistingId() {
        when(orderRepository.read(orderId)).thenReturn(new OrderEntity());

        assertThrows(IllegalArgumentException.class, () ->
                orderService.createOrder(orderId, "order", "desc", "status", new UserEntity(), new DishEntity()));
    }

    @Test
    void deleteOrderById() {
        when(orderRepository.read(orderId)).thenReturn(new OrderEntity());

        orderService.deleteOrderById(orderId);

        verify(orderRepository, times(1)).delete(orderId);
    }

    @Test
    void deleteOrderByIdWithNullId() {
        assertThrows(IllegalArgumentException.class, () ->
                orderService.deleteOrderById(null));
    }

    @Test
    void deleteOrderByIdNotFound() {
        when(orderRepository.read(orderId)).thenReturn(null);

        assertThrows(NoSuchElementException.class, () ->
                orderService.deleteOrderById(orderId));
    }

    @Test
    void updateOrderStatus() {
        OrderEntity orderEntity = new OrderEntity();
        when(orderRepository.read(orderId)).thenReturn(orderEntity);

        orderService.updateOrderStatus(orderId, status);

        assertEquals(status, orderEntity.getOrderStatus());
        verify(orderRepository, times(1)).update(orderEntity);
    }


    @Test
    void updateOrderStatusNotFound() {
        when(orderRepository.read(orderId)).thenReturn(null);

        assertThrows(NoSuchElementException.class, () ->
                orderService.updateOrderStatus(orderId, "Completed"));
    }
}
