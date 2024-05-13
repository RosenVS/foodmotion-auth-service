package com.individual.authservice.RabbitMQ.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.string.name}")
    private String exchangeJson;

    @Value("${rabbitmq.queue.string.name}")
    private String queueNameJson;

    @Value("${rabbitmq.routing.string.key}")
    private String routingKeyJson;

    @Value("${rabbitmq.accountauthdeletion.exchange.json.name}")
    private String accountAuthDeletionExchangeJson;

    @Value("${rabbitmq.accountauthdeletion.queue.json.name}")
    private String accountAuthDeletionQueueJson;

    @Value("${rabbitmq.accountauthdeletion.routing.json.key}")
    private String accountAuthDeletionRoutingKeyJson;


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }



    @Bean
    public Queue queueJson() {
        return new Queue(queueNameJson, true);
    }

    @Bean
    public TopicExchange exchangeJson() {
        return new TopicExchange(exchangeJson);
    }



    @Bean
    public Queue queueAccountAuthDeletionJson() {
        return new Queue(accountAuthDeletionQueueJson, true);
    }

    @Bean
    public TopicExchange exchangeAccountAuthDeletionJson() {
        return new TopicExchange(accountAuthDeletionExchangeJson);
    }

    @Bean
    public Binding bindingJson(Queue queueJson, TopicExchange exchangeJson) {
        return BindingBuilder.bind(queueJson).to(exchangeJson).with(routingKeyJson);
    }

    @Bean
    public Binding bindingAccountAuthDeletionJson(Queue queueAccountAuthDeletionJson, TopicExchange exchangeAccountAuthDeletionJson) {
        return BindingBuilder.bind(queueAccountAuthDeletionJson).to(exchangeAccountAuthDeletionJson).with(accountAuthDeletionRoutingKeyJson);
    }


}