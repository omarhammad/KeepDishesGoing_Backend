package com.omarhammad.kdg_backend.orders.adapters.out.payment;

import com.omarhammad.kdg_backend.orders.domain.Payment;
import com.omarhammad.kdg_backend.orders.domain.PaymentResult;
import com.omarhammad.kdg_backend.orders.ports.out.ProcessPaymentPort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PaymentAdapter implements ProcessPaymentPort {
    @Override
    public PaymentResult process(Payment payment, BigDecimal amountToBePaid) {
        if (payment.getPaymentToken() == null || payment.getPaymentToken().isBlank())
            return PaymentResult.failure("Missing payment token");
        if (!payment.getPaymentToken().startsWith("tok_kdg")) return PaymentResult.failure("Invalid token");
        if (payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) return PaymentResult.failure("Invalid amount");
        if (payment.getAmount().compareTo(amountToBePaid) < 0) return PaymentResult.failure("Insufficient amount");
        if (Math.random() < 0.05) return PaymentResult.failure("Gateway error");
        return PaymentResult.success();
    }
}
