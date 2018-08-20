package com.github.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by leolin on 8/1/2018.
 */
public class Work2 {
    private final static String QUEUE_NAME = "t-hello";

    public static void main(String[] argv) throws Exception {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
//		设置RabbitMQ地址
        factory.setHost("localhost");
        factory.setPort(5672);
//		创建一个新的连接
        Connection connection = factory.newConnection();
//		创建一个频道
        Channel channel = connection.createChannel();
//		声明要关注的队列 -- 在RabbitMQ中，队列声明是幂等性的（一个幂等操作的特点是其任意多次执行所产生的影响均与一次执行的影响相同），也就是说，如果不存在，就创建，如果存在，不会对已经存在的队列产生任何影响。
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println("C [*] Waiting for messages. To exit press CTRL+C");

        // 每次获取消息的数量
        channel.basicQos(1);
//		DefaultConsumer类实现了Consumer接口，通过传入一个频道，告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
                String message = new String(body, "UTF-8");

                System.out.println("Worker2 [x] Received '" + message + "'");
                try {
                    doWork(message);
                } finally {
                    System.out.println("Worker2 [x] Done");
                    // 消息处理完成确认
                    try {
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("C [x] Received '" + message + "'");
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
