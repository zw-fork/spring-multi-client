package com.github.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leolin on 8/1/2018.
 */
public class P {
    private final static String QUEUE_NAME = "t-hello-3";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
//		设置RabbitMQ地址
        factory.setHost("localhost");
        factory.setPort(5672);
//		创建一个新的连接
        Connection connection = factory.newConnection();
//		创建一个频道
        Channel channel = connection.createChannel();

        // 设置队列消息过期时间
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x-expires", 6000);
//		声明一个队列 -- 在RabbitMQ中，队列声明是幂等性的（一个幂等操作的特点是其任意多次执行所产生的影响均与一次执行的影响相同），也就是说，如果不存在，就创建，如果存在，不会对已经存在的队列产生任何影响。
        channel.queueDeclare(QUEUE_NAME, false, false, false, params);
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.deliveryMode(2);
        builder.expiration("6000");
        builder.priority(0);
        AMQP.BasicProperties properties = builder.build();
        for(int i=0;i<10;i++) {
            String message = "Hello World----"+i;
//		发送消息到队列中
            channel.basicPublish("", QUEUE_NAME, properties, message.getBytes("UTF-8"));
            System.out.println("P [x] Sent '" + message + "'");
        }
//		关闭频道和连接
        channel.close();
        connection.close();
    }
}
