package com.github.mq;

import com.rabbitmq.client.*;

import java.io.UnsupportedEncodingException;

/**
 * Created by leolin on 8/1/2018.
 */
public class ReceiveLogsDirect2 {
    // 交换器名称
    private static final String EXCHANGE_NAME = "direct_logs";

    private static final String QUEUE_NAME = "direct_logs_queue_error";

    // 路由关键字
    private static final String[] routingKeys = new String[]{"error"};

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
//		声明交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
//		获取匿名队列名称
//        String queueName = channel.queueDeclare().getQueue();
//		根据路由关键字进行多重绑定
        for (String severity : routingKeys) {
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, severity);
            System.out.println("ReceiveLogsDirect1 exchange:"+EXCHANGE_NAME+", queue:"+QUEUE_NAME+", BindRoutingKey:" + severity);
        }
        System.out.println("ReceiveLogsDirect1 [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                String message = null;
                try {
                    message = new String(body, "UTF-8");
                    try {
                        Thread.sleep(1000); // 暂停1秒钟
                    } catch (InterruptedException _ignored) {
                        Thread.currentThread().interrupt();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
