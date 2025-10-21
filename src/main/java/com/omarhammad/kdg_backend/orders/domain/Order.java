package com.omarhammad.kdg_backend.orders.domain;

import com.omarhammad.kdg_backend.orders.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Id<Order> id;
    private OrderStatus orderStatus;
    private LocalDateTime statusOccurredAt;
    private Id restaurant;
    private List<Id> dishes;
    private Payment payment;
    private Customer customer;

    public Order changeStatusTo(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public void at(LocalDateTime statusOccurredAt) {
        this.statusOccurredAt = statusOccurredAt;
    }


    public void createOrder(List<Id> dishes, Id restaurant) {
        this.id = Id.createNewId();
        this.dishes = dishes;
        this.restaurant = restaurant;
        this.orderStatus = OrderStatus.PENDING_PAYMENT;
        this.statusOccurredAt = LocalDateTime.now();
    }
}
