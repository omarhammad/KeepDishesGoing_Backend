package com.omarhammad.kdg_backend.orders.core;

import com.omarhammad.kdg_backend.orders.core.exceptions.ClosedRestaurantException;
import com.omarhammad.kdg_backend.orders.core.exceptions.DishesFromDifferentRestaurantException;
import com.omarhammad.kdg_backend.orders.core.exceptions.InvalidDishesException;
import com.omarhammad.kdg_backend.orders.domain.DishProjection;
import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.domain.Order;
import com.omarhammad.kdg_backend.orders.domain.enums.DishLiveStatus;
import com.omarhammad.kdg_backend.orders.domain.enums.DishStockStatus;
import com.omarhammad.kdg_backend.orders.ports.in.CreateOrderCmd;
import com.omarhammad.kdg_backend.orders.ports.in.CreateOrderUseCase;
import com.omarhammad.kdg_backend.orders.ports.out.LoadDishProjectionPort;
import com.omarhammad.kdg_backend.orders.core.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.orders.ports.out.RestaurantAvailabilityPort;
import com.omarhammad.kdg_backend.orders.ports.out.SaveOrderPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultCreateOrderUseCase implements CreateOrderUseCase {

    private final LoadDishProjectionPort loadDishProjectionPort;
    private final RestaurantAvailabilityPort restaurantAvailabilityPort;
    private final SaveOrderPort saveOrderPort;

    @Override
    public Id<Order> createOrder(CreateOrderCmd cmd) {

        List<DishProjection> orderDishes = new ArrayList<>();
        List<Id<DishProjection>> dishesNotFound = new ArrayList<>();

        for (Id<DishProjection> dishProjectionId : cmd.dishes()) {
            DishProjection dish = loadDishProjectionPort.findById(dishProjectionId).orElse(null);

            if (Objects.nonNull(dish)) {
                orderDishes.add(dish);
            } else {
                dishesNotFound.add(dishProjectionId);
            }

        }

        if (!dishesNotFound.isEmpty())
            throw new EntityNotFoundException(dishesNotFound.stream()
                    .map(id -> "\"" + id.value() + "\"")
                    .collect(Collectors.joining(",", "{\"dishesNotFound\":[", "]}")));


        // 1) check if all dishes comes from the same restaurant and get the restaurantId if true
        Id restaurantId = getDishesRestaurntId(orderDishes);

        //2)check if the restaurant is open
        boolean isRestaurantOpen = restaurantAvailabilityPort.isRestaurantOpen(restaurantId);

        if (!isRestaurantOpen)
            throw new ClosedRestaurantException("Can't be ordered from a closed restaurant");

        // 3) check if all dishes published and in_stock
        List<DishProjection> invalidDishes = orderDishes.stream()
                .filter(d ->
                        d.getLiveStatus().equals(DishLiveStatus.UNPUBLISHED) ||
                                d.getStockStatus().equals(DishStockStatus.OUT_OF_STOCK)).toList();

        // 4) if it's not empty throw an exception with all invalid dishes
        if (!invalidDishes.isEmpty())
            throw new InvalidDishesException(
                    invalidDishes.stream()
                            .map(this::getDishAsString)
                            .collect(Collectors.joining(",", "{", "}"))
            );


        BigDecimal totalOrderPrice = orderDishes
                .stream()
                .reduce(
                        new BigDecimal(0),
                        (total, d) -> total.add(d.getPrice()),
                        BigDecimal::add
                );

        Order order = new Order();

        order.createOrder(
                orderDishes.stream().map(d -> new Id(d.getDishId().value())).toList(),
                restaurantId,
                totalOrderPrice
        );


        Order savedOrder = saveOrderPort.save(order);

        return savedOrder.getId();
    }

    private String getDishAsString(DishProjection dish) {
        return "\"%s\": {\"restaurantId\": \"%s\", \"liveStatus\": \"%s\", \"stockStatus\": \"%s\"}"
                .formatted(
                        dish.getDishId().value(),
                        dish.getRestaurantId().value(),
                        dish.getLiveStatus(),
                        dish.getStockStatus()
                );
    }

    private Id getDishesRestaurntId(List<DishProjection> dishesFound) {

        List<Id> dishesRestaurantsIds = dishesFound.stream()
                .map(DishProjection::getRestaurantId).toList();
        boolean allFromSameRestaurant = dishesRestaurantsIds.stream().distinct().count() == 1;

        if (!allFromSameRestaurant)
            throw new DishesFromDifferentRestaurantException("Not allowed checkout as dishes not from same restaurant");

        return dishesRestaurantsIds.getFirst();
    }
}
