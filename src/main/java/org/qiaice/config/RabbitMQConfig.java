package org.qiaice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean(value = "direct")
    public Exchange exchange() {
        return ExchangeBuilder.directExchange("amq.direct").build();
    }

    @Bean(value = "queue1")
    public Queue queue() {
        return QueueBuilder.nonDurable("queue1").build();
    }

    @Bean(value = "direct-queue1")
    public Binding binding(
            @Qualifier(value = "direct") Exchange exchange,
            @Qualifier(value = "queue1") Queue queue
    ) {
        return BindingBuilder.bind(queue).to(exchange).with("queue1").noargs();
    }
}
