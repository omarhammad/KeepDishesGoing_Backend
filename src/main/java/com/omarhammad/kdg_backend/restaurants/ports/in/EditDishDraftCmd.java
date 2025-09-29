package com.omarhammad.kdg_backend.restaurants.ports.in;

import com.omarhammad.kdg_backend.restaurants.domain.utils.DishType;
import com.omarhammad.kdg_backend.restaurants.domain.utils.FoodTag;

import java.math.BigDecimal;
import java.util.List;

public record EditDishDraftCmd(
        String id,
        String name,
        DishType dishType,
        List<FoodTag> foodTags,
        String description,
        BigDecimal price,
        String pictureUrl) {
}
