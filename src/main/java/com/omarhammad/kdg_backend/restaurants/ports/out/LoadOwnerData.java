package com.omarhammad.kdg_backend.restaurants.ports.out;

import com.omarhammad.kdg_backend.restaurants.domain.Owner;

public interface LoadOwnerData {

    Owner findOwnerById(Long ownerId);

}
