package org.example.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.util.RabbitMqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Provider {

    /**
     * 路由模式+动态匹配，一个发布根据不同的路由给到不同的队列，路由可以动态匹配
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = RabbitMqUtil.getInstance().newConnection();
        // 创建管道
        Channel channel = connection.createChannel();
        // 参数依次的含义，1-交换机名称、2-交换机类型
        channel.exchangeDeclare("logs-topic", "topic");
        // 发布消息到交换机或者队列，参数依次的含义，1-交换机名称、2-路由(或者队列)名称、3-额为信息、4-信息字节
        channel.basicPublish("logs-topic", "info.update", null, "TopicMode info.update".getBytes());
        channel.basicPublish("logs-topic", "info.insert", null, "TopicMode info.insert".getBytes());
        // 关闭连接
        RabbitMqUtil.closeConnection(connection, channel);
    }

}
