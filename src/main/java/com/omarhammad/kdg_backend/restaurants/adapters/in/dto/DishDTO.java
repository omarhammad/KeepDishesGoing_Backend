package com.omarhammad.kdg_backend.restaurants.adapters.in.dto;

import com.omarhammad.kdg_backend.restaurants.domain.enums.DishType;
import com.omarhammad.kdg_backend.restaurants.domain.enums.FoodTag;

import java.math.BigDecimal;
import java.util.List;

public record DishDTO(String id,
                      String name,
                      DishType dishType,
                      List<FoodTag> foodTags,
                      String description,
                      BigDecimal price,
                      String pictureUrl,
                      boolean isInStock
) {
}
