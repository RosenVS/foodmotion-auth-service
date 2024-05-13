package com.individual.authservice.RabbitMQ.publisher;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonProducer {
    @Value("${rabbitmq.exchange.string.name}")
    private String exchange;

    @Value("${rabbitmq.routing.string.key}")
    private String routingJsonKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(com.individual.authservice.RabbitMQ.publisher.RabbitMQJsonProducer.class);
    private RabbitTemplate rabbitTemplate;

    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void registerAccountDetailsMessage (String userUID){
        LOGGER.info(String.format("Register default account Details Message send -> %s", userUID));
        rabbitTemplate.convertAndSend(exchange, routingJsonKey, userUID);
    }
}

