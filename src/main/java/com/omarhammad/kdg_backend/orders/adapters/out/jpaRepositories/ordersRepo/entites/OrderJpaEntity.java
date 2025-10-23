package com.omarhammad.kdg_backend.orders.adapters.out.jpaRepositories.ordersRepo.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderJpaEntity {
    @Id
    private UUID id;

    private String orderStatus;
    private String rejectedMessage;
    private String declinedMessage;
    private LocalDateTime statusOccurredAt;

    @Column(name = "restaurant_id")
    private UUID restaurant;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "order_dishes",
            joinColumns = @JoinColumn(name = "order_id")
    )
    @Column(name = "dish_Id")
    private List<UUID> dishes;

    private BigDecimal totalPrice;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "payment_id")
    private PaymentJpaEntity payment;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_id")
    private CustomerJpaEntity customer;


}
