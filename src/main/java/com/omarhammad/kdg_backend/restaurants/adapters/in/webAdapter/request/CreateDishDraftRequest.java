package com.omarhammad.kdg_backend.restaurants.adapters.in.webAdapter.request;

import java.math.BigDecimal;
import java.util.List;

public record CreateDishDraftRequest(String name,
                                     String dishType,
                                     List<String> foodTags,
                                     String description,
                                     BigDecimal price,
                                     String pictureUrl) {
}
