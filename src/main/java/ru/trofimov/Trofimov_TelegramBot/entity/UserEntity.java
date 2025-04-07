package ru.trofimov.Trofimov_TelegramBot.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.HashSet;
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
    private String password;

    @Column
    private String number;

    @Column
    private String address;

    @OneToMany(mappedBy = "user")
    private List<OrderEntity> orderHistory;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Collection<Role> roles = new HashSet<>();

    public UserEntity() {
    }

    public UserEntity(String telegram_id, String name, String number, String address) {
        this.telegram_id = telegram_id;
        this.name = name;
        this.number = number;
        this.address = address;
    }

    public UserEntity(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.roles.add(role);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}