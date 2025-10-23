package com.omarhammad.kdg_backend.restaurants.ports.out;


import com.omarhammad.kdg_backend.restaurants.domain.Address;
import com.omarhammad.kdg_backend.restaurants.domain.Coordinates;

public interface GeocodingPort {
    Coordinates geocode(Address address);

}
