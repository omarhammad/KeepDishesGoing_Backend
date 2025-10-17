package com.omarhammad.kdg_backend.restaurants.ports.in;

public interface DeclineOrdersBeyondResponseWindowUseCase {

    void decline(DeclineOrdersCmd cmd);
}
