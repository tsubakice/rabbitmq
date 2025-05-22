package org.qiaice.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean(value = "fanout")
    public Exchange exchange() {
        return ExchangeBuilder.fanoutExchange("my.fanout").durable(true).build();
    }

    @Bean(value = "queue1")
    public Queue queue1() {
        return QueueBuilder.durable("queue1").build();
    }

    @Bean(value = "queue2")
    public Queue queue2() {
        return QueueBuilder.durable("queue2").build();
    }

    @Bean(value = "binding1")
    public Binding binding1(
            @Qualifier(value = "fanout") Exchange exchange,
            @Qualifier(value = "queue1") Queue queue
    ) {
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }

    @Bean(value = "binding2")
    public Binding binding3(
            @Qualifier(value = "fanout") Exchange exchange,
            @Qualifier(value = "queue2") Queue queue
    ) {
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }
}
