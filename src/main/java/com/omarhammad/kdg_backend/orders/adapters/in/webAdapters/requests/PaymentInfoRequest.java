package com.omarhammad.kdg_backend.orders.adapters.in.webAdapters.requests;

import java.math.BigDecimal;

public record PaymentInfoRequest(
        String method,
        BigDecimal amount,
        String paymentToken) {
}
