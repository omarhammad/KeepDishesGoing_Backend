package com.omarhammad.kdg_backend.orders.adapters.in.webAdapters.requests;

public record CheckoutRequest(String firstName,
                              String lastName,
                              DeliveryAddressRequest deliveryAddress,
                              String email,
                              String phoneNumber,
                              PaymentInfoRequest paymentInfo) {
}