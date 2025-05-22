package org.qiaice.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean(value = "direct")
    public Exchange exchange() {
        return ExchangeBuilder.directExchange("my.direct").durable(true).build();
    }

    @Bean(value = "queue")
    public Queue queue() {
        return QueueBuilder.durable("queue").build();
    }

    @Bean(value = "binding1")
    public Binding binding1(
            @Qualifier(value = "direct") Exchange exchange,
            @Qualifier(value = "queue") Queue queue
    ) {
        return BindingBuilder.bind(queue).to(exchange).with("queue1").noargs();
    }

    @Bean(value = "binding2")
    public Binding binding2(
            @Qualifier(value = "direct") Exchange exchange,
            @Qualifier(value = "queue") Queue queue
    ) {
        return BindingBuilder.bind(queue).to(exchange).with("queue2").noargs();
    }
}
