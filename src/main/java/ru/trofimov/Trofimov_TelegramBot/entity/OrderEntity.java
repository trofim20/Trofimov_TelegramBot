package ru.trofimov.Trofimov_TelegramBot.entity;

import jakarta.persistence.*;

/**
 * Сущность заказов
 */
@Entity
@Table(name = "tbl_order")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number")
    private String order;

    @Column
    private String orderDescription;

    @Column
    private String orderStatus;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private DishEntity dishes;


    public OrderEntity() {
    }

    public OrderEntity(String order, String orderDescription, String orderStatus,
                       UserEntity user, DishEntity dishes) {
        this.order = order;
        this.orderDescription = orderDescription;
        this.orderStatus = orderStatus;
        this.user = user;
        this.dishes = dishes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public DishEntity getDishes() {
        return dishes;
    }

    public void setDishes(DishEntity dishes) {
        this.dishes = dishes;
    }
}