package com.omarhammad.kdg_backend.orders.adapters.in.webAdapters;

import com.omarhammad.kdg_backend.orders.adapters.in.dtos.OrderDTO;
import com.omarhammad.kdg_backend.orders.adapters.in.dtos.OrderIdDTO;
import com.omarhammad.kdg_backend.orders.adapters.in.dtos.generic.ResponseDTO;
import com.omarhammad.kdg_backend.orders.adapters.in.webAdapters.requests.CheckoutRequest;
import com.omarhammad.kdg_backend.orders.adapters.in.webAdapters.requests.CreateOrderRequest;
import com.omarhammad.kdg_backend.orders.domain.Id;
import com.omarhammad.kdg_backend.orders.domain.Order;
import com.omarhammad.kdg_backend.orders.ports.in.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderRestController {


    private final CreateOrderUseCase createOrderUseCase;
    private final CheckoutUseCase checkoutUseCase;
    private final OrderRequestMapper mapper;
    private final FindCheckedOutOrdersByRestaurantIdUseCase findCheckedOutOrdersByRestaurantIdUseCase;
    private final FindCheckedOutrderByIdUseCase findCheckedOutrderByIdUseCase;


    @PostMapping("")
    public ResponseEntity<OrderIdDTO> createNewOrder(@RequestBody CreateOrderRequest request) {

        CreateOrderCmd cmd = mapper.toCreateOrderCmd(request);
        Id<Order> orderId = createOrderUseCase.createOrder(cmd);

        return ResponseEntity.ok(new OrderIdDTO(orderId.value()));

    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<ResponseDTO> checkout(@PathVariable String id, @RequestBody CheckoutRequest request) {

        Id<Order> orderId = new Id<>(id);
        CheckoutCmd cmd = mapper.toCheckoutCmd(orderId, request);
        checkoutUseCase.checkout(cmd);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(
                HttpStatus.OK.value(),
                "Checkout completed successfully"
        ));
    }

    @GetMapping("")
    public ResponseEntity<List<OrderDTO>> getCheckedOUtOrdersByRestaurantId(@RequestParam String resId) {

        Id restaurantId = new Id(resId);
        List<Order> orders = findCheckedOutOrdersByRestaurantIdUseCase.findCheckedOutOrdersByRestaurantId(restaurantId);

        if (orders.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        List<OrderDTO> orderDTOS = orders.stream().map(mapper::toOrderDTO).toList();
        return ResponseEntity.status(HttpStatus.OK).body(orderDTOS);

    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getCheckedOUtOrderById(@PathVariable String id) {

        Id<Order> orderId = new Id<>(id);
        Order orders =
                findCheckedOutrderByIdUseCase.findCheckedOutOrdersById(orderId);

        OrderDTO orderDTO = mapper.toOrderDTO(orders);
        return ResponseEntity.status(HttpStatus.OK).body(orderDTO);

    }





    /*
     * TODO:
     *  1) Make the projection for all [orders status on the domain object]
     *     and the [dish status on the dish_view] - DONE
     *  2) ENDPOINT TO REQUEST ORDER CREATION - [CUSTOMER_DATA, ORDER_DETAILS, PAYMENT_DETAILS]
     *     - A) check the dishes are from the same restaurant - DONE
     *     - B) check if the restaurant is open - DONE
     *     - C) check if all dishes are in_stock and published - DONE
     *     - D) create customer entity object - DONE
     *     - E) make payment entity object - DONE
     *     - F) publish ORDER PLACED IF ALL SUCCESS - DONE
     *     - G) SEND EMAIL WITH THE TRACKING LINK FOR THE CUSTOMER - DONE
     *     - H) TBC-> paidAmount == total_dishes_price - DONE
     *  3) ENDPOINT TO RETURN ALL ORDERS BY RESTAURANT_ID - DONE
     *  4) ENDPOINT TO RETURN ORDER DETAILS FOR THE TRACKING PAGE - DONE
     */

}
