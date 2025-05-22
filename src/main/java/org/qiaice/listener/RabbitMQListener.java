package org.qiaice.listener;

import org.qiaice.entity.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    @RabbitListener(queues = "queue1", messageConverter = "jackson2JsonMessageConverter")
    public String receive(User user) {
        System.out.println("Received message: " + user);
        return "响应成功";
    }
}
