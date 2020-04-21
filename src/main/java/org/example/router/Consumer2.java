package org.example.router;

import com.rabbitmq.client.*;
import org.example.util.RabbitMqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer2 {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMqUtil.getInstance().newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("logs-direct", "direct");
        // 声明一个临时队列
        String queueName = channel.queueDeclare().getQueue();
        // 将队列绑定到交换机并且设置路由
        channel.queueBind(queueName, "logs-direct", "info");
        channel.queueBind(queueName, "logs-direct", "error");
        // 参数依次的含义，1-队列名、2-是否自动确认、3-消费对象实例
        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
                System.out.println("接受的消息:" + new String(bytes));
            }
        });
    }

}
