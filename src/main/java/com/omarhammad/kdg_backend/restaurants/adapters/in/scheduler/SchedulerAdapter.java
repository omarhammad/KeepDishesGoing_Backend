package com.omarhammad.kdg_backend.restaurants.adapters.in.scheduler;

import com.omarhammad.kdg_backend.restaurants.ports.in.AutoMarkOrdersReadyForPickUpUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.in.DeclineOrdersBeyondResponseWindowUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.in.DeclineOrdersCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.TriggerScheduledPublishAllPendingDishesUseCase;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SchedulerAdapter {


    private final TriggerScheduledPublishAllPendingDishesUseCase triggerScheduledPublishAllPendingDishesUseCase;
    private final DeclineOrdersBeyondResponseWindowUseCase declineOrdersBeyondResponseWindowUseCase;
    private final AutoMarkOrdersReadyForPickUpUseCase autoMarkOrdersReadyForPickUpUseCase;

    @Scheduled(cron = "0 * * * * *")
    public void triggerScheduledPublishToMakeDishesLive() {
        triggerScheduledPublishAllPendingDishesUseCase
                .triggerScheduledPublish();
    }


    @Scheduled(cron = "0 * * * * *")
    public void declineOrdersPlacedInFiveMinutes() {
        DeclineOrdersCmd cmd = new DeclineOrdersCmd(5);
        declineOrdersBeyondResponseWindowUseCase.decline(cmd);

    }

    @Scheduled(cron = "0 * * * * *")
    public void markOrdersReadyForPickUp() {
        autoMarkOrdersReadyForPickUpUseCase.readyForPickUp();
    }


}
