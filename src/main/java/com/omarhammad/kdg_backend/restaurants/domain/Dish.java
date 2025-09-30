package com.omarhammad.kdg_backend.restaurants.domain;

import com.omarhammad.kdg_backend.restaurants.domain.enums.DishType;
import com.omarhammad.kdg_backend.restaurants.domain.enums.FoodTag;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Dish {

    private Id<Dish> id;
    private String name;
    private DishType dishType;
    private final List<FoodTag> foodTags;
    private String description;
    private BigDecimal price;
    private String pictureUrl;
    private boolean isInStock;
    private boolean isPublished;
    private LocalDateTime publishTime;

    public Dish() {
        this.foodTags = new ArrayList<>();
    }

    public void addFoodTag(FoodTag foodTag) {
        this.foodTags.add(foodTag);
    }
}
