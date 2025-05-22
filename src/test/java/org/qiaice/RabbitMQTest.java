package org.qiaice;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

class RabbitMQTest {

    static ConnectionFactory factory = new ConnectionFactory();

    static {
        factory.setHost("47.108.130.188");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("040905");
        factory.setVirtualHost("/rabbitmq");
    }

    @Test
    void producerTest() {
        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()
        ) {
            // queueDeclare 方法参数
            // s: 队列名称
            // b: 是否持久化
            // b1: 是否为排他队列
            // b2: 是否自动删除
            // map: 一些其他参数
            channel.queueDeclare("queue1", false, false, false, null);
            // queueBind 方法参数
            // s: 队列名称
            // s1: 交换机名称
            // s2: routingKey
            channel.queueBind("queue1", "amq.direct", "queue1");
            // basicPublish 方法参数
            // s: 交换机名称
            // s1: routingKey
            // basicProperties: 一些其他参数
            // bytes: 消息的 bytes 形式
            channel.basicPublish("amq.direct", "queue1", null, "Hello World".getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void consumerTest() throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // basicConsume 方法参数
        // s: 消息队列名称
        // b: 是否自动应答
        // deliverCallback: 消息接收后的函数回调
        // cancelCallback: 当消费者取消订阅时进行的函数回调
        channel.basicConsume("queue1", false, (s, delivery) -> {
            System.out.println("Resolve: " + new String(delivery.getBody()));
            // basicAck, 确认应答, 第一个参数是当前的消息标签, 第二个参数是是否批量处理消息队列中所有的消息, 如果为 false 表示只处理当前消息
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            // basicNack, 拒绝应答, 最后一个参数表示是否将当前消息放回队列, 如果为 false 那么消息就会被丢弃
            // channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
            // 跟上面一样, 最后一个参数为 false 只不过这里省了
            // channel.basicReject(delivery.getEnvelope().getDeliveryTag(), false);
        }, s -> {});
    }
}
