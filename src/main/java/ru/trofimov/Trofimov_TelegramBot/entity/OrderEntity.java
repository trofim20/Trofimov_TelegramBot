package ru.trofimov.Trofimov_TelegramBot.entity;

/**
 * Сущность заказов
 */
public class OrderEntity {

    private Long id;
    private Long userId;
    private String order;
    private String orderDescription;
    private String orderStatus;

    public OrderEntity(Long id, Long userId, String order,
                       String orderDescription,
                       String orderStatus) {
        this.id = id;
        this.userId = userId;
        this.order = order;
        this.orderDescription = orderDescription;
        this.orderStatus = orderStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id заказа=" + id +
                ", id пользователя=" + userId +
                ", номер заказа='" + order + '\'' +
                ", описание заказа='" + orderDescription + '\'' +
                ", статус заказа='" + orderStatus;
    }
}