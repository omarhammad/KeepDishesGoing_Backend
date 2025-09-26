package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.ports.in.CreateDishCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.ICreateDishDraftUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreateDishDraftUseCase implements ICreateDishDraftUseCase {
    @Override
    public void createDish(CreateDishCmd cmd) {

    }
}
