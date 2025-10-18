package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.resturantRepo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "dishes")
public class DishJpaEntity {

    @Id
    @Setter
    private UUID id;

    @Setter
    private boolean isInStock;

    private LocalDateTime scheduledTime;

    @OneToOne(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private DishLiveJpa live;

    @OneToOne(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private DishDraftJpa draft;

    @ManyToOne
    private RestaurantsJpaEntity restaurant;


    public DishJpaEntity(UUID id, boolean isInStock, LocalDateTime scheduledTime) {
        this.id = id;
        this.isInStock = isInStock;
        this.scheduledTime = scheduledTime;
    }


    public void setLive(DishLiveJpa newLive) {
        if (Objects.isNull(newLive)) {
            this.live = null;
            return;
        }
        if (Objects.isNull(this.live)) {
            this.live = newLive;
            this.live.setDish(this);
        }

        this.live.setName(newLive.getName());
        this.live.setDishType(newLive.getDishType());
        this.live.setFoodTags(newLive.getFoodTags());
        this.live.setDescription(newLive.getDescription());
        this.live.setPrice(newLive.getPrice());
        this.live.setPictureUrl(newLive.getPictureUrl());

    }

    public void setDraft(DishDraftJpa newDraft) {
        if (Objects.isNull(newDraft)) {
            this.draft = null;
            return;
        }
        if (Objects.isNull(this.draft)) {
            this.draft = newDraft;
            this.draft.setDish(this);
            return;
        }

        this.draft.setName(newDraft.getName());
        this.draft.setDishType(newDraft.getDishType());
        this.draft.setFoodTags(newDraft.getFoodTags());
        this.draft.setDescription(newDraft.getDescription());
        this.draft.setPrice(newDraft.getPrice());
        this.draft.setPictureUrl(newDraft.getPictureUrl());

    }
}
