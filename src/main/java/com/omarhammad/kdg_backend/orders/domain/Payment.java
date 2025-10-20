package com.omarhammad.kdg_backend.orders.domain;

import com.omarhammad.kdg_backend.orders.domain.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class Payment {


    private Id<Payment> id;
    private PaymentMethod method;
    private BigDecimal amount;
    private String paymentToken;
    private PaymentResult paymentResult;


    public Payment(PaymentMethod method, BigDecimal amount, String paymentToken) {
        this.method = method;
        this.amount = amount;
        this.paymentToken = paymentToken;
    }
}
