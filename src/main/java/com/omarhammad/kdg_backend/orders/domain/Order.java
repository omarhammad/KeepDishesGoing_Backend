package com.omarhammad.kdg_backend.orders.domain;

import com.omarhammad.kdg_backend.orders.domain.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Order {

    private Id<Order> id;
    private OrderStatus orderStatus;
    private Id restaurant;
    private List<Id> dishes;
    private Payment payment;
    private Customer customer;


    public Order(Id<Order> id, OrderStatus orderStatus, Id restaurant, List<Id> dishes, Payment payment, Customer customer) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.restaurant = restaurant;
        this.dishes = dishes;
        this.payment = payment;
        this.customer = customer;
    }

    public Order(OrderStatus orderStatus, Id restaurant, List<Id> dishes, Payment payment, Customer customer) {
        this.orderStatus = orderStatus;
        this.restaurant = restaurant;
        this.dishes = dishes;
        this.payment = payment;
        this.customer = customer;
    }
}
