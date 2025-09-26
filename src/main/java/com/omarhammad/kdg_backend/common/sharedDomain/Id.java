package com.omarhammad.kdg_backend.common.sharedDomain;

import java.util.Objects;
import java.util.UUID;

public record Id(UUID value) {

    public Id(UUID value) {
        this.value = Objects.requireNonNull(value, "ID can't be null");
    }

    public static Id createNewId() {
        return new Id(UUID.randomUUID());
    }

    @Override
    public UUID value() {
        return value;
    }
}
