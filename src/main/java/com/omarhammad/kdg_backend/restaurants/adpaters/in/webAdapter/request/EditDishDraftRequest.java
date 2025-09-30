package com.omarhammad.kdg_backend.restaurants.adpaters.in.webAdapter.request;

import com.omarhammad.kdg_backend.restaurants.domain.enums.DishType;
import com.omarhammad.kdg_backend.restaurants.domain.enums.FoodTag;

import java.math.BigDecimal;
import java.util.List;

public record EditDishDraftRequest(
        String id,
        String name,
        String dishType,
        List<String> foodTags,
        String description,
        BigDecimal price,
        String pictureUrl
) {
}
