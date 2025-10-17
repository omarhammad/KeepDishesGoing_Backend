package com.omarhammad.kdg_backend.restaurants.adapters.in.webAdapter.resturantRestAPI;

import com.omarhammad.kdg_backend.restaurants.adapters.in.dto.*;
import com.omarhammad.kdg_backend.restaurants.adapters.in.dto.generic.ResponseDTO;
import com.omarhammad.kdg_backend.restaurants.adapters.in.exceptions.InvalidDishStateValue;
import com.omarhammad.kdg_backend.restaurants.adapters.in.exceptions.WrongOpeningStatusValueException;
import com.omarhammad.kdg_backend.restaurants.adapters.in.webAdapter.resturantRestAPI.request.*;
import com.omarhammad.kdg_backend.restaurants.domain.*;
import com.omarhammad.kdg_backend.restaurants.ports.in.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Slf4j
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
    private final SchedulePublishToAllPendingDishesUseCase schedulePublishToAllPendingDishesUseCase;
    private final FindOwnerByIdUseCse findOwnerByIdUseCse;
    private final ManualOpenCloseRestaurantUseCase manualOpenCloseRestaurantUseCase;
    private final RejectOrderUseCase rejectOrderUseCase;
    private final AcceptOrderUseCase acceptOrderUseCase;
    private final RestaurantRequestMapper requestMapper;

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        List<Restaurant> restaurants = findAllRestaurantsUseCasePort.findAllRestaurants();
        List<RestaurantDTO> restaurantDTOS = restaurants
                .stream()
                .map(restaurant -> {
                    Owner owner = findOwnerByIdUseCse.findOwnerById(restaurant.getOwnerId());
                    return requestMapper.toRestaurantDTO(restaurant, owner);
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
        Owner owner = findOwnerByIdUseCse.findOwnerById(restaurant.getOwnerId());
        RestaurantDTO restaurantDTO = requestMapper.toRestaurantDTO(restaurant, owner);

        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTO);
    }


    /*http POST :8080/api/restaurants \
    name="La Piazza" \
    email="contact@lapiazza.com" \
    resPictureUrl="https://example.com/images/lapiazza.jpg" \
    cuisine="ITALIAN" \
    defaultPrepTime:=30 \
    ownerId="550e8400-e29b-41d4-a716-446655440000" \
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
    public ResponseEntity<ResponseDTO> makeNewRestaurant(@RequestBody CreateRestaurantRequest request, @AuthenticationPrincipal Jwt jwt) {

        String ownerId = jwt.getSubject();
        CreateRestaurantCmd cmd = requestMapper.toCreateRestaurantCmd(ownerId, request);
        createRestaurantUseCase.CreateRestaurant(cmd);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(HttpStatus.CREATED.value(), "Restaurant created successfully"));
    }

    @GetMapping("/{id}/open-status")
    public ResponseEntity<RestaurantOpeningStatusDTO> checkManualOpining(@PathVariable String id) {

        Id<Restaurant> restaurantId = new Id<>(id);
        Restaurant restaurant = findRestaurantByIdUseCase.findRestaurantById(restaurantId);

        RestaurantOpeningStatusDTO dto = new RestaurantOpeningStatusDTO(restaurant.isOpen());

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }


    @PostMapping("/{id}/open-status")
    public ResponseEntity<ResponseDTO> setManualOpining(@PathVariable String id, @RequestBody RestaurantOpeningStatusRequest request) {

        Id<Restaurant> restaurantId = new Id<>(id);
        String message;
        if (request.openStatus().equalsIgnoreCase("open")) {
            manualOpenCloseRestaurantUseCase.open(restaurantId);
            message = "Restaurant is set to OPEN";

        } else if (request.openStatus().equalsIgnoreCase("close")) {
            manualOpenCloseRestaurantUseCase.close(restaurantId);
            message = "Restaurant is set to CLOSE";

        } else if (request.openStatus().equalsIgnoreCase("auto")) {
            manualOpenCloseRestaurantUseCase.auto(restaurantId);
            message = "Restaurant is set to AUTO";
        } else {
            throw new WrongOpeningStatusValueException("openStatus should be ['AUTO', 'OPEN','CLOSE']");
        }


        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(
                HttpStatus.OK.value(),
                message
        ));
    }

    @GetMapping("/{id}/dishes")
    public ResponseEntity<List<DishDTO>> getAllRestaurantDishes(@PathVariable String id, @RequestParam String state) {
        Id<Restaurant> restaurantId = new Id<>(id);
        List<Dish> dishes = findDishesByRestaurantIdUseCase.findDishesByRestaurantId(restaurantId);

        List<DishDTO> dishDTOS;

        if (state.equalsIgnoreCase("draft")) {
            dishDTOS = dishes.stream()
                    .map(dish -> requestMapper.toDishDTO(dish.getId(), dish.isInStock(), dish.getScheduledTime(), dish.getDraft()))
                    .filter(Objects::nonNull)
                    .toList();
        } else if (state.equalsIgnoreCase("live")) {
            dishDTOS = dishes.stream()
                    .map(dish -> requestMapper.toDishDTO(dish.getId(), dish.isInStock(), dish.getScheduledTime(), dish.getLive()))
                    .filter(Objects::nonNull)
                    .toList();
        } else {
            throw new InvalidDishStateValue("State must be provided as { 'live' or 'draft' }");
        }

        return ResponseEntity.status(HttpStatus.OK).body(dishDTOS);

    }

    @GetMapping("/{id}/dishes/{dId}")
    public ResponseEntity<DishDTO> getDishOfARestaurant(@PathVariable String id, @PathVariable String dId, @RequestParam String state) {

        Id<Restaurant> restaurantId = new Id<>(id);
        Id<Dish> dishId = new Id<>(dId);
        Dish dish = findDishForRestaurantByIdUseCase.findDishOfARestaurantById(restaurantId, dishId);

        DishDTO dto;
        if (state.equalsIgnoreCase("draft")) {
            dto = requestMapper.toDishDTO(dish.getId(), dish.isInStock(), dish.getScheduledTime(), dish.getDraft());

        } else if (state.equalsIgnoreCase("live")) {
            dto = requestMapper.toDishDTO(dish.getId(), dish.isInStock(), dish.getScheduledTime()
                    , dish.getLive());
        } else {
            throw new InvalidDishStateValue("State must be provided as { 'live' or 'draft' }");
        }

        return Objects.isNull(dto) ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    /* http POST :8080/api/restaurants/8ac77cab-22d2-4684-8a15-f326f5772c77/dishes \
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
    http PATCH :8080/api/restaurants/8ac77cab-22d2-4684-8a15-f326f5772c77/dishes/66cb71b9-a739-4cd8-a463-f590061610b7 \
    id="66cb71b9-a739-4cd8-a463-f590061610b7" \
    name="Margherita Pizza - Updated" \
    dishType="MAIN" \
    foodTags:='["VEGAN","LACTOSE"]' \
    description="Updated description: with extra basil and olive oil" \
    price:=25.75 \
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
        http PATCH :8080/api/restaurants/11111111-1111-1111-1111-111111111111/dishes/aaaa1111-aaaa-1111-aaaa-111111111111/published \
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
    http PATCH :8080/api/restaurants/8ac77cab-22d2-4684-8a15-f326f5772c77/dishes/66cb71b9-a739-4cd8-a463-f590061610b7/stock \
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

    /* http POST :8080/api/restaurants/11111111-1111-1111-1111-111111111111/dishes/publish-all */
    @PostMapping("/{id}/dishes/publish-all")
    public ResponseEntity<ResponseDTO> publishAllPendingDishes(@PathVariable String id) {
        Id<Restaurant> restaurantId = new Id<>(id);
        publishAllPendingDishesUseCase.publishAllPendingDishes(restaurantId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.NO_CONTENT.value(), "All pending dishes published successfully"));
    }

    /*
    http POST :8080/api/restaurants/11111111-1111-1111-1111-111111111111/dishes/schedule-publish \
    scheduleTime="2025-10-04T00:38:00"
  */
    @PostMapping("/{id}/dishes/schedule-publish")
    public ResponseEntity<ResponseDTO> schedulePublishToAllPendingDishes(@PathVariable String id, @RequestBody SchedulePublishRequest request) {

        Id<Restaurant> restaurantId = new Id<>(id);
        SchedulePublishCmd cmd = new SchedulePublishCmd(request.scheduleTime());

        schedulePublishToAllPendingDishesUseCase
                .schedulePublish(restaurantId, cmd);


        String ScheduleTimeStr = request.scheduleTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm a"));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(
                        HttpStatus.OK.value(),
                        "All pending dishes scheduled to publish at {%s} ".formatted(ScheduleTimeStr))
                );
    }


    @PostMapping("/{id}/reject-order")
    public ResponseEntity<ResponseDTO> rejectOrder(@PathVariable String id, @RequestBody RejectOrderRequest request) {

        Id<Restaurant> restaurantId = new Id<>(id);
        RejectOrderCmd cmd = requestMapper.toRejectOrderCmd(restaurantId, request);
        rejectOrderUseCase.reject(cmd);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(
                HttpStatus.OK.value(),
                "Order rejected"
        ));
    }


    @PostMapping("/{id}/accept-order")
    public ResponseEntity<ResponseDTO> acceptOrder(@PathVariable String id, @RequestBody AcceptOrderRequest request) {

        Id<Restaurant> restaurantId = new Id<>(id);
        AcceptOrderCmd cmd = requestMapper.toAcceptOrderCmd(restaurantId, request);
        acceptOrderUseCase.accept(cmd);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(
                HttpStatus.OK.value(),
                "Order accepted"
        ));
    }

    // TODO (Tuesday 6th OCT)
    //  1) Manual opening/closing use case - DONE
    //  2) Understand the KeyCloak for Auth - DONE
    //  3) Implement the Owner Auth - DONE
    //  4) Read the RabbitMQ - DONE
    //  5) Make events
    //       ORDER_ACCEPTED -DONE
    //       ORDER_REJECTED - DONE
    //       ORDER_DECLINED AFTER 5MIN - DONE
    //       DISH PUBLISHED
    //       DISH_UNPUBLISHED
    //       DISH_OUT_OF_STOCK
    //       DISH_IN_STOCK
    //  6) publish all these events


}
