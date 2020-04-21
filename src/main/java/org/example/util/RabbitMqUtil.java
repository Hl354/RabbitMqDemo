package org.example.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMqUtil {

    // volatile保证可见性和防止指令重排(防止指令颠倒，产生属性未设置的情况)
    private volatile static ConnectionFactory connectionFactory = null;

    // 构造方法私有化，这里其实不需要，因为返回的不是本类
    private RabbitMqUtil() {
    }

    // 懒汉式加双重检查构建单例
    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) {
            synchronized (RabbitMqUtil.class) {
                if (connectionFactory == null) {
                    connectionFactory = new ConnectionFactory();
                    // 依次设置主机、端口、账号、密码
                    connectionFactory.setHost("127.0.0.1");
                    connectionFactory.setPort(5672);
                    connectionFactory.setUsername("guest");
                    connectionFactory.setPassword("guest");
                }
            }
        }
        return connectionFactory;
    }

    // 关闭连接和管道
    public static void closeConnection(Connection connection, Channel channel) {
        try {
            if (channel != null) channel.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
