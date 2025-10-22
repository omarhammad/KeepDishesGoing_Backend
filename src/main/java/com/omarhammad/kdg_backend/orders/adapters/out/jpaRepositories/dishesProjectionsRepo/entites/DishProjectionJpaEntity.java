package com.omarhammad.kdg_backend.orders.adapters.out.jpaRepositories.dishesProjectionsRepo.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "dish_projection")
public class DishProjectionJpaEntity {


    @Id
    private UUID dishId;
    private UUID restaurantId;
    private LocalDateTime occurredAt;
    private BigDecimal price;
    private String liveStatus;
    private String stockStatus;
}
