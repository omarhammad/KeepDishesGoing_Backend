package com.omarhammad.kdg_backend.orders.adapters.in.exceptions;

import java.util.Arrays;

public class InvalidEnumValueException extends RuntimeException {
    public InvalidEnumValueException(String invalidValue, Class<?> enumType) {
        super("Invalid value {%s} for enum {%s}. Allowed values %s".formatted(
                invalidValue,
                enumType.getSimpleName(),
                Arrays.toString(enumType.getEnumConstants())));

    }
}
