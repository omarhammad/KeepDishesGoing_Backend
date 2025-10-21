package com.omarhammad.kdg_backend.orders.adapters.out.jpaRepositories.ordersRepo.entites;

import com.omarhammad.kdg_backend.orders.domain.PaymentResult;
import com.omarhammad.kdg_backend.orders.domain.enums.PaymentMethod;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "payments")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentJpaEntity {
    @Id
    private UUID id;
    private String method;
    private BigDecimal amount;
    private String paymentToken;
    private PaymentResultsJpa paymentResult;
}
