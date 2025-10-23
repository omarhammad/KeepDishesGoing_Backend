package com.omarhammad.kdg_backend.orders.domain;

import com.omarhammad.kdg_backend.common.events.DomainEvent;
import com.omarhammad.kdg_backend.common.events.ordersEvents.OrderPlacedEvent;
import com.omarhammad.kdg_backend.orders.domain.enums.OrderStatus;
import com.omarhammad.kdg_backend.orders.domain.enums.PaymentMethod;
import com.omarhammad.kdg_backend.orders.domain.excptions.CustomerAlreadyExist;
import com.omarhammad.kdg_backend.orders.domain.excptions.OrderAlreadyPaidException;
import com.omarhammad.kdg_backend.orders.domain.excptions.PaymentNotSuccessfulException;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter

public class Order {

    private Id<Order> id;
    private OrderStatus orderStatus;
    private LocalDateTime statusOccurredAt;
    private Id restaurant;
    private List<Id> dishes;
    private BigDecimal totalPrice;
    private Payment payment;
    private Customer customer;
    private final List<DomainEvent> domainEvents;

    public Order() {
        this.domainEvents = new ArrayList<>();
    }

    public Order(Id<Order> id, OrderStatus orderStatus, LocalDateTime statusOccurredAt, Id restaurant, List<Id> dishes, BigDecimal totalPrice, Payment payment, Customer customer) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.statusOccurredAt = statusOccurredAt;
        this.restaurant = restaurant;
        this.dishes = dishes;
        this.totalPrice = totalPrice;
        this.payment = payment;
        this.customer = customer;
        this.domainEvents = new ArrayList<>();
    }

    public Order changeStatusTo(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public void at(LocalDateTime statusOccurredAt) {
        this.statusOccurredAt = statusOccurredAt;
    }


    public void createOrder(List<Id> dishes, Id restaurant, BigDecimal totalOrderPrice) {
        this.id = Id.createNewId();
        this.dishes = dishes;
        this.restaurant = restaurant;
        this.totalPrice = totalOrderPrice;
        this.orderStatus = OrderStatus.PENDING_PAYMENT;
        this.statusOccurredAt = LocalDateTime.now();
    }

    public void addNewCustomer(String firstName, String lastName, DeliveryAddress deliveryAddress, Email email, String phoneNumber) {
        if (Objects.nonNull(this.customer))
            throw new CustomerAlreadyExist("For this order there is already a customer");

        this.customer = new Customer(
                Id.createNewId(),
                firstName,
                lastName,
                deliveryAddress,
                email,
                phoneNumber
        );
    }

    public Payment addNewPayment(PaymentMethod paymentMethod, BigDecimal amount, String paymentToken) {
        if (Objects.nonNull(this.payment)) throw new OrderAlreadyPaidException("Order already paid");

        this.payment = new Payment(
                Id.createNewId(),
                paymentMethod,
                amount,
                paymentToken
        );

        return this.payment;

    }

    public void verifyPayment(PaymentResult paymentResult) {

        if (!paymentResult.successState()) throw new PaymentNotSuccessfulException(paymentResult.message());

        this.payment.paymentSuccessful(paymentResult);
        this.orderStatus = OrderStatus.PLACED;
        this.domainEvents.add(new OrderPlacedEvent(
                this.id.value(),
                this.restaurant.value(),
                new OrderPlacedEvent.Address(
                        customer.getDeliveryAddress().street(),
                        customer.getDeliveryAddress().number(),
                        customer.getDeliveryAddress().postalCode(),
                        customer.getDeliveryAddress().city(),
                        customer.getDeliveryAddress().country()

                ),
                LocalDateTime.now()
        ));


    }
}
