package org.example.workQueues;

import com.rabbitmq.client.*;
import org.example.util.RabbitMqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer2 {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMqUtil.getInstance().newConnection();
        final Channel channel = connection.createChannel();
        channel.basicQos(1);
        // 参数依次的含义，1-队列名、2-是否自动确认、3-消费对象实例
        channel.basicConsume("SimpleMode", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
                System.out.println("接受的消息:" + new String(bytes));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }

}
