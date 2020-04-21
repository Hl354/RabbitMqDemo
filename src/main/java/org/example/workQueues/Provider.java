package org.example.workQueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.util.RabbitMqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Provider {

    /**
     * 工作队列模式，这种模式配合手动确认，可以根据不同的处理能力分配不同的消息，提高响应能力
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = RabbitMqUtil.getInstance().newConnection();
        // 创建管道
        Channel channel = connection.createChannel();
        // 参数依次的含义，1-队列名、2-是否持久化、3-是否独占、4-是否当队列为空时(连接已断开)删除队列、5-额为信息
        channel.queueDeclare("SimpleMode", false, false, false, null);
        // 参数依次的含义，1-交换机名称、2-路由(或者队列)名称、3-额为信息、4-信息字节
        for (int i = 1; i <= 10; i++) {
            channel.basicPublish("", "SimpleMode", null, ("SimpleMode-" + i).getBytes());
        }
        // 关闭连接
        RabbitMqUtil.closeConnection(connection, channel);
    }

}
