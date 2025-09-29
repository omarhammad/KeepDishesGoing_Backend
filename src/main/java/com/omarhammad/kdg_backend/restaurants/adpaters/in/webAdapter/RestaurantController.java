package com.omarhammad.kdg_backend.restaurants.adpaters.in.webAdapter;

import com.omarhammad.kdg_backend.common.sharedDomain.Id;
import com.omarhammad.kdg_backend.restaurants.adpaters.in.dto.DishDTO;
import com.omarhammad.kdg_backend.restaurants.adpaters.in.dto.OwnerDTO;
import com.omarhammad.kdg_backend.restaurants.adpaters.in.dto.RestaurantDTO;
import com.omarhammad.kdg_backend.restaurants.domain.Dish;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.ports.in.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@AllArgsConstructor
public class RestaurantController {

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final FindAllRestaurantsUseCase findAllRestaurantsUseCasePort;
    private final CreateDishDraftUseCase createDishDraftUseCase;
    private final FindRestaurantByIdUseCase findRestaurantByIdUseCase;
    private final EditDishDraftUseCase editDishDraftUseCase;
    private final FindDishForRestaurantByIdUseCase findDishForRestaurantByIdUseCase;
    private final FindDishesByRestaurantIdUseCase findDishesByRestaurantIdUseCase;
    private final Logger log = LoggerFactory.getLogger(RestaurantController.class);

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        List<Restaurant> restaurants = findAllRestaurantsUseCasePort.findAllRestaurants();
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

        if (restaurantDTOS.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable String id) {
        Id<Restaurant> restuarantId = new Id<>(id);
        Restaurant restaurant = findRestaurantByIdUseCase.findRestaurantById(restuarantId);
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

    /* http POST :8080/api/restaurants   name="La Piazza"   email="contact@lapiazza.com"   resPictureUrl="https://example.com/images/lapiazza.jpg"   cuisine="ITALIAN"   defaultPrepTime:=30   ownerId="1494f798-c30a-4a6d-ac7d-502ff746c9b5"   address:='{
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
    public ResponseEntity<Void> makeNewRestaurant(@RequestBody CreateRestaurantCmd cmd) {
        createRestaurantUseCase.CreateRestaurant(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}/dishes")
    public ResponseEntity<List<DishDTO>> getAllRestaurantDishes(@PathVariable String id) {
        Id<Restaurant> restaurantId = new Id<>(id);
        List<Dish> dishes = findDishesByRestaurantIdUseCase.findDishesByRestaurantId(restaurantId);

        if (dishes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<DishDTO> dishDTOS = dishes.stream().map(dish -> new DishDTO(
                dish.getId().value(),
                dish.getName(),
                dish.getDishType(),
                dish.getFoodTags(),
                dish.getDescription(),
                dish.getPrice(),
                dish.getPictureUrl()
        )).toList();

        return ResponseEntity.status(HttpStatus.OK).body(dishDTOS);

    }

    @GetMapping("/{id}/dishes/{dhId}")
    public ResponseEntity<DishDTO> getDishOfARestaurant(@PathVariable String dhId, @PathVariable String id) {

        Id<Restaurant> restaurantId = new Id<>(id);
        Id<Dish> dishId = new Id<>(dhId);
        Dish dish = findDishForRestaurantByIdUseCase.findDishOfARestaurantById(restaurantId, dishId);
        DishDTO dto = new DishDTO(
                dish.getId().value(),
                dish.getName(),
                dish.getDishType(),
                dish.getFoodTags(),
                dish.getDescription(),
                dish.getPrice(),
                dish.getPictureUrl()
        );

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    /* http POST :8080/api/restaurants/17b354fc-13b6-4eb0-b6f4-0a3fab83e615/dishes \
                   name="Margherita Pizza" \
                   dishType="MAIN" \
                   foodTags:='["VEGAN","GLUTEN"]' \
                   description="Classic pizza with tomato, basil and vegan cheese" \
                   price:=12.50 \
                   pictureUrl="https://example.com/images/margherita.jpg"
    */
    @PostMapping("/{id}/dishes")
    public ResponseEntity<Void> makeNewDishDraft(@PathVariable String id, @RequestBody CreateDishDraftCmd cmd) {
        Id<Restaurant> restaurntId = new Id<>(id);
        createDishDraftUseCase.createDish(restaurntId, cmd);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
    http PUT :8080/api/restaurants/4b511c8c-d762-4d15-bcf4-9a691c0be90e/dishes/d707724f-8bb6-43d3-ba04-ecc74cc444bc \
    id="d707724f-8bb6-43d3-ba04-ecc74cc444bc" \
    name="Margherita Pizza - Updated" \
    dishType="MAIN" \
    foodTags:='["VEGAN","LACTOSE"]' \
    description="Updated description: with extra basil and olive oil" \
    price:=13.75 \
    pictureUrl="https://example.com/images/margherita-updated.jpg"
    */
    @PutMapping("/{id}/dishes/{dId}")
    public ResponseEntity<Void> editDishDraft(@PathVariable String id, @PathVariable String dId, @RequestBody EditDishDraftCmd cmd) {

        if (!dId.equals(cmd.id()))
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        Id<Restaurant> restaurantId = new Id<>(id);
        Id<Dish> dishId = new Id<>(dId);
        editDishDraftUseCase.editDish(restaurantId, dishId, cmd);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    // TODO (MONDAY 30th SEP)
    //  1) MAKE FIND ALL DISHES FOR A RESTAURANT, THEN START WITH
    //  2) PUBLISH/UNPUBLISH,
    //  3) APPLY_PUBLISH_ON_ALL_PENDING_MULTI
    //  4) SCHEDULE SELECTED DISHES TO PUBLISH
    //  5) MARK DISH IN_STOCK/OUT_OF_STOCK


}
