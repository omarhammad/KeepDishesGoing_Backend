package com.omarhammad.kdg_backend.orders.ports.in;

import com.omarhammad.kdg_backend.orders.domain.DeliveryAddress;
import com.omarhammad.kdg_backend.orders.domain.Email;
import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.domain.Order;
import com.omarhammad.kdg_backend.orders.domain.enums.PaymentMethod;

import java.math.BigDecimal;

public record CheckoutCmd(Id<Order> orderId,
                          String firstName,
                          String lastName,
                          DeliveryAddress deliveryAddress,
                          Email email,
                          String phoneNumber,
                          PaymentMethod paymentMethod,
                          BigDecimal paymentAmount,
                          String paymentToken) {
}
