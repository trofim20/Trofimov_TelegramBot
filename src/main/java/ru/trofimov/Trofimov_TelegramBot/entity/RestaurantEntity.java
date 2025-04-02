package ru.trofimov.Trofimov_TelegramBot.entity;

import jakarta.persistence.*;

import java.util.List;

/**
 * Сущность ресторана
 */
@Entity
@Table(name = "tbl_restaurant")
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String address;

    @OneToMany(mappedBy = "restaurant")
    private List<DishEntity> menu;

    public RestaurantEntity() {
    }

    public RestaurantEntity(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<DishEntity> getMenu() {
        return menu;
    }

    public void setMenu(List<DishEntity> menu) {
        this.menu = menu;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

