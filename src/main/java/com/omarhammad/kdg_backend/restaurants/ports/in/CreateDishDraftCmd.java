package com.omarhammad.kdg_backend.restaurants.ports.in;

import com.omarhammad.kdg_backend.restaurants.domain.enums.DishType;
import com.omarhammad.kdg_backend.restaurants.domain.enums.FoodTag;

import java.math.BigDecimal;
import java.util.List;

public record CreateDishDraftCmd(
        String name,
        DishType dishType,
        List<FoodTag> foodTags,
        String description,
        BigDecimal price,
        String pictureUrl) {
}
