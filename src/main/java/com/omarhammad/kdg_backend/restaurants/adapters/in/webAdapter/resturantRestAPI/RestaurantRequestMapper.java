package com.omarhammad.kdg_backend.restaurants.adapters.in.webAdapter.resturantRestAPI;

import com.omarhammad.kdg_backend.restaurants.adapters.in.dto.*;
import com.omarhammad.kdg_backend.restaurants.adapters.in.webAdapter.resturantRestAPI.request.*;
import com.omarhammad.kdg_backend.restaurants.domain.*;
import com.omarhammad.kdg_backend.restaurants.domain.enums.Cuisine;
import com.omarhammad.kdg_backend.restaurants.domain.enums.Day;
import com.omarhammad.kdg_backend.restaurants.domain.enums.DishType;
import com.omarhammad.kdg_backend.restaurants.domain.enums.FoodTag;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.InvalidEnumValueException;
import com.omarhammad.kdg_backend.restaurants.ports.in.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RestaurantRequestMapper {


    public RestaurantDTO toRestaurantDTO(Restaurant restaurant, Owner owner) {
        return new RestaurantDTO(restaurant.getId().value(),
                restaurant.getName(),
                restaurant.getEmail(),
                restaurant.getAddress(),
                restaurant.getResPictureUrl(),
                restaurant.getDayOpeningHours(),
                restaurant.getCuisine(),
                restaurant.getDefaultPrepTime(),
                restaurant.hasScheduledPublish(),
                new OwnerDTO(owner.getId().value(),
                        owner.getFirstName(),
                        owner.getLastName(),
                        owner.getEmail(),
                        owner.getPhoneNumber()));
    }

    public CreateRestaurantCmd toCreateRestaurantCmd(String ownerId, CreateRestaurantRequest request) {

        return new CreateRestaurantCmd(
                request.name(),
                new Email(request.email()),
                toAddress(request.address()),
                request.resPictureUrl(),
                toDayOpeningHoursMap(request.dayOpeningHoursMap()),
                toEnum(request.cuisine().toUpperCase(), Cuisine.class),
                request.defaultPrepTime(),
                ownerId
        );

    }

    public CreateDishDraftCmd toCreateDishDraftCmd(CreateDishDraftRequest request) {

        List<FoodTag> foodTags = request.foodTags()
                .stream()
                .map(value -> toEnum(value, FoodTag.class))
                .toList();

        return new CreateDishDraftCmd(
                request.name(),
                toEnum(request.dishType(), DishType.class),
                foodTags,
                request.description(),
                request.price(),
                request.pictureUrl()
        );
    }

    public EditDishDraftCmd toEditDishDraftCmd(EditDishDraftRequest request) {
        List<FoodTag> foodTags = request.foodTags()
                .stream()
                .map(value -> toEnum(value, FoodTag.class))
                .toList();

        return new EditDishDraftCmd(
                request.id(),
                request.name(),
                toEnum(request.dishType(), DishType.class),
                foodTags,
                request.description(),
                request.price(),
                request.pictureUrl()
        );
    }

    public DishDTO toDishDTO(Id<Dish> dishId, boolean isInStock, LocalDateTime scheduledTime, DishData dishData) {
        if (dishData == null) {
            return null;
        }
        return new DishDTO(
                dishId.value(),
                dishData.name(),
                dishData.dishType(),
                dishData.foodTags(),
                dishData.description(),
                dishData.price(),
                dishData.pictureUrl(),
                isInStock,
                scheduledTime
        );
    }

    public Address toAddress(AddressDTO addressDTO) {
        return new Address(addressDTO.street(),
                addressDTO.number(),
                addressDTO.postalCode(),
                addressDTO.city(),
                addressDTO.country());
    }

    public Map<Day, OpeningHours> toDayOpeningHoursMap(Map<String, OpeningHoursDTO> dayOpeningHoursDTOMap) {
        Map<Day, OpeningHours> dayOpeningHoursMap = new HashMap<>();

        for (String dayKey : dayOpeningHoursDTOMap.keySet()) {
            Day day = toEnum(dayKey.toUpperCase(), Day.class);
            OpeningHoursDTO openingHoursDTO = dayOpeningHoursDTOMap.get(dayKey);
            OpeningHours openingHours = new OpeningHours(
                    openingHoursDTO.open(),
                    openingHoursDTO.close()
            );

            dayOpeningHoursMap.put(day, openingHours);
        }
        return dayOpeningHoursMap;
    }

    public <E extends Enum<E>> E toEnum(String value, Class<E> enumType) {
        try {
            return Enum.valueOf(enumType, value);

        } catch (IllegalArgumentException e) {
            throw new InvalidEnumValueException(value, enumType);
        }

    }


    public RejectOrderCmd toRejectOrderCmd(Id<Restaurant> restaurantId, RejectOrderRequest request) {

        return new RejectOrderCmd(
                restaurantId,
                new Id<>(request.orderId()),
                request.reason()
        );

    }

    public AcceptOrderCmd toAcceptOrderCmd(Id<Restaurant> restaurantId, AcceptOrderRequest request) {

        return new AcceptOrderCmd(
                restaurantId,
                new Id<>(request.orderId())
        );

    }
}
