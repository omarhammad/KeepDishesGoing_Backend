package com.omarhammad.kdg_backend.restaurants.adpaters.in.webAdapter;

import com.omarhammad.kdg_backend.restaurants.adpaters.in.dto.*;
import com.omarhammad.kdg_backend.restaurants.adpaters.in.dto.generic.ResponseDTO;
import com.omarhammad.kdg_backend.restaurants.adpaters.in.webAdapter.request.*;
import com.omarhammad.kdg_backend.restaurants.domain.*;
import com.omarhammad.kdg_backend.restaurants.ports.in.*;
import lombok.AllArgsConstructor;
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
    private final SetDishPublishStatusUseCase setDishPublishStatusUseCase;
    private final SetDishStockStatusUseCase setDishStockStatusUseCase;
    private final PublishAllPendingDishesUseCase publishAllPendingDishesUseCase;
    private final RestaurantRequestMapper requestMapper;

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

    /*http POST :8080/api/restaurants \
    name="La Piazza" \
    email="contact@lapiazza.com" \
    resPictureUrl="https://example.com/images/lapiazza.jpg" \
    cuisine="ITALIAN" \
    defaultPrepTime:=30 \
    ownerId="1494f798-c30a-4a6d-ac7d-502ff746c9b5" \
    address:='{
        "street": "Main Street",
        "number": 42,
        "postalCode": "2000",
        "city": "Antwerp",
        "country": "Belgium"
    }' \
    dayOpeningHoursMap:='{
        "MONDAY": { "open": "11:00", "close": "22:00" },
        "TUESDAY": { "open": "11:00", "close": "22:00" },
        "WEDNESDAY": { "open": "11:00", "close": "22:00" },
        "THURSDAY": { "open": "11:00", "close": "22:00" },
        "FRIDAY": { "open": "11:00", "close": "23:00" },
        "SATURDAY": { "open": "12:00", "close": "23:00" },
        "SUNDAY": { "open": "12:00", "close": "21:00" }
    }'
    */
    @PostMapping
    public ResponseEntity<ResponseDTO> makeNewRestaurant(@RequestBody CreateRestaurantRequest request) {
        CreateRestaurantCmd cmd = requestMapper.toCreateRestaurantCmd(request);
        createRestaurantUseCase.CreateRestaurant(cmd);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(HttpStatus.CREATED.value(), "Restaurant created successfully"));
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

    /* http POST :8080/api/restaurants/4a5a2a19-572e-413c-baea-3f070ef1d772/dishes \
                   name="Margherita Pizza" \
                   dishType="MAIN" \
                   foodTags:='["VEGAN","GLUTEN"]' \
                   description="Classic pizza with tomato, basil and vegan cheese" \
                   price:=12.50 \
                   pictureUrl="https://example.com/images/margherita.jpg"
    */
    @PostMapping("/{id}/dishes")
    public ResponseEntity<ResponseDTO> makeNewDishDraft(@PathVariable String id, @RequestBody CreateDishDraftRequest request) {
        Id<Restaurant> restaurntId = new Id<>(id);
        CreateDishDraftCmd cmd = requestMapper.toCreateDishDraftCmd(request);
        createDishDraftUseCase.createDish(restaurntId, cmd);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(HttpStatus.CREATED.value(), "Dish draft created successfully"));
    }

    /*
    http PATCH :8080/api/restaurants/c170ead7-c77c-40c3-8be6-17a5a8e17ea2/dishes/0c44ba6f-46a2-4231-8241-7c8ff9f78815 \
    id="0c44ba6f-46a2-4231-8241-7c8ff9f78815" \
    name="Margherita Pizza - Updated" \
    dishType="MAIN" \
    foodTags:='["VEGAN","GLUTSDEN"]' \
    description="Updated description: with extra basil and olive oil" \
    price:=15.75 \
    pictureUrl="https://example.com/images/margherita-updated.jpg"
    */
    @PatchMapping("/{id}/dishes/{dId}")
    public ResponseEntity<ResponseDTO> editDishDraft(@PathVariable String id, @PathVariable String dId, @RequestBody EditDishDraftRequest request) {

        if (!dId.equals(request.id()))
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(HttpStatus.CONFLICT.value(),
                            "Conflict in the ID between request body and path"));

        Id<Restaurant> restaurantId = new Id<>(id);
        Id<Dish> dishId = new Id<>(dId);
        EditDishDraftCmd cmd = requestMapper.toEditDishDraftCmd(request);
        editDishDraftUseCase.editDishDraft(restaurantId, dishId, cmd);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Dish draft updated successfully"));
    }

    /*
        http PATCH :8080/api/restaurants/c2547bab-bf76-431b-ace5-3450763e9113/dishes/9d34c045-2b74-4278-ab64-6f3b1f6b0e65/published \
           isPublished:=true
        */
    @PatchMapping("/{id}/dishes/{dId}/published")
    public ResponseEntity<ResponseDTO> setDishPublishStatus(@PathVariable String dId, @PathVariable String id, @RequestBody DishPublishStatusRequest request) {

        Id<Restaurant> restaurantId = new Id<>(id);
        Id<Dish> dishId = new Id<>(dId);

        SetDishPublishStatusCmd cmd = new SetDishPublishStatusCmd(request.isPublished());
        setDishPublishStatusUseCase.setPublishDishStatus(restaurantId, dishId, cmd);

        String message;
        if (request.isPublished()) {
            message = "Dish published successfully";
        } else {
            message = "Dish unPublished successfully";

        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), message));
    }

    /*
    http PATCH :8080/api/restaurants/c2547bab-bf76-431b-ace5-3450763e9113/dishes/9d34c045-2b74-4278-ab64-6f3b1f6b0e65/stock \
       isInStock:=true
    */
    @PatchMapping("/{id}/dishes/{dId}/stock")
    public ResponseEntity<ResponseDTO> setDishStockStatus(@PathVariable String id, @PathVariable String dId, @RequestBody DishStockStatusRequest request) {
        Id<Restaurant> restaurantId = new Id<>(id);
        Id<Dish> dishId = new Id<>(dId);

        SetDishStockStatusCmd cmd = new SetDishStockStatusCmd(request.isInStock());
        setDishStockStatusUseCase.setDishStockStatus(restaurantId, dishId, cmd);
        String message;

        if (request.isInStock()) {
            message = "Dish stock changed to {IN_STOCK} successfully";
        } else {
            message = "Dish stock changed to {OUT_OF_STOCK} successfully";

        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), message));
    }


    @PostMapping("/{id}/dishes/publish-all")
    public ResponseEntity<ResponseDTO> publishAllPendingDishes(@PathVariable String id) {
        Id<Restaurant> restaurantId = new Id<>(id);
        publishAllPendingDishesUseCase.publishAllPendingDishes(restaurantId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.NO_CONTENT.value(), "All pending dishes published successfully"));
    }

    // TODO (Tuesday 30th SEP)
    //  1) MAKE FIND ALL DISHES FOR A RESTAURANT, THEN START WITH - DONE
    //  2) PUBLISH/UNPUBLISH - DONE
    //   3) MARK DISH IN_STOCK/OUT_OF_STOCK - DONE
    //  4) APPLY_PUBLISH_ON_ALL_PENDING_MULTI - DONE
    //  5) SCHEDULE SELECTED DISHES TO PUBLISH


}
