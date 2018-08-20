package com.github.mq;

import com.rabbitmq.client.*;

/**
 * Created by leolin on 8/1/2018.
 */
public class RoutingSendDirect {
    private static final String EXCHANGE_NAME = "direct_logs2";
    // 路由关键字
    private static final String[] routingKeys = new String[]{"info" ,"warning", "error"};

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
//		声明交换器  direct直连
        channel.exchangeDeclare(EXCHANGE_NAME, "direct",true);
//        BasicProperties properties = MessageProperties.PERSISTENT_TEXT_PLAIN;
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.deliveryMode(2);
        builder.expiration("60000");
        builder.priority(0);
        AMQP.BasicProperties properties = builder.build();

//		发送消息
        for(String severity :routingKeys){
            for(int i=0;i<10;i++) {
                String message = "Send the message level:" + severity+"---"+i;
                // 消息持久化。
                channel.basicPublish(EXCHANGE_NAME, severity, properties, message.getBytes());
//            channel.basicPublish(EXCHANGE_NAME, severity, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                System.out.println(" [x] Sent '" + severity + "':'" + message + "'");
            }
        }
        channel.close();
        connection.close();
    }
}
