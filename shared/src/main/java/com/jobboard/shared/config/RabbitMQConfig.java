package com.jobboard.shared.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
public class RabbitMQConfig {

    //Exchanges
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("companyRatingExchange");
    }

    @Bean
    public FanoutExchange fanoutExchangeDLX() {
        return new FanoutExchange("companyRatingExchangeDLX");
    }


    //Queues
    @Bean
    public Queue companyRatingQueue() {
        Map<String, Object> args = new HashMap<>();

        args.put("x-dead-letter-exchange", "companyRatingExchangeDLX");
        // args.put("x-dead-letter-routing-key", "companyRatingExchangeDLX");

        return new Queue("companyRatingQueue", true, false, false, args);
    }

    @Bean
    public Queue companyRatingQueueDLQ() {
        return new Queue("companyRatingQueueDLQ");
    }

    @Bean
    public Queue companyRatingQueueDLQParkingLot() {
        return new Queue("companyRatingQueueDLQParkingLot");
    }


    //Bindings
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(companyRatingQueue()).to(fanoutExchange());
    }


    @Bean
    public Binding bindingDLQ() {
        return BindingBuilder.bind(companyRatingQueueDLQ()).to(fanoutExchangeDLX());
    }


    //Other Configurations

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationReadyEventApplicationListener(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        rabbitTemplate.setMessageConverter(jsonMessageConverter());

        return rabbitTemplate;
    }

}
