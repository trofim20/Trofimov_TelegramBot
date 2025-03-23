package ru.trofimov.Trofimov_TelegramBot.entity;

import jakarta.persistence.*;

import java.util.List;

/**
 * Сущность пользователя
 */
@Entity
@Table(name = "tbl_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String telegram_id;

    @Column
    private String name;

    @Column
    private String number;

    @Column
    private String address;

    @OneToMany(mappedBy = "user")
    private List<OrderEntity> orderHistory;

    public UserEntity() {
    }

    public UserEntity(String telegram_id, String name, String number, String address) {
        this.telegram_id = telegram_id;
        this.name = name;
        this.number = number;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelegram_id() {
        return telegram_id;
    }

    public void setTelegram_id(String telegram_id) {
        this.telegram_id = telegram_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderEntity> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<OrderEntity> orderHistory) {
        this.orderHistory = orderHistory;
    }
}