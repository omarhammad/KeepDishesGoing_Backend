package com.omarhammad.kdg_backend.common.sharedDomain;

import java.util.Objects;
import java.util.UUID;

public record Id(String value) {

    public Id(String value) {
        this.value = Objects.requireNonNull(value, "ID can't be null");
        try {
            UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid id, it has to be UUID");
        }


    }

    public static Id createNewId() {
        return new Id(UUID.randomUUID().toString());
    }

    @Override
    public String value() {
        return value;
    }
}
