package com.github.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by leolin on 8/1/2018.
 */
public class Work1 {
    private final static String QUEUE_NAME = "t-hello";

    public static void main(String[] argv) throws Exception {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
//		设置RabbitMQ地址
        factory.setHost("localhost");
        factory.setPort(5672);
//
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println("Worker1 [*] Waiting for messages. To exit press CTRL+C");
        // 每次从队列中获取数量
        channel.basicQos(1);

        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                System.out.println("Worker1 [x] Received '" + message + "'");
                try {
                    doWork(message);
                } finally {
                    System.out.println("Worker1 [x] Done");
                    // 消息处理完成确认
                    channel.basicAck(envelope.getDeliveryTag(), false);
                    // 拒绝消息
//                    channel.basicReject(envelope.getDeliveryTag(), false);
                }
            }
        };

//		自动回复队列应答 -- RabbitMQ中的消息确认机制，后面章节会详细讲解
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }


    private static void doWork(String message) {
        try {
            Thread.sleep(1000); // 暂停1秒钟
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
