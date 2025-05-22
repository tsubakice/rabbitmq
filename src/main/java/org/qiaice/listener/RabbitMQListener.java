package org.qiaice.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class RabbitMQListener {

    @RabbitListener(queues = "queue1")
    public void receive1(Message message, Channel channel) {
        int channelNumber = channel.getChannelNumber();
        String decoded = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println("Received message(" + channelNumber + "): " + decoded);
    }

    @RabbitListener(queues = "queue2")
    public void receive2(Message message, Channel channel) {
        int channelNumber = channel.getChannelNumber();
        String decoded = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println("Received message(" + channelNumber + "): " + decoded);
    }
}
