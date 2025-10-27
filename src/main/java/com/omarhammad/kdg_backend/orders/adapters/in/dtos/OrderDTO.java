package com.omarhammad.kdg_backend.orders.adapters.in.dtos;

import com.omarhammad.kdg_backend.orders.domain.Customer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(String id,
                       String orderStatus,
                       String rejectedMsg,
                       String declinedMsg,
                       LocalDateTime statusOccurredAt,
                       String restaurantId,
                       List<String> dishes,
                       BigDecimal totalPrice,
                       CustomerDTO customer
) {


}
