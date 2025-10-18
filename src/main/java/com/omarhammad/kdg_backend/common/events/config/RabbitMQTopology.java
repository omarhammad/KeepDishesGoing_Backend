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

    public static final String KDG_EXCHANGE = "kdg.exchange";
    public static final String ORDER_PLACED_QUEUE = "order.placed";
    public static final String ORDER_REJECTED_QUEUE = "order.rejected";
    public static final String ORDER_DECLINED_QUEUE = "order.declined";
    public static final String ORDER_ACCEPTED_QUEUE = "order.accepted";
    public static final String ORDER_READY_FOR_PICKUP_QUEUE = "order.ready-for-pickup";
    public static final String DISH_PUBLISHED_QUEUE = "dish.published";
    public static final String DISH_UNPUBLISHED_QUEUE = "dish.unpublished";
    public static final String DISH_OUT_OF_STOCK_QUEUE = "dish.out-of-stock";
    public static final String DISH_IN_STOCK_QUEUE = "dish.in-stock";

    @Bean
    public TopicExchange kdgExchange() {
        return ExchangeBuilder
                .topicExchange(KDG_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean(ORDER_PLACED_QUEUE)
    public Queue orderPlacedQueue() {
        return QueueBuilder
                .durable(ORDER_PLACED_QUEUE)
                .build();
    }

    @Bean(ORDER_DECLINED_QUEUE)
    public Queue orderDeclinedQueue() {
        return QueueBuilder
                .durable(ORDER_DECLINED_QUEUE)
                .build();
    }

    @Bean(ORDER_REJECTED_QUEUE)
    public Queue orderRejectedQueue() {
        return QueueBuilder
                .durable(ORDER_REJECTED_QUEUE)
                .build();
    }

    @Bean(ORDER_ACCEPTED_QUEUE)
    public Queue orderAcceptedQueue() {
        return QueueBuilder
                .durable(ORDER_ACCEPTED_QUEUE)
                .build();
    }

    @Bean(ORDER_READY_FOR_PICKUP_QUEUE)
    public Queue orderReadyForPickUpQueue() {
        return QueueBuilder
                .durable(ORDER_READY_FOR_PICKUP_QUEUE)
                .build();
    }

    @Bean(DISH_PUBLISHED_QUEUE)
    public Queue dishPublishedQueue() {
        return QueueBuilder
                .durable(DISH_PUBLISHED_QUEUE)
                .build();
    }


    @Bean(DISH_UNPUBLISHED_QUEUE)
    public Queue dishUnpublishedQueue() {
        return QueueBuilder
                .durable(DISH_UNPUBLISHED_QUEUE)
                .build();
    }


    @Bean(DISH_IN_STOCK_QUEUE)
    public Queue dishInStockQueue() {
        return QueueBuilder
                .durable(DISH_IN_STOCK_QUEUE)
                .build();
    }


    @Bean(DISH_OUT_OF_STOCK_QUEUE)
    public Queue dishOutOfStockQueue() {
        return QueueBuilder
                .durable(DISH_OUT_OF_STOCK_QUEUE)
                .build();
    }

    @Bean
    public Binding orderPlacedBinding(@Qualifier(ORDER_PLACED_QUEUE) Queue orderPlacedQueue, TopicExchange kdgExchange) {
        return BindingBuilder
                .bind(orderPlacedQueue)
                .to(kdgExchange)
                .with("kdg.*.order.placed");  //kdg.<ORDER_ID>.order.placed
    }

    @Bean
    public Binding orderDeclinedBinding(@Qualifier(ORDER_DECLINED_QUEUE) Queue orderDeclinedQueue, TopicExchange kdgExchange) {
        return BindingBuilder
                .bind(orderDeclinedQueue)
                .to(kdgExchange)
                .with("kdg.*.order.declined"); //kdg.<ORDER_ID>.order.declined
    }


    @Bean
    public Binding orderRejectedBinding(@Qualifier(ORDER_REJECTED_QUEUE) Queue orderRejectedQueue, TopicExchange kdgExchange) {
        return BindingBuilder
                .bind(orderRejectedQueue)
                .to(kdgExchange)
                .with("kdg.*.order.rejected"); //kdg.<ORDER_ID>.order.rejected
    }

    @Bean
    public Binding orderAcceptedBinding(@Qualifier(ORDER_ACCEPTED_QUEUE) Queue orderAcceptedQueue, TopicExchange kdgExchange) {
        return BindingBuilder
                .bind(orderAcceptedQueue)
                .to(kdgExchange)
                .with("kdg.*.order.accepted"); //kdg.<ORDER_ID>.order.accepted
    }

    @Bean
    public Binding orderReadyForPickUpBinding(@Qualifier(ORDER_READY_FOR_PICKUP_QUEUE) Queue orderReadyForPickUpQueue, TopicExchange kdgExchange) {
        return BindingBuilder
                .bind(orderReadyForPickUpQueue)
                .to(kdgExchange)
                .with("kdg.*.order.ready-for-pickup"); //kdg.<ORDER_ID>.order.ready-for-pick-up
    }

    @Bean
    public Binding dishPublishedBinding(@Qualifier(DISH_PUBLISHED_QUEUE) Queue dishPublishedQueue, TopicExchange kdgExchange) {
        return BindingBuilder
                .bind(dishPublishedQueue)
                .to(kdgExchange)
                .with("kdg.*.dish.published"); //kdg.<DISH_ID>.dish.published
    }

    @Bean
    public Binding dishUnPublishedBinding(@Qualifier(DISH_UNPUBLISHED_QUEUE) Queue dishUnPublishedQueue, TopicExchange kdgExchange) {
        return BindingBuilder
                .bind(dishUnPublishedQueue)
                .to(kdgExchange)
                .with("kdg.*.dish.unpublished"); //kdg.<DISH_ID>.dish.unpublished
    }


    @Bean
    public Binding dishInStockBinding(@Qualifier(DISH_IN_STOCK_QUEUE) Queue dishInStockQueue, TopicExchange kdgExchange) {
        return BindingBuilder
                .bind(dishInStockQueue)
                .to(kdgExchange)
                .with("kdg.*.dish.in-stock"); //kdg.<DISH_ID>.dish.in-stock
    }

    @Bean
    public Binding dishOutOfStockBinding(@Qualifier(DISH_OUT_OF_STOCK_QUEUE) Queue dishOutOfStockQueue, TopicExchange kdgExchange) {
        return BindingBuilder
                .bind(dishOutOfStockQueue)
                .to(kdgExchange)
                .with("kdg.*.dish.out-of-stock"); //kdg.<DISH_ID>.dish.out-of-stock
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
