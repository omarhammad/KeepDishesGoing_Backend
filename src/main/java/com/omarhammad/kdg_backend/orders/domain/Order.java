package com.omarhammad.kdg_backend.orders.domain;

import com.omarhammad.kdg_backend.orders.domain.enums.OrderStatus;
import com.sun.source.util.DocTreeFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Order {

    private Id<Order> id;
    private OrderStatus orderStatus;
    private LocalDateTime statusOccurredAt;
    private Id restaurant;
    private List<Id> dishes;
    private Payment payment;
    private Customer customer;

    public Order(OrderStatus orderStatus, LocalDateTime statusOccurredAt, Id restaurant, List<Id> dishes, Payment payment, Customer customer) {
        this.orderStatus = orderStatus;
        this.statusOccurredAt = statusOccurredAt;
        this.restaurant = restaurant;
        this.dishes = dishes;
        this.payment = payment;
        this.customer = customer;
    }

    public Order changeStatusTo(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }


    public void at(LocalDateTime statusOccurredAt) {
        this.statusOccurredAt = statusOccurredAt;
    }
}
