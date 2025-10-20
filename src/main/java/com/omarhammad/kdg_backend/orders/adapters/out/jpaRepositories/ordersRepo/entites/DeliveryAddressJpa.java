package com.omarhammad.kdg_backend.orders.adapters.out.jpaRepositories.ordersRepo.entites;

import jakarta.persistence.Embeddable;

@Embeddable
public record DeliveryAddressJpa(String street,
                                 int number,
                                 String postalCode,
                                 String city,
                                 String country) {
}
