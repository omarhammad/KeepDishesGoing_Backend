package com.omarhammad.kdg_backend.restaurants.domain;

import com.omarhammad.kdg_backend.common.events.DomainEvent;
import com.omarhammad.kdg_backend.common.events.restaurantEvents.OrderAcceptedEvent;
import com.omarhammad.kdg_backend.common.events.restaurantEvents.OrderRejectedEvent;
import com.omarhammad.kdg_backend.restaurants.domain.enums.Cuisine;
import com.omarhammad.kdg_backend.restaurants.domain.enums.Day;
import com.omarhammad.kdg_backend.restaurants.domain.enums.OpeningStatus;
import com.omarhammad.kdg_backend.restaurants.domain.enums.OrderProjectionStatus;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.OrderAlreadyAcceptedException;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.OrderAlreadyRejectedException;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Slf4j
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
    private OpeningStatus manualOpening;
    @Setter
    private Cuisine cuisine;
    @Setter
    private int defaultPrepTime;
    private final List<Dish> dishes;
    @Setter
    private boolean hasScheduledPublish;
    @Setter
    private Id<Owner> ownerId;

    private List<DomainEvent> domainEvents;

    public Restaurant() {
        this.dayOpeningHours = new HashMap<>();
        this.domainEvents = new ArrayList<>();
        this.dishes = new ArrayList<>();
    }

    public Restaurant(Id<Restaurant> id, String name, Email email, Address address, String resPictureUrl, OpeningStatus manualOpening, Cuisine cuisine, int defaultPrepTime, boolean hasScheduledPublish, Id<Owner> ownerId) {
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
        this.domainEvents = new ArrayList<>();
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

    public OpeningStatus getManualOpening() {
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

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }

    public boolean isOpen() {

        boolean autoStatus = isAutoOpen();

        if (autoStatus && manualOpening.equals(OpeningStatus.OPEN)) this.manualOpening = OpeningStatus.AUTO;
        if (!autoStatus && manualOpening.equals(OpeningStatus.CLOSE)) this.manualOpening = OpeningStatus.AUTO;

        if (manualOpening.equals(OpeningStatus.OPEN)) return true;
        if (manualOpening.equals(OpeningStatus.CLOSE)) return false;
        return autoStatus;
    }

    private boolean isAutoOpen() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime today = LocalDateTime.now();

        OpeningHours todayOH = dayOpeningHours.get(Day.valueOf(today.getDayOfWeek().toString()));
        int openHour = LocalTime.parse(todayOH.open(), formatter).getHour();
        int closeHour = LocalTime.parse(todayOH.close(), formatter).getHour();
        int currentHour = today.getHour();

        return openHour <= currentHour && currentHour < closeHour;
    }


    public void rejectOrder(String orderId, String reason, LocalDateTime occurredAt) {

        this.domainEvents.add(new OrderRejectedEvent(
                orderId,
                reason,
                occurredAt
        ));

    }

    public void acceptOrder(String orderId, LocalDateTime occurredAt) {
        this.domainEvents.add(new OrderAcceptedEvent(
                orderId,
                occurredAt
        ));

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
