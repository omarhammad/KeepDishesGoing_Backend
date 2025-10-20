package com.omarhammad.kdg_backend.orders.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Customer {


    private Id<Customer> id;
    private String firstName;
    private String lastName;
    private DeliveryAddress deliveryAddress;
    private Email email;
    private String phoneNumber;


    public Customer(String firstName, String lastName, DeliveryAddress deliveryAddress, Email email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.deliveryAddress = deliveryAddress;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}

