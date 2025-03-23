package ru.trofimov.Trofimov_TelegramBot.entity;

import jakarta.persistence.*;

/**
 * Сущность оплаты
 */
@Entity
@Table(name = "tbl_payment")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double amount;

    @Column
    private String paymentMethod;

    @Column
    private String status;

    @OneToOne
    private OrderEntity order;

    public PaymentEntity() {
    }

    public PaymentEntity(double amount, String paymentMethod, String status, OrderEntity order) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.order = order;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
