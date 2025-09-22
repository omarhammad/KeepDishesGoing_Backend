package com.omarhammad.kdg_backend.restaurants.adpaters.in.webAdapter;

import com.omarhammad.kdg_backend.restaurants.adpaters.in.dto.RestaurantDTO;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.ports.in.CreateRestaurantCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.ICreateRestaurantUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.in.IFindAllRestaurants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final ICreateRestaurantUseCase createRestaurantUseCase;
    private final IFindAllRestaurants findAllRestaurantsPort;


    public RestaurantController(ICreateRestaurantUseCase createRestaurantUseCase, IFindAllRestaurants findAllRestaurantsPort) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.findAllRestaurantsPort = findAllRestaurantsPort;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> findAllRestaurants() {
        List<Restaurant> restaurants = findAllRestaurantsPort.findAllRestaurants();
        List<RestaurantDTO> restaurantDTOS = restaurants
                .stream()
                .map(restaurant -> new RestaurantDTO(restaurant.getId(),
                        restaurant.getName(),
                        restaurant.getEmail(),
                        restaurant.getAddress(),
                        restaurant.getResPictureUrl(),
                        restaurant.getDayOpeningHours(),
                        restaurant.getCuisine(),
                        restaurant.getDefaultPrepTime(),
                        restaurant.getOwner()))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTOS);
    }

    @PostMapping
    public ResponseEntity<Void> createNewRestaurant(@RequestBody CreateRestaurantCmd cmd) {
        createRestaurantUseCase.CreateRestaurant(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
