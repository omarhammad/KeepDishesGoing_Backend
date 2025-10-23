package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.OrdersProjectionsRespo.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public record DropOfAddressJpa(String street,
                               int number,
                               String postalCode,
                               String city,
                               String country) {
}
