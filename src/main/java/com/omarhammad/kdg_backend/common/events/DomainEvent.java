package com.omarhammad.kdg_backend.common.events;

import java.time.LocalDateTime;

public interface DomainEvent {
    LocalDateTime occurredAt();
}
