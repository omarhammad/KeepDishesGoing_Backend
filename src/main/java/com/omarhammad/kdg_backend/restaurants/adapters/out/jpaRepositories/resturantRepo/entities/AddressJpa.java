package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.resturantRepo.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public record AddressJpa( String street,
                          int number,
                          String postalCode,
                          String city,
                          String country) {
}
