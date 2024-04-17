package com.example.bankingSolution.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ACCOUNT_QUEUE_NAME = "accountQueue";
    public static final String ACCOUNT_EXCHANGE_NAME = "accountExchange";
    public static final String ACCOUNT_ROUTING_KEY = "accountKey";

    public static final String TRANSACTION_QUEUE_NAME = "transactionQueue";
    public static final String TRANSACTION_EXCHANGE_NAME = "transactionExchange";
    public static final String TRANSACTION_ROUTING_KEY = "transactionKey";

    @Bean
    Queue accountQueue() {
        return new Queue(ACCOUNT_QUEUE_NAME, true);
    }

    @Bean
    Queue transactionQueue() {
        return new Queue(TRANSACTION_QUEUE_NAME, true);
    }

    @Bean
    DirectExchange accountExchange() {
        return new DirectExchange(ACCOUNT_EXCHANGE_NAME);
    }

    @Bean
    DirectExchange transactionExchange() {
        return new DirectExchange(TRANSACTION_EXCHANGE_NAME);
    }

    @Bean
    Binding accountBinding(Queue accountQueue, DirectExchange accountExchange) {
        return BindingBuilder.bind(accountQueue).to(accountExchange).with(ACCOUNT_ROUTING_KEY);
    }

    @Bean
    Binding transactionBinding(Queue transactionQueue, DirectExchange transactionExchange) {
        return BindingBuilder.bind(transactionQueue).to(transactionExchange).with(TRANSACTION_ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter(ObjectMapper jsonMapper) {
        return new Jackson2JsonMessageConverter(jsonMapper);
    }

}
