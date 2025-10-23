package com.omarhammad.kdg_backend.common.events.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopology {

    // TODO
//       Events: RK pattern (<context>.<restaurantId>.<resource>.<action>.v1) , QUEUE pattern (<consumer-bc>.<resource>.<action>)
//        1) DONE - OrderPlaced ---> Order publish >>> RESTAURANT(restaurant-bc.order.placed) - (RK = order.<orderId>.order.placed.v1)
//        2) DONE - OrderDeclined ---> Restaurant publish >>> ORDER(order-bc.order.declined) - (RK = restaurant.<restaurantId>.order.declined.v1)
//        3) DONE - OrderRejected ---> Restaurant publish >>> ORDER(order-bc.order.rejected) - (RK = restaurant.<restaurantId>.order.rejected.v1)
//        4) DONE - OrderAccepted ---> Restaurant publish >>> ORDER(order-bc.order.accepted) , DELIVERY(delivery-bc.order.accepted) - Both same (RK = restaurant.<restaurantId>.order.accepted.v1)
//        5) DONE - OrderReadyForPickUp ---> Restaurant publish >>> ORDER(order-bc.order.ready) , DELIVERY(delivery-bc.order.order.ready) - Both same (RK = restaurant.<restaurantId>.order.ready.v1)
//        6) DONE -OrderPickedUp ---> Delivery publish >>> RESTAURANT(restaurant-bc.corder.pickedup) ,ORDER(order-bc.order.pickedup) - Both same (RK = delivery.<deliveryId>.order.pickedup.v1)
//        7) DONE -OrderDelivered ---> Delivery publish >>> RESTAURANT(restaurant-bc.order.delivered) ,ORDER(order-bc.order.delivered) - Both same (RK = delivery.<deliveryId>.order.delivered.v1)


    public static final String KDG_EXCHANGE = "kdg.events";
    public static final String RESTAURANT_BC_ORDER_PLACED_QUEUE = "restaurant-bc.order.placed";
    public static final String ORDER_BC_ORDER_REJECTED_QUEUE = "order-bc.order.rejected";
    public static final String ORDER_BC_ORDER_DECLINED_QUEUE = "order-bc.order.declined";
    public static final String ORDER_BC_ORDER_ACCEPTED_QUEUE = "order-bc.order.accepted";
    public static final String DELIVERY_BC_ORDER_ACCEPTED_QUEUE = "delivery-bc.order.accepted";
    public static final String ORDER_BC_ORDER_READY_QUEUE = "order-bc.order.ready";
    public static final String DELIVERY_BC_ORDER_READY_QUEUE = "delivery-bc.order.ready";
    public static final String RESTAURANT_BC_ORDER_PICKEDUP_QUEUE = "restaurant-bc.order.pickedup";
    public static final String ORDER_BC_ORDER_PICKEDUP_QUEUE = "order-bc.order.pickedup";
    public static final String RESTAURANT_BC_ORDER_DELIVERED_QUEUE = "restaurant-bc.order.delivered";
    public static final String ORDER_BC_ORDER_DELIVERED_QUEUE = "order-bc.order.delivered";
    public static final String ORDER_BC_DISH_PUBLISHED_QUEUE = "order-bc.dish.published";
    public static final String ORDER_BC_DISH_UNPUBLISHED_QUEUE = "order-bc.dish.unpublished";
    public static final String ORDER_BC_DISH_OUT_OF_STOCK_QUEUE = "order-bc.dish.out-of-stock";
    public static final String ORDER_BC_DISH_IN_STOCK_QUEUE = "order-bc.dish.in-stock";


    @Bean
    public TopicExchange kdgEvent() {
        return ExchangeBuilder
                .topicExchange(KDG_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean(RESTAURANT_BC_ORDER_PLACED_QUEUE)
    public Queue orderPlacedQueue() {
        return QueueBuilder
                .durable(RESTAURANT_BC_ORDER_PLACED_QUEUE)
                .build();
    }

    @Bean(ORDER_BC_ORDER_DECLINED_QUEUE)
    public Queue orderDeclinedQueue() {
        return QueueBuilder
                .durable(ORDER_BC_ORDER_DECLINED_QUEUE)
                .build();
    }

    @Bean(ORDER_BC_ORDER_REJECTED_QUEUE)
    public Queue orderRejectedQueue() {
        return QueueBuilder
                .durable(ORDER_BC_ORDER_REJECTED_QUEUE)
                .build();
    }

    @Bean(ORDER_BC_ORDER_ACCEPTED_QUEUE)
    public Queue orderBcOrderAcceptedQueue() {
        return QueueBuilder
                .durable(ORDER_BC_ORDER_ACCEPTED_QUEUE)
                .build();
    }


    @Bean(DELIVERY_BC_ORDER_ACCEPTED_QUEUE)
    public Queue deliveryBcOrderAcceptedQueue() {
        return QueueBuilder
                .durable(DELIVERY_BC_ORDER_ACCEPTED_QUEUE)
                .build();
    }

    @Bean(ORDER_BC_ORDER_READY_QUEUE)
    public Queue orderBcOrderReadyQueue() {
        return QueueBuilder
                .durable(ORDER_BC_ORDER_READY_QUEUE)
                .build();
    }


    @Bean(DELIVERY_BC_ORDER_READY_QUEUE)
    public Queue deliveryBcOrderReadyQueue() {
        return QueueBuilder
                .durable(DELIVERY_BC_ORDER_READY_QUEUE)
                .build();
    }

    @Bean(RESTAURANT_BC_ORDER_PICKEDUP_QUEUE)
    public Queue restarauntBcOrderPickedupQueue() {
        return QueueBuilder
                .durable(RESTAURANT_BC_ORDER_PICKEDUP_QUEUE)
                .build();
    }


    @Bean(ORDER_BC_ORDER_PICKEDUP_QUEUE)
    public Queue orderBcOrderPickedupQueue() {
        return QueueBuilder
                .durable(ORDER_BC_ORDER_PICKEDUP_QUEUE)
                .build();
    }

    @Bean(RESTAURANT_BC_ORDER_DELIVERED_QUEUE)
    public Queue restarauntBcOrderDeliveredQueue() {
        return QueueBuilder
                .durable(RESTAURANT_BC_ORDER_DELIVERED_QUEUE)
                .build();
    }


    @Bean(ORDER_BC_ORDER_DELIVERED_QUEUE)
    public Queue orderBcOrderDeliveredQueue() {
        return QueueBuilder
                .durable(ORDER_BC_ORDER_DELIVERED_QUEUE)
                .build();
    }


    @Bean(ORDER_BC_DISH_PUBLISHED_QUEUE)
    public Queue dishPublishedQueue() {
        return QueueBuilder
                .durable(ORDER_BC_DISH_PUBLISHED_QUEUE)
                .build();
    }


    @Bean(ORDER_BC_DISH_UNPUBLISHED_QUEUE)
    public Queue dishUnpublishedQueue() {
        return QueueBuilder
                .durable(ORDER_BC_DISH_UNPUBLISHED_QUEUE)
                .build();
    }


    @Bean(ORDER_BC_DISH_IN_STOCK_QUEUE)
    public Queue dishInStockQueue() {
        return QueueBuilder
                .durable(ORDER_BC_DISH_IN_STOCK_QUEUE)
                .build();
    }


    @Bean(ORDER_BC_DISH_OUT_OF_STOCK_QUEUE)
    public Queue dishOutOfStockQueue() {
        return QueueBuilder
                .durable(ORDER_BC_DISH_OUT_OF_STOCK_QUEUE)
                .build();
    }


    // ---------- ORDER PLACED ----------
    @Bean
    public Binding orderPlacedBinding(
            @Qualifier(RESTAURANT_BC_ORDER_PLACED_QUEUE) Queue orderPlacedQueue,
            TopicExchange kdgEvent) {
        return BindingBuilder
                .bind(orderPlacedQueue)
                .to(kdgEvent)
                .with("order.*.order.placed.v1");
    }

    // ---------- ORDER DECLINED ----------
    @Bean
    public Binding orderDeclinedBinding(
            @Qualifier(ORDER_BC_ORDER_DECLINED_QUEUE) Queue orderDeclinedQueue,
            TopicExchange kdgEvent) {
        return BindingBuilder
                .bind(orderDeclinedQueue)
                .to(kdgEvent)
                .with("restaurant.*.order.declined.v1");
    }

    // ---------- ORDER REJECTED ----------
    @Bean
    public Binding orderRejectedBinding(
            @Qualifier(ORDER_BC_ORDER_REJECTED_QUEUE) Queue orderRejectedQueue,
            TopicExchange kdgEvent) {
        return BindingBuilder
                .bind(orderRejectedQueue)
                .to(kdgEvent)
                .with("restaurant.*.order.rejected.v1");
    }

    // ---------- ORDER ACCEPTED ----------
    @Bean
    public Binding orderAcceptedBinding(
            @Qualifier(ORDER_BC_ORDER_ACCEPTED_QUEUE) Queue orderBcOrderAcceptedQueue,
            TopicExchange kdgEvent) {
        return BindingBuilder
                .bind(orderBcOrderAcceptedQueue)
                .to(kdgEvent)
                .with("restaurant.*.order.accepted.v1");
    }

    @Bean
    public Binding deliveryOrderAcceptedBinding(
            @Qualifier(DELIVERY_BC_ORDER_ACCEPTED_QUEUE) Queue deliveryBcOrderAcceptedQueue,
            TopicExchange kdgEvent) {
        return BindingBuilder
                .bind(deliveryBcOrderAcceptedQueue)
                .to(kdgEvent)
                .with("restaurant.*.order.accepted.v1");
    }

    // ---------- ORDER READY ----------
    @Bean
    public Binding orderReadyBinding(
            @Qualifier(ORDER_BC_ORDER_READY_QUEUE) Queue orderBcOrderReadyQueue,
            TopicExchange kdgEvent) {
        return BindingBuilder
                .bind(orderBcOrderReadyQueue)
                .to(kdgEvent)
                .with("restaurant.*.order.ready.v1");
    }

    @Bean
    public Binding deliveryOrderReadyBinding(
            @Qualifier(DELIVERY_BC_ORDER_READY_QUEUE) Queue deliveryBcOrderReadyQueue,
            TopicExchange kdgEvent) {
        return BindingBuilder
                .bind(deliveryBcOrderReadyQueue)
                .to(kdgEvent)
                .with("restaurant.*.order.ready.v1");
    }

    // ---------- ORDER PICKED UP ----------
    @Bean
    public Binding restaurantOrderPickedupBinding(
            @Qualifier(RESTAURANT_BC_ORDER_PICKEDUP_QUEUE) Queue restaurantBcOrderPickedupQueue,
            TopicExchange kdgEvent) {
        return BindingBuilder
                .bind(restaurantBcOrderPickedupQueue)
                .to(kdgEvent)
                .with("delivery.*.order.pickedup.v1");

    }

    @Bean
    public Binding orderPickedupBinding(
            @Qualifier(ORDER_BC_ORDER_PICKEDUP_QUEUE) Queue orderBcOrderPickedupQueue,
            TopicExchange kdgEvent) {
        return BindingBuilder
                .bind(orderBcOrderPickedupQueue)
                .to(kdgEvent)
                .with("delivery.*.order.pickedup.v1");
    }

    // ---------- ORDER DELIVERED ----------
    @Bean
    public Binding restaurantOrderDeliveredBinding(
            @Qualifier(RESTAURANT_BC_ORDER_DELIVERED_QUEUE) Queue restaurantBcOrderDeliveredQueue,
            TopicExchange kdgEvent) {
        return BindingBuilder
                .bind(restaurantBcOrderDeliveredQueue)
                .to(kdgEvent)
                .with("delivery.*.order.delivered.v1");
    }

    @Bean
    public Binding orderDeliveredBinding(
            @Qualifier(ORDER_BC_ORDER_DELIVERED_QUEUE) Queue orderBcOrderDeliveredQueue,
            TopicExchange kdgEvent) {
        return BindingBuilder
                .bind(orderBcOrderDeliveredQueue)
                .to(kdgEvent)
                .with("delivery.*.order.delivered.v1");
    }


    @Bean
    public Binding dishPublishedBinding(@Qualifier(ORDER_BC_DISH_PUBLISHED_QUEUE) Queue dishPublishedQueue, TopicExchange kdgEvent) {
        return BindingBuilder
                .bind(dishPublishedQueue)
                .to(kdgEvent)
                .with("restaurant.*.dish.published.v1");
    }

    @Bean
    public Binding dishUnPublishedBinding(@Qualifier(ORDER_BC_DISH_UNPUBLISHED_QUEUE) Queue dishUnPublishedQueue, TopicExchange kdgEvent) {
        return BindingBuilder
                .bind(dishUnPublishedQueue)
                .to(kdgEvent)
                .with("restaurant.*.dish.unpublished.v1");
    }


    @Bean
    public Binding dishInStockBinding(@Qualifier(ORDER_BC_DISH_IN_STOCK_QUEUE) Queue dishInStockQueue, TopicExchange kdgEvent) {
        return BindingBuilder
                .bind(dishInStockQueue)
                .to(kdgEvent)
                .with("restaurant.*.dish.in-stock.v1");
    }

    @Bean
    public Binding dishOutOfStockBinding(@Qualifier(ORDER_BC_DISH_OUT_OF_STOCK_QUEUE) Queue dishOutOfStockQueue, TopicExchange kdgEvent) {
        return BindingBuilder
                .bind(dishOutOfStockQueue)
                .to(kdgEvent)
                .with("restaurant.*.dish.out-of-stock.v1");
    }


    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
        simpleRabbitListenerContainerFactory.setMessageConverter(jackson2JsonMessageConverter());

        return simpleRabbitListenerContainerFactory;
    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
