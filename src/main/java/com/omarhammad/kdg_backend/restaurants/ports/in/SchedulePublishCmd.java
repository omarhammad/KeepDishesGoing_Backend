package com.omarhammad.kdg_backend.restaurants.ports.in;

import java.time.LocalDateTime;

public record SchedulePublishCmd(LocalDateTime scheduleTime) {

}
