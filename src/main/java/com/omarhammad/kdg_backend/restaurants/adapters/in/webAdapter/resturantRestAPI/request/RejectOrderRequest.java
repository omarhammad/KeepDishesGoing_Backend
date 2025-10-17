package com.omarhammad.kdg_backend.restaurants.adapters.in.webAdapter.resturantRestAPI.request;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.OrderProjection;

public record RejectOrderRequest(String orderId,
                                 String reason) {
}
