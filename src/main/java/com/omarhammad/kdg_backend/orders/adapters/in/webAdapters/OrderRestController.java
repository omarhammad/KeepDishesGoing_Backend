package com.omarhammad.kdg_backend.orders.adapters.in.webAdapters;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderRestController {


    /*
     * TODO:
     *  1) Make the projection for all [orders status on the domain object] and the [dish status on the dish_view]
     *  2) ENDPOINT TO REQUEST ORDER CREATION - [CUSTOMER_DATA, ORDER_DETAILS, PAYMENT_DETAILS]
     *     - A) check the dishes are from the same restaurant
     *     - B) check if the restaurant is open
     *     - C) check if all dishes are in_stock and published
     *     - D) create customer entity object
     *     - E) make payment entity object
     *     - F) create Order and save then return the id of the order for the tracking page
     *  3) ENDPOINT TO RETURN ORDER STATUS FOR THE TRACKING PAGE
     *  4) ENDPOINT TO RETURN ORDER DETAILS FOR THE CUSTOMER/OWNER
     *  5) As KDG, I want to publish messages for the delivery service when an order is accepted
     *     and when it is ready for pickup.
     */

}
