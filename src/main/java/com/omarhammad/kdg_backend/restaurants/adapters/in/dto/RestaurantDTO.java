package com.omarhammad.kdg_backend.restaurants.adapters.in.dto;

import com.omarhammad.kdg_backend.restaurants.domain.Address;
import com.omarhammad.kdg_backend.restaurants.domain.Email;
import com.omarhammad.kdg_backend.restaurants.domain.OpeningHours;
import com.omarhammad.kdg_backend.restaurants.domain.enums.Cuisine;
import com.omarhammad.kdg_backend.restaurants.domain.enums.Day;

import java.util.Map;

public record RestaurantDTO(String id,
                            String name,
                            Email email,
                            Address address,
                            String resPictureUrl,
                            Map<Day, OpeningHours> dayOpeningHours,
                            Cuisine cuisine,
                            int defaultPrepTime,
                            OwnerDTO owner) {
}
