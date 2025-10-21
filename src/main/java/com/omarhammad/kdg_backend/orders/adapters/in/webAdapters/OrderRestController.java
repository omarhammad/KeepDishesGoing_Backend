package com.omarhammad.kdg_backend.orders.adapters.in.webAdapters;

import com.omarhammad.kdg_backend.orders.adapters.in.dtos.generic.OrderIdDTO;
import com.omarhammad.kdg_backend.orders.adapters.in.dtos.generic.ResponseDTO;
import com.omarhammad.kdg_backend.orders.adapters.in.webAdapters.requests.CheckoutRequest;
import com.omarhammad.kdg_backend.orders.adapters.in.webAdapters.requests.CreateOrderRequest;
import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.domain.Order;
import com.omarhammad.kdg_backend.orders.ports.in.CreateOrderCmd;
import com.omarhammad.kdg_backend.orders.ports.in.CreateOrderUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderRestController {


    private CreateOrderUseCase createOrderUseCase;
    private OrderRequestMapper mapper;

    @PostMapping("")
    public ResponseEntity<OrderIdDTO> createNewOrder(@RequestBody CreateOrderRequest request) {

        CreateOrderCmd cmd = mapper.toCreateOrderCmd(request);
        Id<Order> orderId = createOrderUseCase.createOrder(cmd);

        return ResponseEntity.ok(new OrderIdDTO(orderId.value()));

    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<ResponseDTO> checkout(@PathVariable String id, @RequestBody CheckoutRequest request) {

        /*TODO
            1) All domain and core exceptions must be handled
            2) create customer entity object
            3) make payment entity object
            4) publish ORDER PLACED IF ALL SUCCESS.
         */
        return null;
    }

    /*
     * TODO:
     *  1) Make the projection for all [orders status on the domain object]
     *     and the [dish status on the dish_view] - DONE
     *  2) ENDPOINT TO REQUEST ORDER CREATION - [CUSTOMER_DATA, ORDER_DETAILS, PAYMENT_DETAILS]
     *     - A) check the dishes are from the same restaurant - DONE
     *     - B) check if the restaurant is open - DONE
     *     - C) check if all dishes are in_stock and published - DONE
     *     - D) create customer entity object
     *     - E) make payment entity object
     *     - F) create Order and save then return the id of the order for the tracking page
     *  3) ENDPOINT TO RETURN ORDER STATUS FOR THE TRACKING PAGE
     *  4) ENDPOINT TO RETURN ORDER DETAILS FOR THE CUSTOMER/OWNER
     *  5) As KDG, I want to publish messages for the delivery service when an order is accepted
     *     and when it is ready for pickup.
     */

}
