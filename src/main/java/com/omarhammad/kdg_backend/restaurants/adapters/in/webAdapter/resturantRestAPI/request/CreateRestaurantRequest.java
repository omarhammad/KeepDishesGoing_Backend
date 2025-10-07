package com.omarhammad.kdg_backend.restaurants.adapters.in.webAdapter.resturantRestAPI.request;

import com.omarhammad.kdg_backend.restaurants.adapters.in.dto.AddressDTO;
import com.omarhammad.kdg_backend.restaurants.adapters.in.dto.OpeningHoursDTO;

import java.util.Map;

public record CreateRestaurantRequest(String name,
                                      String email,
                                      AddressDTO address,
                                      String resPictureUrl,
                                      Map<String, OpeningHoursDTO> dayOpeningHoursMap,
                                      String cuisine,
                                      int defaultPrepTime) {
}
