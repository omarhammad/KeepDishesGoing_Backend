package com.omarhammad.kdg_backend.restaurants.core;

import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.restaurants.ports.in.FindOwnerByIdUseCse;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadOwnerPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultFindOwnerByIdUseCase implements FindOwnerByIdUseCse {


    private LoadOwnerPort loadOwnerPort;

    @Override
    public Owner findOwnerById(Id<Owner> ownerId) {

        return loadOwnerPort.findOwnerById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner {%s} not found".formatted(ownerId.value())));
    }

}
