package ru.trofimov.Trofimov_TelegramBot.commandProcessor;

import org.springframework.stereotype.Component;
import ru.trofimov.Trofimov_TelegramBot.entity.DishEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.OrderEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.PaymentEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.UserEntity;
import ru.trofimov.Trofimov_TelegramBot.service.OrderService;

import java.util.List;

/**
 * Компонент для обработки команд, введённых пользователем через консоль
 */
@Component
public class CommandProcessor {

    private final OrderService orderService;

    public CommandProcessor(OrderService orderService) {
        this.orderService = orderService;
    }

    public void processCommand(String input) {
        String[] command = input.split(" ");
        try {
            switch (command[0]) {
                case "create" -> {
                    Long id = Long.parseLong(command[1]);
                    UserEntity user = new UserEntity();
                    String order = command[3];
                    String orderDescription = command[4];
                    String orderStatus = command[5];
                    List<DishEntity> dishes = List.of();
                    PaymentEntity payment = new PaymentEntity();
                    orderService.createOrder(id, order, orderDescription, orderStatus,
                            user, dishes, payment);
                    System.out.println("Заказ создан");
                }
                case "find" -> {
                    OrderEntity order = orderService.findOrderById(Long.valueOf(command[1]));
                    System.out.println("Заказ : " + order + " найден");
                }
                case "delete" -> {
                    orderService.deleteOrderById(Long.valueOf(command[1]));
                    System.out.println("Заказ удален");
                }
                case "update" -> {
                    orderService.updateOrderStatus(Long.valueOf(command[1]), command[2]);
                    System.out.println("Заказ с id: обнавлен на статус " + command[2]);
                }
                default -> System.out.println("Неизвестная команда");
            }
        } catch (Exception e) {
            System.err.println("Ошибка " + e.getMessage());
        }
    }
}