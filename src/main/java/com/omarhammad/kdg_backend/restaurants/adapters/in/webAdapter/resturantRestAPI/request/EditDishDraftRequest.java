package com.omarhammad.kdg_backend.restaurants.adapters.in.webAdapter.resturantRestAPI.request;

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
