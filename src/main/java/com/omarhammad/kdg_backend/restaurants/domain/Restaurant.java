package com.omarhammad.kdg_backend.restaurants.domain;

import com.omarhammad.kdg_backend.restaurants.domain.enums.Cuisine;
import com.omarhammad.kdg_backend.restaurants.domain.enums.Day;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.*;


public class Restaurant {

    @Setter
    private Id<Restaurant> id;
    @Setter
    private String name;
    @Setter
    private Email email;
    @Setter
    private Address address;
    @Setter
    private String resPictureUrl;

    private final Map<Day, OpeningHours> dayOpeningHours;
    @Setter
    private int manualOpening;
    @Setter
    private Cuisine cuisine;
    @Setter
    private int defaultPrepTime;
    private final List<Dish> dishes;
    @Setter
    private boolean hasScheduledPublish;
    @Setter
    private Id<Owner> ownerId;

    public Restaurant() {
        this.dayOpeningHours = new HashMap<>();
        this.dishes = new ArrayList<>();
    }

    public Restaurant(Id<Restaurant> id, String name, Email email, Address address, String resPictureUrl, int manualOpening, Cuisine cuisine, int defaultPrepTime, boolean hasScheduledPublish, Id<Owner> ownerId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.resPictureUrl = resPictureUrl;
        this.manualOpening = manualOpening;
        this.cuisine = cuisine;
        this.defaultPrepTime = defaultPrepTime;
        this.dishes = new ArrayList<>();
        this.hasScheduledPublish = hasScheduledPublish;
        this.dayOpeningHours = new HashMap<>();
        this.ownerId = ownerId;
    }

    public Id<Restaurant> getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public Email getEmail() {
        return email;
    }


    public Address getAddress() {
        return address;
    }


    public String getResPictureUrl() {
        return resPictureUrl;
    }


    public Map<Day, OpeningHours> getDayOpeningHours() {
        return dayOpeningHours;
    }

    public OpeningHours getOneDayOpeningHours(Day day) {
        return this.dayOpeningHours.get(day);
    }

    public void addOpeningHoursForDay(Day day, OpeningHours op) {
        this.dayOpeningHours.put(day, op);
    }

    public int getManualOpening() {
        return manualOpening;
    }

    public Cuisine getCuisine() {
        return cuisine;
    }

    public int getDefaultPrepTime() {
        return defaultPrepTime;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void addDish(Dish dish) {
        this.dishes.add(dish);
    }

    public boolean hasScheduledPublish() {
        return hasScheduledPublish;
    }

    public Id<Owner> getOwnerId() {
        return this.ownerId;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email=" + email +
                ", address=" + address +
                ", resPictureUrl='" + resPictureUrl + '\'' +
                ", dayOpeningHours=" + dayOpeningHours +
                ", manualOpening=" + manualOpening +
                ", cuisine=" + cuisine +
                ", defaultPrepTime=" + defaultPrepTime +
                ", dishes=" + dishes +
                ", owner=" + ownerId +
                '}';
    }
}
