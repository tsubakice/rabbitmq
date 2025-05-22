package org.qiaice.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class RabbitMQListener {

    // @RabbitListener(queues = "queue")
    // public void receive1(Message message, Channel channel) {
    //     System.out.println("Received message(1): " + new String(message.getBody(), StandardCharsets.UTF_8));
    //     System.out.println("Received channelId(1): " + channel.getChannelNumber());
    // }

    // 多个消费者监听同一条队列时, 默认使用轮询分发消息, 一次轮询直接分发 250 条消息
    // 显示效果如果像是一次轮询只分发 1 条消息的话, 纯属消息入队速度太慢

    // 除了编写多个消费者外, 也可以直接在注解中指定消费者的数量: concurrency

    @RabbitListener(queues = "queue", concurrency = "3")
    public void receive(Message message, Channel channel) {
        int channelNumber = channel.getChannelNumber();
        String decoded = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println("Received message(" + channelNumber + "): " + decoded);
    }
}
