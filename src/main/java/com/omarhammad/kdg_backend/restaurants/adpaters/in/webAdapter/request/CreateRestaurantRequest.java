package com.omarhammad.kdg_backend.restaurants.adpaters.in.webAdapter.request;

import com.omarhammad.kdg_backend.restaurants.adpaters.in.dto.AddressDTO;
import com.omarhammad.kdg_backend.restaurants.adpaters.in.dto.OpeningHoursDTO;

import java.util.Map;

public record CreateRestaurantRequest(String name,
                                      String email,
                                      AddressDTO address,
                                      String resPictureUrl,
                                      Map<String, OpeningHoursDTO> dayOpeningHoursMap,
                                      String cuisine,
                                      int defaultPrepTime,
                                      String ownerId) {
}
