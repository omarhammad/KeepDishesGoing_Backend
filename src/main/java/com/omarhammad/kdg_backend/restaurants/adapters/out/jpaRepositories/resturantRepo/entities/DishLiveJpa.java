package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.resturantRepo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "dishes_lives")
public class DishLiveJpa {

    @Id
    private UUID id;

    private String name;

    private String dishType;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "dish_live_food_tags",
            joinColumns = @JoinColumn(name = "dish_live_id")
    )
    private List<String> foodTags;

    private String description;

    private BigDecimal price;

    private String pictureUrl;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private DishJpaEntity dish;


    public DishLiveJpa(String name, String dishType, String description, BigDecimal price, String pictureUrl) {
        this.name = name;
        this.dishType = dishType;
        this.foodTags = new ArrayList<>();
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;
    }


    public void addFoodTags(String foodTag) {
        this.foodTags.add(foodTag);

    }
}
