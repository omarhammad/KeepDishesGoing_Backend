package com.omarhammad.kdg_backend.restaurants.adpaters.in.webAdapter;

import com.omarhammad.kdg_backend.restaurants.adpaters.in.dto.OwnerDTO;
import com.omarhammad.kdg_backend.restaurants.adpaters.in.dto.RestaurantDTO;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.ports.in.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final ICreateRestaurantUseCase createRestaurantUseCase;
    private final IFindAllRestaurants findAllRestaurantsPort;
    private final ICreateDishDraftUseCase createDishDraftUseCase;

    public RestaurantController(ICreateRestaurantUseCase createRestaurantUseCase, IFindAllRestaurants findAllRestaurantsPort, ICreateDishDraftUseCase createDishDraftUseCase) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.findAllRestaurantsPort = findAllRestaurantsPort;
        this.createDishDraftUseCase = createDishDraftUseCase;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> findAllRestaurants() {
        List<Restaurant> restaurants = findAllRestaurantsPort.findAllRestaurants();
        List<RestaurantDTO> restaurantDTOS = restaurants
                .stream()
                .map(restaurant -> {
                    Owner owner = restaurant.getOwner();
                    return new RestaurantDTO(restaurant.getId().value().toString(),
                            restaurant.getName(),
                            restaurant.getEmail(),
                            restaurant.getAddress(),
                            restaurant.getResPictureUrl(),
                            restaurant.getDayOpeningHours(),
                            restaurant.getCuisine(),
                            restaurant.getDefaultPrepTime(),
                            new OwnerDTO(owner.getId().value().toString(),
                                    owner.getFirstName(),
                                    owner.getLastName(),
                                    owner.getEmail(),
                                    owner.getPhoneNumber()));
                })
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTOS);
    }

    @PostMapping
    public ResponseEntity<Void> createNewRestaurant(@RequestBody CreateRestaurantCmd cmd) {
        createRestaurantUseCase.CreateRestaurant(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{id}/dish")
    public ResponseEntity<Void> createNewDish(@PathVariable UUID id, @RequestBody CreateDishCmd cmd) {
        createDishDraftUseCase.createDish(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
