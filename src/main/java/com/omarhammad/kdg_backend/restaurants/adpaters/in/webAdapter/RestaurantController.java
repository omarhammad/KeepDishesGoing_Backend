package com.omarhammad.kdg_backend.restaurants.adpaters.in.webAdapter;

import com.omarhammad.kdg_backend.restaurants.ports.in.CreateRestaurantCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.ICreateRestaurantUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final ICreateRestaurantUseCase createRestaurantUseCase;

    public RestaurantController(ICreateRestaurantUseCase createRestaurantUseCase) {
        this.createRestaurantUseCase = createRestaurantUseCase;
    }


    @PostMapping("")
    public ResponseEntity<Void> createNewRestaurant(@RequestBody CreateRestaurantCmd cmd) {
        createRestaurantUseCase.CreateRestaurant(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
