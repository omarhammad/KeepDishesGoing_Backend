package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.resturantRepo.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public record OpeningHoursJpa(String open, String close) {

}
