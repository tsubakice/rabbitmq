package org.qiaice.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean(value = "header")
    public HeadersExchange exchange() {
        return ExchangeBuilder.headersExchange("my.header").durable(true).build();
    }

    @Bean(value = "queue")
    public Queue queue() {
        return QueueBuilder.durable("queue").build();
    }

    @Bean(value = "binding")
    public Binding binding1(
            @Qualifier(value = "header") HeadersExchange exchange,
            @Qualifier(value = "queue") Queue queue
    ) {
        return BindingBuilder.bind(queue).to(exchange)
                // .where("test").matches("test");
                // .whereAny("a", "b").exist();
                .whereAll("a", "b").exist();
    }
}
