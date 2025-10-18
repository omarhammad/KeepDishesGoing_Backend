package com.omarhammad.kdg_backend.restaurants.domain;

import com.omarhammad.kdg_backend.restaurants.domain.enums.DishType;
import com.omarhammad.kdg_backend.restaurants.domain.enums.FoodTag;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.DishAlreadyUnPublishedException;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.DishAlreadyPublishedException;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@ToString
@NoArgsConstructor
public class Dish {

    @Setter
    private Id<Dish> id;
    private boolean isInStock;
    private LocalDateTime scheduledTime;
    private DishData live;
    private DishData draft;


    public Dish(Id<Dish> id, boolean isInStock, LocalDateTime scheduledTime, DishData live, DishData draft) {
        this.id = id;
        this.isInStock = isInStock;
        this.scheduledTime = scheduledTime;
        this.live = live;
        this.draft = draft;
    }

    public void saveDraft(String name, DishType dishType, List<FoodTag> foodTags,
                          String description, BigDecimal price, String pictureUrl) {
        this.draft = new DishData(
                name,
                dishType,
                description,
                price,
                pictureUrl
        );
        foodTags.forEach(this.draft::addFoodTag);
    }

    public void publish() {
        if (Objects.isNull(this.draft)) {
            throw new DishAlreadyPublishedException("Dish {%s} already published".formatted(this.live.name()));
        }

        this.live = this.draft;
        this.draft = null;
        this.scheduledTime = null;
    }

    public void unpublish() {

        if (Objects.isNull(this.live)) {
            throw new DishAlreadyUnPublishedException("Dish already not published");
        }

        if (Objects.isNull(this.draft)) {
            this.draft = this.live;
        }
        this.live = null;
    }

    public Id<Dish> getId() {
        return id;
    }

    public void createId() {
        this.id = Id.createNewId();
    }

    public boolean isPublished() {
        return Objects.nonNull(this.live);
    }

    public boolean isInStock() {
        return isInStock;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        if (Objects.nonNull(this.draft)) {
            this.scheduledTime = scheduledTime;
        }
    }

    public DishData getDraft() {
        return draft;
    }

    public DishData getLive() {
        return live;
    }


    public void markInStock() {
        this.isInStock = true;
    }

    public void markOutOfStock() {
        this.isInStock = false;
    }
}
