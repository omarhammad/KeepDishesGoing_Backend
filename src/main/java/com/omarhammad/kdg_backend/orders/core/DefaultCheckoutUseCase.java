package com.omarhammad.kdg_backend.orders.core;

import com.omarhammad.kdg_backend.orders.domain.Order;
import com.omarhammad.kdg_backend.orders.domain.Payment;
import com.omarhammad.kdg_backend.orders.domain.PaymentResult;
import com.omarhammad.kdg_backend.orders.ports.in.CheckoutCmd;
import com.omarhammad.kdg_backend.orders.ports.in.CheckoutUseCase;
import com.omarhammad.kdg_backend.orders.ports.out.*;
import com.omarhammad.kdg_backend.orders.core.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultCheckoutUseCase implements CheckoutUseCase {


    private LoadOrderPort loadOrderPort;
    private EditOrderPort editOrderPort;
    private ProcessPaymentPort processPaymentPort;
    private EventPublisherPort publisherPort;
    private NotificationPort notificationPort;


    @Override
    public void checkout(CheckoutCmd cmd) {


        Order order = loadOrderPort.findById(cmd.orderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));


        order.addNewCustomer(
                cmd.firstName(),
                cmd.lastName(),
                cmd.deliveryAddress(),
                cmd.email(),
                cmd.phoneNumber()
        );

        Payment payment = order.addNewPayment(cmd.paymentMethod(), cmd.paymentAmount(), cmd.paymentToken());
        PaymentResult paymentResult = processPaymentPort.process(payment);
        order.verifyPayment(paymentResult);

        editOrderPort.edit(order);
        publisherPort.publish(order);
        notificationPort.notify(order);

    }
}
