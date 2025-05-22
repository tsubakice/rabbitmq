package org.qiaice.listener;

import com.rabbitmq.client.Channel;
import org.qiaice.entity.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class RabbitMQListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue1", durable = "true"),
            exchange = @Exchange(value = "amq.direct"),
            key = "queue1"),
            messageConverter = "jackson2JsonMessageConverter")
    public String receive(User user, Message message, Channel channel) {
        System.out.println("Received user: " + user);
        System.out.println("Received message: " + new String(message.getBody(), StandardCharsets.UTF_8));
        System.out.println("Received channel: " + channel.getChannelNumber());
        return "响应成功";
    }
}
