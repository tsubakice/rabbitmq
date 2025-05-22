package org.qiaice.listener;

import com.rabbitmq.client.Channel;
import org.qiaice.entity.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class RabbitMQListener {

    @RabbitListener(queues = "dQueue", messageConverter = "jackson2JsonMessageConverter")
    public void receive(User user, Message message, Channel channel) {
        System.out.println("Received user: " + user);
        System.out.println("Received message: " + new String(message.getBody(), StandardCharsets.UTF_8));
        System.out.println("Received channelId: " + channel.getChannelNumber());
    }
}
