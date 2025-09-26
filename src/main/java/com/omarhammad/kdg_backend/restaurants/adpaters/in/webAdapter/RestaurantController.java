package com.omarhammad.kdg_backend.restaurants.adpaters.in.webAdapter;

import com.omarhammad.kdg_backend.common.sharedDomain.Id;
import com.omarhammad.kdg_backend.restaurants.adpaters.in.dto.OwnerDTO;
import com.omarhammad.kdg_backend.restaurants.adpaters.in.dto.RestaurantDTO;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/restaurants")
@AllArgsConstructor
public class RestaurantController {

    private final ICreateRestaurantUseCase createRestaurantUseCase;
    private final IFindAllRestaurants findAllRestaurantsPort;
    private final ICreateDishDraftUseCase createDishDraftUseCase;
    private final IFindRestaurantById findRestaurantById;

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> findAllRestaurants() {
        List<Restaurant> restaurants = findAllRestaurantsPort.findAllRestaurants();
        List<RestaurantDTO> restaurantDTOS = restaurants
                .stream()
                .map(restaurant -> {
                    Owner owner = restaurant.getOwner();
                    return new RestaurantDTO(restaurant.getId().value(),
                            restaurant.getName(),
                            restaurant.getEmail(),
                            restaurant.getAddress(),
                            restaurant.getResPictureUrl(),
                            restaurant.getDayOpeningHours(),
                            restaurant.getCuisine(),
                            restaurant.getDefaultPrepTime(),
                            new OwnerDTO(owner.getId().value(),
                                    owner.getFirstName(),
                                    owner.getLastName(),
                                    owner.getEmail(),
                                    owner.getPhoneNumber()));
                })
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> findRestaurantById(@PathVariable String id) {
        Id restuarantId = new Id(id);
        Restaurant restaurant = findRestaurantById.findRestaurantById(restuarantId);
        Owner owner = restaurant.getOwner();

        RestaurantDTO restaurantDTO = new RestaurantDTO(restaurant.getId().value(),
                restaurant.getName(),
                restaurant.getEmail(),
                restaurant.getAddress(),
                restaurant.getResPictureUrl(),
                restaurant.getDayOpeningHours(),
                restaurant.getCuisine(),
                restaurant.getDefaultPrepTime(),
                new OwnerDTO(owner.getId().value(),
                        owner.getFirstName(),
                        owner.getLastName(),
                        owner.getEmail(),
                        owner.getPhoneNumber()));
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTO);
    }

    /*                       http POST :8080/api/restaurants   name="La Piazza"   email="contact@lapiazza.com"   resPictureUrl="https://example.com/images/lapiazza.jpg"   cuisine="ITALIAN"   defaultPrepTime:=30   ownerId="1494f798-c30a-4a6d-ac7d-502ff746c9b5"   address:='{
                         "street": "Main Street",
                         "number": "42",
                         "postalCode": "2000",
                         "city": "Antwerp",
                         "country": "Belgium"
             }'   dayOpeningHours:='{
                     "MONDAY": { "open": "11:00", "close": "22:00" },
                     "TUESDAY": { "open": "11:00", "close": "22:00" },
                     "WEDNESDAY": { "open": "11:00", "close": "22:00" },
                     "THURSDAY": { "open": "11:00", "close": "22:00" },
                     "FRIDAY": { "open": "11:00", "close": "23:00" },
                     "SATURDAY": { "open": "12:00", "close": "23:00" },
                     "SUNDAY": { "open": "12:00", "close": "21:00" }
                     }'*/
    @PostMapping
    public ResponseEntity<Void> createNewRestaurant(@RequestBody CreateRestaurantCmd cmd) {
        createRestaurantUseCase.CreateRestaurant(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /*
            http POST :8080/api/restaurants/1494f798-c30a-4a6d-ac7d-502ff746c9b5/dish \
            name="Margherita Pizza" \
            dishType="MAIN" \
            foodTags:='["VEGAN","GLUTEN"]' \
            description="Classic pizza with tomato, basil and vegan cheese" \
            price:=12.50 \
            pictureUrl="https://example.com/images/margherita.jpg"
    */
    @PostMapping("/{id}/dish")
    public ResponseEntity<Void> createNewDish(@PathVariable String id, @RequestBody CreateDishDraftCmd cmd) {
        Id restaurntId = new Id(id);
        createDishDraftUseCase.createDish(restaurntId, cmd);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
