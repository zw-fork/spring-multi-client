package com.github.mq;

import com.rabbitmq.client.*;
import org.springframework.boot.autoconfigure.jms.JmsProperties;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by leolin on 8/1/2018.
 */
public class ReceiveLogsDirect1 {
    // 交换器名称
    private static final String EXCHANGE_NAME = "direct_logs2";

    // 队列名称
    private static final String QUEUE_NAME = "direct_logs_queue_error";

    // 路由关键字
    private static final String[] routingKeys = new String[]{"error"};

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
//		声明交换器 并且持久化
        channel.exchangeDeclare(EXCHANGE_NAME, "direct",true);
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
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
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
                // 处理完成之后再确认
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        // 关闭自动确认
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }

}
