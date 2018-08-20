package com.github.mq;

import com.rabbitmq.client.*;

import java.io.UnsupportedEncodingException;

/**
 * Created by leolin on 8/1/2018.
 */
public class ReceiveLogs2 {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 申明交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // 生成队列名称
        String queueName = channel.queueDeclare().getQueue();

        // 绑定队列和交换器
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body){
                String message = null;
                try {
                    message = new String(body, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
