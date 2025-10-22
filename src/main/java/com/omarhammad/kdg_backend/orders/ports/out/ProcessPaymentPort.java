package com.omarhammad.kdg_backend.orders.ports.out;

import com.omarhammad.kdg_backend.orders.domain.Payment;
import com.omarhammad.kdg_backend.orders.domain.PaymentResult;

import java.math.BigDecimal;

public interface ProcessPaymentPort {

    PaymentResult process(Payment payment, BigDecimal amountToBePaid);

}
