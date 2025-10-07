package com.omarhammad.kdg_backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.Modulith;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Modulith
@EnableScheduling
@EnableWebSecurity
public class KdgBackendApplication {

    private final Logger log = LoggerFactory.getLogger(KdgBackendApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(KdgBackendApplication.class, args);
    }

    @EventListener(ApplicationStartedEvent.class)
    void onApplicationStarted(){
        ApplicationModules modules = ApplicationModules.of(KdgBackendApplication.class);
        modules.forEach(module -> log.info("\n{}",module));
    }

}


