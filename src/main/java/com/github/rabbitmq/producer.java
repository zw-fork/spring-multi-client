package com.github.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 生产者
 * Created by leolin on 8/1/2018.
 */
public class Producer {

    private static final String EXCHANGE_NAME = "direct_logs3";
    private static final String ERROR_EXCHANGE_NAME = "error_direct_logs";
    // 路由关键字
    private static final String[] routingKeys = new String[]{"info" ,"warning", "error"};

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername("guest");
        factory.setPassword("guest");

        factory.setHost("localhost");
        factory.setPort(5672);

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        // 设置一个保存错误情况的
        channel.exchangeDeclare(ERROR_EXCHANGE_NAME, "direct");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x-expires", 6000);
        // 死信 处理
//        params.put("x-dead-letter-exchange", ERROR_EXCHANGE_NAME);
//        params.put("x-dead-letter-routing-key", "some-routing-key");

        //声明交换器  direct直连
        channel.exchangeDeclare(EXCHANGE_NAME, "direct",true,false,false,params);
//        channel.exchangeDeclare(EXCHANGE_NAME, "direct",true);
//        BasicProperties properties = MessageProperties.PERSISTENT_TEXT_PLAIN;
        // 构造时间，持久化等参数。
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.deliveryMode(2);
        builder.expiration("6000");
        builder.priority(0);
        AMQP.BasicProperties properties = builder.build();

//		发送消息
        for(String severity :routingKeys) {
            for (int i = 0; i < 10; i++){
                String message = "Send the message level:" + severity+" -- "+i;
                // 消息持久化。
                channel.basicPublish(EXCHANGE_NAME, severity, properties, message.getBytes());
//              channel.basicPublish(EXCHANGE_NAME, severity, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                System.out.println(" [x] Sent '" + severity + "':'" + message + "'");
            }
        }
        channel.close();
        connection.close();
    }

}
