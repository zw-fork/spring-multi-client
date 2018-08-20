package com.github.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 日志消息 生产者
 * Created by leolin on 8/1/2018.
 */
public class EmitLog {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 申明交换器  如果当前没有队列被绑定到交换器，消息将被丢弃
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

//      分发消息
        for(int i = 0 ; i < 5; i++){
            String message = "Hello World! " + i;
            // 消息发送给交换器，而不是发送给队列。
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
        channel.close();
        connection.close();
    }
}
