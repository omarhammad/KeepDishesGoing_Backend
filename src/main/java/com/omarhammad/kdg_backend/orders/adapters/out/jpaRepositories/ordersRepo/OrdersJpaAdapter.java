package com.omarhammad.kdg_backend.orders.adapters.out.jpaRepositories.ordersRepo;

import com.omarhammad.kdg_backend.orders.adapters.out.jpaRepositories.ordersRepo.entites.*;
import com.omarhammad.kdg_backend.orders.domain.*;
import com.omarhammad.kdg_backend.orders.domain.enums.OrderStatus;
import com.omarhammad.kdg_backend.orders.domain.enums.PaymentMethod;
import com.omarhammad.kdg_backend.orders.ports.out.EditOrderPort;
import com.omarhammad.kdg_backend.orders.ports.out.LoadOrderPort;
import com.omarhammad.kdg_backend.orders.ports.out.SaveOrderPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class OrdersJpaAdapter implements LoadOrderPort, SaveOrderPort, EditOrderPort {


    private final OrdersRepository repository;

    @Override
    public Optional<Order> findById(Id<Order> orderId) {
        Optional<OrderJpaEntity> entity = repository.findById(UUID.fromString(orderId.value()));
        return entity.map(this::toOrder);
    }

    @Override
    public List<Order> findOrdersByRestaurantId(Id restaurantId) {
        List<OrderJpaEntity> entities = repository.findAllByRestaurant(UUID.fromString(restaurantId.value()));

        return entities.stream().map(this::toOrder).toList();
    }


    @Override
    public Order save(Order order) {
        OrderJpaEntity entity = toOrderJpaEntity(order);
        OrderJpaEntity savedOrderEntity = repository.save(entity);
        return toOrder(savedOrderEntity);
    }

    @Override
    public void edit(Order order) {
        OrderJpaEntity entity = toOrderJpaEntity(order);
        repository.save(entity);
    }

    private Order toOrder(OrderJpaEntity entity) {
        return new Order(
                new Id<>(entity.getId().toString()),
                OrderStatus.valueOf(entity.getOrderStatus().toUpperCase()),
                entity.getStatusOccurredAt(),
                new Id(entity.getRestaurant().toString()),
                entity.getDishes().stream().map(d -> new Id(d.toString())).toList(),
                entity.getTotalPrice(),
                toPayment(entity.getPayment()),
                toCustomer(entity.getCustomer())
        );
    }

    private Payment toPayment(PaymentJpaEntity payment) {
        if (Objects.isNull(payment)) return null;
        return new Payment(
                new Id<>(payment.getId().toString()),
                PaymentMethod.valueOf(payment.getMethod()),
                payment.getAmount(),
                payment.getPaymentToken(),
                new PaymentResult(
                        payment.getPaymentResult().successState(),
                        payment.getPaymentResult().message()
                )
        );
    }

    private Customer toCustomer(CustomerJpaEntity customer) {
        if (Objects.isNull(customer)) return null;
        return new Customer(
                new Id<>(customer.getId().toString()),
                customer.getFirstName(),
                customer.getLastName(),
                toDeliveryAddress(customer.getDeliveryAddress()),
                new Email(customer.getEmail()),
                customer.getPhoneNumber()
        );
    }

    private DeliveryAddress toDeliveryAddress(DeliveryAddressJpa deliveryAddress) {
        return new DeliveryAddress(
                deliveryAddress.street(),
                deliveryAddress.number(),
                deliveryAddress.postalCode(),
                deliveryAddress.city(),
                deliveryAddress.country()
        );
    }

    private OrderJpaEntity toOrderJpaEntity(Order order) {
        return new OrderJpaEntity(
                UUID.fromString(order.getId().value()),
                order.getOrderStatus().name(),
                order.getStatusOccurredAt(),
                UUID.fromString(order.getRestaurant().value()),
                order.getDishes().stream().map(d -> UUID.fromString(d.value())).toList(),
                order.getTotalPrice(),
                toPaymentJpaEntity(order.getPayment()),
                toCustomerJpaEntity(order.getCustomer())
        );
    }

    private PaymentJpaEntity toPaymentJpaEntity(Payment payment) {
        if (Objects.isNull(payment)) return null;
        return new PaymentJpaEntity(
                UUID.fromString(payment.getId().value()),
                payment.getMethod().name(),
                payment.getAmount(),
                payment.getPaymentToken(),
                new PaymentResultsJpa(
                        payment.getPaymentResult().successState(),
                        payment.getPaymentResult().message()
                )
        );
    }

    private CustomerJpaEntity toCustomerJpaEntity(Customer customer) {
        if (Objects.isNull(customer)) return null;

        return new CustomerJpaEntity(
                UUID.fromString(customer.getId().value()),
                customer.getFirstName(),
                customer.getLastName(),
                toDeliveryAddressJpa(customer.getDeliveryAddress()),
                customer.getEmail().email(),
                customer.getPhoneNumber()
        );
    }

    private DeliveryAddressJpa toDeliveryAddressJpa(DeliveryAddress deliveryAddress) {
        return new DeliveryAddressJpa(
                deliveryAddress.street(),
                deliveryAddress.number(),
                deliveryAddress.postalCode(),
                deliveryAddress.city(),
                deliveryAddress.country()
        );
    }


}
