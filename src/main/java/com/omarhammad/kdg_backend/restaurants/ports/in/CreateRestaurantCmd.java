package com.omarhammad.kdg_backend.restaurants.ports.in;

import com.omarhammad.kdg_backend.common.sharedDomain.Address;
import com.omarhammad.kdg_backend.common.sharedDomain.Email;
import com.omarhammad.kdg_backend.restaurants.domain.OpeningHours;
import com.omarhammad.kdg_backend.restaurants.domain.utils.Cuisine;
import com.omarhammad.kdg_backend.restaurants.domain.utils.Day;

import java.util.Map;

public record CreateRestaurantCmd(String name,
                                  Email email,
                                  Address address,
                                  String resPictureUrl,
                                  Map<Day, OpeningHours> dayOpeningHours,
                                  Cuisine cuisine,
                                  int defaultPrepTime,
                                  String ownerId) {


}
