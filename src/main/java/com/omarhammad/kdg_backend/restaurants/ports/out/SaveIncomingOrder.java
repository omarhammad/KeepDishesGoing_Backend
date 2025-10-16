package com.omarhammad.kdg_backend.restaurants.ports.out;

import com.omarhammad.kdg_backend.restaurants.domain.IncomingOrder;

import java.util.Optional;

public interface SaveIncomingOrder {


    Optional<IncomingOrder> save(IncomingOrder incomingOrder);


}
