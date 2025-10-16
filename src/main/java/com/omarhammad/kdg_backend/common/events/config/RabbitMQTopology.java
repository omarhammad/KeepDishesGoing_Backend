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
    public static final String ORDER_ACCEPTED_QUEUE = "order.accepted";

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

    @Bean
    public Binding orderPlacedBinding(@Qualifier(ORDER_PLACED_QUEUE) Queue orderPlacedQueue, TopicExchange kdgExchange) {
        return BindingBuilder
                .bind(orderPlacedQueue)
                .to(kdgExchange)
                .with("kdg.*.order.placed");  //kdg.<ORDER_ID>.order.placed
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
