package com.omarhammad.kdg_backend.common;

import java.util.Objects;

public record Email(String email) {

    public Email {
        Objects.requireNonNull(email);
    }


}
