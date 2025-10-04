package com.omarhammad.kdg_backend.restaurants.ports.in;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;

public interface FindOwnerByIdUseCse {

    Owner findOwnerById(Id<Owner> ownerId);
}
