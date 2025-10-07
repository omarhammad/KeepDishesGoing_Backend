package com.omarhammad.kdg_backend.restaurants.adapters.in.webAdapter.resturantRestAPI.request;

import jakarta.validation.constraints.Future;

import java.time.LocalDateTime;

public record SchedulePublishRequest(@Future(message = "Schedule time should be in future") LocalDateTime scheduleTime) {
}
