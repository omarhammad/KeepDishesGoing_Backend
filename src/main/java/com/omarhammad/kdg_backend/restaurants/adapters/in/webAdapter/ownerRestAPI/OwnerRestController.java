package com.omarhammad.kdg_backend.restaurants.adapters.in.webAdapter.ownerRestAPI;

import com.omarhammad.kdg_backend.restaurants.adapters.in.dto.RestaurantDTO;
import com.omarhammad.kdg_backend.restaurants.adapters.in.webAdapter.resturantRestAPI.RestaurantRequestMapper;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.ports.in.FindOwnerByIdUseCse;
import com.omarhammad.kdg_backend.restaurants.ports.in.FindRestaurantByOwnerIdUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/owners")
@AllArgsConstructor
public class OwnerRestController {


    private FindRestaurantByOwnerIdUseCase findRestaurantByOwnerIdUseCase;
    private FindOwnerByIdUseCse findOwnerByIdUseCse;
    private RestaurantRequestMapper mapper;


    @GetMapping("/{id}/restaurant")
    public ResponseEntity<RestaurantDTO> getRestaurantByOwnerId(@PathVariable String id) {

        Id<Owner> ownerId = new Id<>(id);
        Restaurant restaurant = findRestaurantByOwnerIdUseCase.findRestaurantByOwnerId(ownerId);
        Owner owner = findOwnerByIdUseCse.findOwnerById(ownerId);
        RestaurantDTO dto = mapper.toRestaurantDTO(restaurant, owner);
        return ResponseEntity.status(HttpStatus.OK).body(dto);

    }

}
