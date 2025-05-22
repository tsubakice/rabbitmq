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

    @Bean(value = "dDirect")
    public Exchange dExchange() {
        return ExchangeBuilder.directExchange("my.d.direct").durable(true).build();
    }

    @Bean(value = "dQueue")
    public Queue dQueue() {
        return QueueBuilder.durable("dQueue").build();
    }

    @Bean(value = "d-direct-queue")
    public Binding dBinding(
            @Qualifier(value = "dDirect") Exchange exchange,
            @Qualifier(value = "dQueue") Queue queue
    ) {
        return BindingBuilder.bind(queue).to(exchange).with("dQueue").noargs();
    }

    @Bean(value = "direct")
    public Exchange exchange() {
        return ExchangeBuilder.directExchange("my.direct").durable(true).build();
    }

    @Bean(value = "queue")
    public Queue queue() {
        return QueueBuilder.durable("queue")
                .deadLetterExchange("my.d.direct")
                .deadLetterRoutingKey("dQueue")
                // 默认情况下需要拒绝消息并不让消息入队, 才能使消息进入死信队列
                // .ttl(5000) // 设置过期时间, 时间到了但未被处理的消息会被发送到死信队列
                .maxLength(2) // 设置队列长度, 溢出的消息会被发送到死信队列
                .build();
    }

    @Bean(value = "direct-queue")
    public Binding binding(
            @Qualifier(value = "direct") Exchange exchange,
            @Qualifier(value = "queue") Queue queue
    ) {
        return BindingBuilder.bind(queue).to(exchange).with("queue").noargs();
    }
}
