package com.omarhammad.kdg_backend.orders.adapters.out.payment;

import com.omarhammad.kdg_backend.orders.domain.Payment;
import com.omarhammad.kdg_backend.orders.domain.PaymentResult;
import com.omarhammad.kdg_backend.orders.ports.out.ProcessPaymentPort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PaymentAdapter implements ProcessPaymentPort {
    @Override
    public PaymentResult process(Payment payment) {
        if (payment.getPaymentToken() == null) return PaymentResult.failure("no token");
        if (!payment.getPaymentToken().startsWith("tok_kdg")) return PaymentResult.failure("invalid token");
        if (payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) return PaymentResult.failure("invalid amount");
        if (Math.random() < 0.05) return PaymentResult.failure("gateway error");
        return PaymentResult.success();
    }
}
