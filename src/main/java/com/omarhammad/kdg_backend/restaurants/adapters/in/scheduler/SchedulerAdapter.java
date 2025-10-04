package com.omarhammad.kdg_backend.restaurants.adapters.in.scheduler;

import com.omarhammad.kdg_backend.restaurants.ports.in.TriggerScheduledPublishAllPendingDishesUseCase;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SchedulerAdapter {


    private final TriggerScheduledPublishAllPendingDishesUseCase triggerScheduledPublishAllPendingDishesUseCase;

    @Scheduled(cron = "0 * * * * *")
    private void triggerScheduledPublishToMakeDishesLive() {
        triggerScheduledPublishAllPendingDishesUseCase
                .triggerScheduledPublish();
    }


}
