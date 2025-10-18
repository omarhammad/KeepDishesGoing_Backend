package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.resturantRepo.entities;

import jakarta.persistence.*;
import jakarta.persistence.Id;
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

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<DishJpaEntity> dishJpaEntities;

    private UUID owner;


    public RestaurantsJpaEntity() {
        this.dishJpaEntities = new ArrayList<>();
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
        this.dishJpaEntities = new ArrayList<>();
        this.owner = owner;
        this.dayOpeningHours = new HashMap<>();
    }

    public void addDayOpeningHours(String day, OpeningHoursJpa openingHoursJpa) {
        this.dayOpeningHours.put(day, openingHoursJpa);
    }

    public void addDishJpaEntity(DishJpaEntity dishJpaEntity) {
        dishJpaEntity.setRestaurant(this);
        this.dishJpaEntities.add(dishJpaEntity);


    }


}
