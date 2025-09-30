package com.omarhammad.kdg_backend.restaurants.domain.enums;

import lombok.Getter;

@Getter
public enum Day {
    MONDAY("MONDAY"),
    TUESDAY("TUESDAY"),
    WEDNESDAY("WEDNESDAY"),
    THURSDAY("THURSDAY"),
    FRIDAY("FRIDAY"),
    SATURDAY("SATURDAY"),
    SUNDAY("SUNDAY");


    final String value;

    Day(String value) {
        this.value = value;
    }


}
