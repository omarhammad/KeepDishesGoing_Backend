package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.resturantRepo.entities;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Setter
@Getter
@Table(name = "restaurants")
public class RestaurantsJpaEntity {

    @Id
    UUID id;

    private String name;

    String email;

    private AddressJpa address;

    private String resPictureUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "restaurant_opening_hours",
            joinColumns = @JoinColumn(name = "restaurants_id")
    )
    @MapKeyColumn(name = "day_of_week")
    private final Map<String, OpeningHoursJpa> dayOpeningHours;

    private String manualOpening;

    private String cuisine;

    private int defaultPrepTime;

    private boolean hasScheduledPublish;

    private UUID owner;

    public RestaurantsJpaEntity() {
        dayOpeningHours = new HashMap<>();
    }

    public RestaurantsJpaEntity(UUID id, String name, String email, AddressJpa address, String resPictureUrl, String manualOpening, String cuisine, int defaultPrepTime, boolean hasScheduledPublish, UUID owner) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.resPictureUrl = resPictureUrl;
        this.manualOpening = manualOpening;
        this.cuisine = cuisine;
        this.defaultPrepTime = defaultPrepTime;
        this.hasScheduledPublish = hasScheduledPublish;
        this.owner = owner;
        this.dayOpeningHours = new HashMap<>();
    }

    public void addDayOpeningHours(String day, OpeningHoursJpa openingHoursJpa) {
        this.dayOpeningHours.put(day, openingHoursJpa);
    }


}
