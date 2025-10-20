package com.omarhammad.kdg_backend.orders.adapters.in.webAdapters;

import com.omarhammad.kdg_backend.orders.adapters.in.exceptions.InvalidEnumValueException;
import com.omarhammad.kdg_backend.orders.adapters.in.webAdapters.requests.CreateOrderRequest;
import com.omarhammad.kdg_backend.orders.domain.DishProjection;
import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.ports.in.CreateOrderCmd;
import org.springframework.stereotype.Component;

@Component
public class OrderRequestMapper {


    public CreateOrderCmd toCreateOrderCmd(CreateOrderRequest request) {

        return new CreateOrderCmd(
                request.dishes().stream().map(dishId -> new Id<DishProjection>(dishId)).toList()
        );
    }

    public <E extends Enum<E>> E toEnum(String value, Class<E> enumType) {
        try {
            return Enum.valueOf(enumType, value);

        } catch (IllegalArgumentException e) {
            throw new InvalidEnumValueException(value, enumType);
        }

    }

}
