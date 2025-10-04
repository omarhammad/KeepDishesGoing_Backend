package com.omarhammad.kdg_backend.restaurants.domain;

import com.omarhammad.kdg_backend.restaurants.domain.enums.DishType;
import com.omarhammad.kdg_backend.restaurants.domain.enums.FoodTag;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record DishData(String name,
                       DishType dishType,
                       List<FoodTag> foodTags,
                       String description,
                       BigDecimal price,
                       String pictureUrl) {

    public DishData(String name, DishType dishType,
                    String description, BigDecimal price, String pictureUrl) {
        this(name, dishType, new ArrayList<>(), description, price, pictureUrl);
    }

    public void addFoodTag(FoodTag foodTag) {
        this.foodTags.add(foodTag);
    }
}
