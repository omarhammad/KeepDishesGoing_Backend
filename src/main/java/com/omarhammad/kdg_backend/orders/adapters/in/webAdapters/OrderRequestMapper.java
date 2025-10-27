package com.omarhammad.kdg_backend.orders.adapters.in.webAdapters;

import com.omarhammad.kdg_backend.orders.adapters.in.dtos.CustomerDTO;
import com.omarhammad.kdg_backend.orders.adapters.in.dtos.DeliveryAddressDTO;
import com.omarhammad.kdg_backend.orders.adapters.in.dtos.OrderDTO;
import com.omarhammad.kdg_backend.orders.adapters.in.exceptions.InvalidEnumValueException;
import com.omarhammad.kdg_backend.orders.adapters.in.webAdapters.requests.CheckoutRequest;
import com.omarhammad.kdg_backend.orders.adapters.in.webAdapters.requests.CreateOrderRequest;
import com.omarhammad.kdg_backend.orders.adapters.in.webAdapters.requests.DeliveryAddressRequest;
import com.omarhammad.kdg_backend.orders.domain.*;
import com.omarhammad.kdg_backend.orders.domain.enums.PaymentMethod;
import com.omarhammad.kdg_backend.orders.ports.in.CheckoutCmd;
import com.omarhammad.kdg_backend.orders.ports.in.CreateOrderCmd;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderRequestMapper {


    public CreateOrderCmd toCreateOrderCmd(CreateOrderRequest request) {

        return new CreateOrderCmd(
                request.dishes().stream().map(dishId -> new Id<DishProjection>(dishId)).toList()
        );
    }


    public CheckoutCmd toCheckoutCmd(Id<Order> orderId, CheckoutRequest request) {
        return new CheckoutCmd(
                orderId,
                request.firstName(),
                request.lastName(),
                toDeliveryAddress(request.deliveryAddress()),
                new Email(request.email()),
                request.phoneNumber(),
                toEnum(request.paymentInfo().method(), PaymentMethod.class),
                request.paymentInfo().amount(),
                request.paymentInfo().paymentToken()

        );
    }

    private DeliveryAddress toDeliveryAddress(DeliveryAddressRequest deliveryAddressRequest) {
        return new DeliveryAddress(
                deliveryAddressRequest.street(),
                deliveryAddressRequest.number(),
                deliveryAddressRequest.postalCode(),
                deliveryAddressRequest.city(),
                deliveryAddressRequest.country()
        );
    }


    public <E extends Enum<E>> E toEnum(String value, Class<E> enumType) {
        try {
            return Enum.valueOf(enumType, value);

        } catch (IllegalArgumentException e) {
            throw new InvalidEnumValueException(value, enumType);
        }

    }


    public OrderDTO toOrderDTO(Order order) {
        return new OrderDTO(
                order.getId().value(),
                order.getOrderStatus().name(),
                order.getRejectedMessage(),
                order.getDeclinedMessage(),
                order.getStatusOccurredAt(),
                order.getRestaurant().value(),
                order.getDishes().stream().map(Id::value).toList(),
                order.getTotalPrice(),
                toCustomerDTO(order.getCustomer())


        );
    }

    private CustomerDTO toCustomerDTO(Customer customer) {
        return new CustomerDTO(
                customer.getId().value(),
                customer.getFirstName(),
                customer.getLastName(),
                toDeliveryAddressDTO(customer.getDeliveryAddress()),
                customer.getEmail().email(),
                customer.getPhoneNumber()
        );
    }

    public DeliveryAddressDTO toDeliveryAddressDTO(DeliveryAddress deliveryAddress) {
        return new DeliveryAddressDTO(
                deliveryAddress.street(),
                deliveryAddress.number(),
                deliveryAddress.postalCode(),
                deliveryAddress.city(),
                deliveryAddress.country()
        );
    }
}
