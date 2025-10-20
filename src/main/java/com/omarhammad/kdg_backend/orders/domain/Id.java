package com.omarhammad.kdg_backend.orders.domain;

import com.omarhammad.kdg_backend.orders.domain.excptions.InvalidUUIDFormatException;
import java.util.Objects;
import java.util.UUID;

public record Id<T>(String value) {

    public Id(String value) {
        this.value = Objects.requireNonNull(value, "ID can't be null");
        try {
            UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidUUIDFormatException("Invalid id, it has to be UUID");
        }


    }

    public static <T> Id<T> createNewId() {
        return new Id<>(UUID.randomUUID().toString());
    }

    @Override
    public String value() {
        return value;
    }
}
