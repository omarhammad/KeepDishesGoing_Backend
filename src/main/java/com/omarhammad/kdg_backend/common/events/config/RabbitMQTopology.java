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
