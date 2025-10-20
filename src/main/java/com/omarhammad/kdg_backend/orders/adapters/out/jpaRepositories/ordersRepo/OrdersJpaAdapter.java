package com.omarhammad.kdg_backend.orders.adapters.out.jpaRepositories.ordersRepo;

import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.domain.Order;
import com.omarhammad.kdg_backend.orders.ports.out.EditOrderPort;
import com.omarhammad.kdg_backend.orders.ports.out.LoadOrderPort;
import com.omarhammad.kdg_backend.orders.ports.out.SaveOrderPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrdersJpaAdapter implements LoadOrderPort, SaveOrderPort, EditOrderPort {
    @Override
    public Optional<Order> findById(Id<Order> orderId) {
        return Optional.empty();
    }

    @Override
    public Optional<Order> save(Order order) {
        return Optional.empty();
    }

    @Override
    public void edit(Order order) {

    }


}
