package org.qiaice.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class RabbitMQListener {

    @RabbitListener(queues = "queue1")
    public String receive(Message message) {
        System.out.println("Received message: " + new String(message.getBody(), StandardCharsets.UTF_8));
        return "响应成功";
    }
}
