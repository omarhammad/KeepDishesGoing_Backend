package com.omarhammad.kdg_backend.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record Email(String email) {

    @JsonCreator
    public Email(String email) {
        this.email = email;
    }

    @JsonValue
    public String email() {
        return this.email;
    }
}
