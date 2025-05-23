package org.qiaice.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean(value = "topic")
    public Exchange exchange() {
        return ExchangeBuilder.topicExchange("my.topic").durable(true).build();
    }

    @Bean(value = "queue")
    public Queue queue() {
        return QueueBuilder.durable("queue").build();
    }

    @Bean(value = "binding")
    public Binding binding1(
            @Qualifier(value = "topic") Exchange exchange,
            @Qualifier(value = "queue") Queue queue
    ) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                // .with("queue.*") // *: 任意一个单词
                .with("queue.#") // #: 任意个单词, 包括 0 个
                .noargs();
    }
}
