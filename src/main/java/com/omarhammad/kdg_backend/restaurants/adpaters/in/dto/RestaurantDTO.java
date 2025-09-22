package com.omarhammad.kdg_backend.restaurants.adpaters.in.dto;

import com.omarhammad.kdg_backend.common.sharedDomain.Address;
import com.omarhammad.kdg_backend.common.sharedDomain.Email;
import com.omarhammad.kdg_backend.restaurants.domain.OpeningHours;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import com.omarhammad.kdg_backend.restaurants.domain.utils.Cuisine;
import com.omarhammad.kdg_backend.restaurants.domain.utils.Day;

import java.util.Map;
import java.util.UUID;

public record RestaurantDTO(UUID id,
                            String name,
                            Email email,
                            Address address,
                            String resPictureUrl,
                            Map<Day, OpeningHours> dayOpeningHours,
                            Cuisine cuisine,
                            int defaultPrepTime,
                            Owner owner) {
}
