package com.omarhammad.kdg_backend.orders.adapters.out.jpaRepositories.ordersRepo.entites;

import jakarta.persistence.Embeddable;

@Embeddable
public record PaymentResultsJpa(boolean successState, String message) {
}
