package com.omarhammad.kdg_backend.restaurants.domain;

import com.omarhammad.kdg_backend.common.sharedDomain.Address;
import com.omarhammad.kdg_backend.common.sharedDomain.Email;
import com.omarhammad.kdg_backend.restaurants.domain.utils.Cuisine;
import com.omarhammad.kdg_backend.restaurants.domain.utils.Day;

import java.util.*;

public class Restaurant {

    private UUID id;
    private String name;
    private Email email;
    private Address address;
    private String resPictureUrl;
    private final Map<Day, OpeningHours> dayOpeningHours;
    private int manualOpening;
    private Cuisine cuisine;
    private int defaultPrepTime;
    private final List<Dish> dishes;
    private Owner owner;

    public Restaurant() {
        this.dayOpeningHours = new HashMap<>();
        this.dishes = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getResPictureUrl() {
        return resPictureUrl;
    }

    public void setResPictureUrl(String resPictureUrl) {
        this.resPictureUrl = resPictureUrl;
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

    public void setManualOpening(int manualOpening) {
        this.manualOpening = manualOpening;
    }

    public Cuisine getCuisine() {
        return cuisine;
    }

    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }

    public int getDefaultPrepTime() {
        return defaultPrepTime;
    }

    public void setDefaultPrepTime(int defaultPrepTime) {
        this.defaultPrepTime = defaultPrepTime;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void addDish(Dish dish) {
        this.dishes.add(dish);
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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
                ", owner=" + owner +
                '}';
    }
}
