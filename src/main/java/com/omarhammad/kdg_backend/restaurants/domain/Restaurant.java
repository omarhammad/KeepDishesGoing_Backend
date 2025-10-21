package com.omarhammad.kdg_backend.restaurants.domain;

import com.omarhammad.kdg_backend.common.events.DomainEvent;
import com.omarhammad.kdg_backend.common.events.restaurantEvents.*;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.ListIsEmptyException;
import com.omarhammad.kdg_backend.restaurants.domain.enums.*;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
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

    private final List<DomainEvent> domainEvents;

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

    public void addOpeningHoursForDay(Day day, OpeningHours op) {
        this.dayOpeningHours.put(day, op);
    }

    public void setManualOpening(OpeningStatus openingStatus) {
        if (isAutoOpen() && openingStatus.equals(OpeningStatus.OPEN)) this.manualOpening = OpeningStatus.AUTO;
        else if (!isAutoOpen() && openingStatus.equals(OpeningStatus.CLOSE)) this.manualOpening = OpeningStatus.AUTO;
        else this.manualOpening = openingStatus;
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

    public Id<Owner> getOwnerId() {
        return this.ownerId;
    }

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }

    public OpeningHours findOneDayOpeningHours(Day day) {
        return this.dayOpeningHours.get(day);
    }

    public List<Dish> findAllDishes() {
        return dishes;
    }

    public void addDish(Dish dish) {
        this.dishes.add(dish);
    }


    public Dish findDishById(Id<Dish> dishId) {
        return this.dishes
                .stream()
                .filter(d -> d.getId().equals(dishId))
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Dish {%s} not found".formatted(dishId.value())));
    }

    private List<Dish> findAllDishesHasDraftVersion() {
        return this.dishes
                .stream()
                .filter(dish -> Objects.nonNull(dish.getDraft()))
                .toList();
    }

    private List<Dish> findAllDishesHasScheduledTime() {
        return this.dishes
                .stream()
                .filter(dish -> Objects.nonNull(dish.getScheduledTime()))
                .toList();
    }


    public void createDish(String name, DishType dishType, List<FoodTag> foodTags,
                           String description, BigDecimal price, String pictureUrl) {

        Dish dish = new Dish();
        dish.createId();

        dish.saveDraft(
                name,
                dishType,
                foodTags,
                description,
                price,
                pictureUrl
        );
        this.dishes.add(dish);

    }

    public void editDishDraft(Id<Dish> dishId, String name, DishType dishType,
                              List<FoodTag> foodTags, String description, BigDecimal price, String pictureUrl) {

        Dish dish = findDishById(dishId);
        dish.saveDraft(
                name,
                dishType,
                foodTags,
                description,
                price,
                pictureUrl
        );
    }

    public void publishDish(Id<Dish> dishId) {

        Dish dish = findDishById(dishId);
        dish.publish();
        this.domainEvents.add(new DishPublishedEvent(
                dish.getId().value(),
                this.getId().value(),
                LocalDateTime.now()
        ));

    }

    public void unpublishDish(Id<Dish> dishId) {

        Dish dish = findDishById(dishId);
        dish.unpublish();
        this.domainEvents.add(new DishUnPublishedEvent(
                dish.getId().value(),
                this.getId().value(),
                LocalDateTime.now()
        ));

    }

    public void publishAllDishesPendingChanges() {

        List<Dish> dishesHasDraftVersion = findAllDishesHasDraftVersion();

        if (dishesHasDraftVersion.isEmpty())
            throw new ListIsEmptyException("Restaurant {%s} has no dishes drafts to publish".formatted(this.getName()));

        for (Dish dish : dishesHasDraftVersion) {
            dish.publish();
            this.domainEvents.add(new DishPublishedEvent(
                    dish.getId().value(),
                    this.getId().value(),
                    LocalDateTime.now()
            ));
        }
        this.setHasScheduledPublish(false);
    }

    public void schedulePublishToALlPendingDishes(LocalDateTime scheduleTime) {

        List<Dish> dishesHasDraftVersion = findAllDishesHasDraftVersion();

        if (dishesHasDraftVersion.isEmpty()) {
            throw new ListIsEmptyException("no dishes drafts to publish!");
        }

        for (Dish dish : dishesHasDraftVersion) {
            dish.setScheduledTime(scheduleTime);
        }

        this.setHasScheduledPublish(true);

    }

    public void publishAllScheduledDishes() {

        List<Dish> dishesHasScheduledTime = findAllDishesHasScheduledTime();

        boolean stillHasScheduled = false;
        for (Dish dish : dishesHasScheduledTime) {
            if (dish.getScheduledTime().isAfter(LocalDateTime.now())) {
                stillHasScheduled = true;
                continue;
            }
            dish.publish();
            this.domainEvents.add(new DishPublishedEvent(
                    dish.getId().value(),
                    this.getId().value(),
                    LocalDateTime.now()
            ));
        }
        this.setHasScheduledPublish(stillHasScheduled);
    }

    public void setDishInStockStatus(Id<Dish> dishId) {

        Dish dish = findDishById(dishId);
        dish.markInStock();
        this.domainEvents.add(new DishInStockEvent(
                dish.getId().value(),
                this.getId().value(),
                LocalDateTime.now()
        ));
    }

    public void setDishOutOfStockStatus(Id<Dish> dishId) {
        Dish dish = findDishById(dishId);
        dish.markOutOfStock();
        this.domainEvents.add(new DishOutOfStockEvent(
                dish.getId().value(),
                this.getId().value(),
                LocalDateTime.now()
        ));
    }

    public boolean hasScheduledPublish() {
        return hasScheduledPublish;
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
        LocalTime openTime = LocalTime.parse(todayOH.open(), formatter);
        LocalTime closeTime = LocalTime.parse(todayOH.close(), formatter);
        LocalTime currentTime = today.toLocalTime();

        if (openTime.isBefore(closeTime)) {
            return !currentTime.isBefore(openTime) && currentTime.isBefore(closeTime);
        } else {
            return !currentTime.isBefore(openTime) || currentTime.isBefore(closeTime);
        }
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

    public void readyForPickUp(String orderId, LocalDateTime occurredAt) {

        this.domainEvents.add(new OrderReadyForPickUpEvent(
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
