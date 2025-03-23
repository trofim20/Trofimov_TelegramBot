package ru.trofimov.Trofimov_TelegramBot.entity;

import jakarta.persistence.*;

import java.util.List;

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

    @OneToMany(mappedBy = "order")
    private List<DishEntity> dishes;

    @OneToOne
    private PaymentEntity payment;

    public OrderEntity() {
    }

    public OrderEntity(String order, String orderDescription, String orderStatus,
                       UserEntity user, List<DishEntity> dishes, PaymentEntity payment) {
        this.order = order;
        this.orderDescription = orderDescription;
        this.orderStatus = orderStatus;
        this.user = user;
        this.dishes = dishes;
        this.payment = payment;
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

    public List<DishEntity> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishEntity> dishes) {
        this.dishes = dishes;
    }

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }
}