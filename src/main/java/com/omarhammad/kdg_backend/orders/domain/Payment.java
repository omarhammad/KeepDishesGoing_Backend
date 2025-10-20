package com.omarhammad.kdg_backend.orders.domain;

import com.omarhammad.kdg_backend.orders.domain.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Payment {


    private Id<Payment> id;
    private PaymentMethod method;
    private BigDecimal amount;
    private boolean isPaid;

    public Payment(Id<Payment> id, PaymentMethod method, BigDecimal amount, boolean isPaid) {
        this.id = id;
        this.method = method;
        this.amount = amount;
        this.isPaid = isPaid;
    }

    public Payment(PaymentMethod method, BigDecimal amount, boolean isPaid) {
        this.method = method;
        this.amount = amount;
        this.isPaid = isPaid;
    }
}
