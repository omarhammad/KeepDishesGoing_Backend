package com.omarhammad.kdg_backend.orders.adapters.out.jpaRepositories.ordersRepo.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "restaurant_id")
    private UUID restaurant;

    @ElementCollection
    @CollectionTable(
            name = "order_dishes",
            joinColumns = @JoinColumn(name = "order_id")
    )
    @Column(name = "dish_Id")
    private List<UUID> dishes;

    @OneToOne
    private PaymentJpaEntity payment;

    @OneToOne
    private CustomerJpaEntity customer;



}
