package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.dishesRepo;

import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.dishesRepo.entites.DishDraftJpa;
import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.dishesRepo.entites.DishJpaEntity;
import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.dishesRepo.entites.DishLiveJpa;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.DishData;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.enums.DishType;
import com.omarhammad.kdg_backend.restaurants.domain.enums.FoodTag;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.out.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class DishesJpaAdapter implements SaveDishDraftPort, EditDishPort, LoadDishPort {


    private final DishesRepository repository;


    @Override

    public void save(Id<Restaurant> restaurantId, Dish dish) {

        DishJpaEntity dishJpaEntity = toDishJpaEntity(restaurantId, dish);
        repository.save(dishJpaEntity);

    }

    @Override
    public void edit(Id<Restaurant> restaurantId, Dish updatedDish) {

        String dishId = updatedDish.getId().value();
        DishJpaEntity dishJpaEntity = repository.findById(UUID.fromString(dishId))
                .orElseThrow(() -> new EntityNotFoundException("Dish {%s} not found".formatted(dishId)));

        dishJpaEntity.setInStock(updatedDish.isInStock());
        dishJpaEntity.setScheduledTime(updatedDish.getScheduledTime());
        dishJpaEntity.setLive(toDishLiveJpa(updatedDish.getLive()));
        dishJpaEntity.setDraft(toDishDraftJpa(updatedDish.getDraft()));

        repository.save(dishJpaEntity);

    }


    @Override
    public Optional<Dish> findById(Id<Restaurant> restaurantId, Id<Dish> dishId) {

        return repository.findById(UUID.fromString(dishId.value())).map(this::toDish);

    }

    @Override
    public List<Dish> findDishesByRestaurantId(Id<Restaurant> restaurantId) {

        return repository.findAllByRestaurant(UUID.fromString(restaurantId.value()))
                .stream()
                .map(this::toDish)
                .toList();
    }


    private DishJpaEntity toDishJpaEntity(Id<Restaurant> restaurantId, Dish dish) {

        DishJpaEntity dishJpaEntity = new DishJpaEntity(
                UUID.fromString(dish.getId().value()),
                dish.isInStock(),
                dish.getScheduledTime(),
                UUID.fromString(restaurantId.value())
        );

        dishJpaEntity.setDraft(toDishDraftJpa(dish.getDraft()));
        dishJpaEntity.setLive(toDishLiveJpa(dish.getLive()));

        return dishJpaEntity;
    }

    private DishLiveJpa toDishLiveJpa(DishData live) {
        if (Objects.isNull(live)) return null;
        DishLiveJpa dishLiveJpa =
                new DishLiveJpa(
                        live.name(),
                        live.dishType().name(),
                        live.description(),
                        live.price(),
                        live.pictureUrl()
                );
        live.foodTags().forEach(foodTag -> dishLiveJpa.addFoodTags(foodTag.name()));
        return dishLiveJpa;
    }

    private DishDraftJpa toDishDraftJpa(DishData draft) {
        if (Objects.isNull(draft)) return null;
        DishDraftJpa dishDraftJpa =
                new DishDraftJpa(
                        draft.name(),
                        draft.dishType().name(),
                        draft.description(),
                        draft.price(),
                        draft.pictureUrl()
                );
        draft.foodTags().forEach(foodTag -> dishDraftJpa.addFoodTags(foodTag.name()));
        return dishDraftJpa;
    }

    private Dish toDish(DishJpaEntity dishJpaEntity) {

        return new Dish(
                new Id<>(dishJpaEntity.getId().toString()),
                dishJpaEntity.isInStock(),
                dishJpaEntity.getScheduledTime(),
                toDishDataLive(dishJpaEntity.getLive()),
                toDishDataDraft(dishJpaEntity.getDraft())
        );

    }

    private DishData toDishDataLive(DishLiveJpa liveJpa) {
        if (Objects.isNull(liveJpa)) return null;
        DishData liveData = new DishData(
                liveJpa.getName(),
                DishType.valueOf(liveJpa.getDishType()),
                liveJpa.getDescription(),
                liveJpa.getPrice(),
                liveJpa.getPictureUrl()
        );
        liveJpa.getFoodTags().forEach(foodTagS -> liveData.addFoodTag(FoodTag.valueOf(foodTagS)));
        return liveData;
    }

    private DishData toDishDataDraft(DishDraftJpa draftJpa) {
        if (Objects.isNull(draftJpa)) return null;
        DishData draftData = new DishData(
                draftJpa.getName(),
                DishType.valueOf(draftJpa.getDishType()),
                draftJpa.getDescription(),
                draftJpa.getPrice(),
                draftJpa.getPictureUrl()
        );

        draftJpa.getFoodTags().forEach(foodTagS -> draftData.addFoodTag(FoodTag.valueOf(foodTagS)));
        return draftData;
    }

}
