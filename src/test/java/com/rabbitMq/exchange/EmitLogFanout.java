package com.rabbitMq.exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by pc on 2017-08-03.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class EmitLogFanout {
    private final static String EXCHANGE_NAME = "logs";
    private final static String QUEUE_NAME = "fanout_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout",true);//fanout表示分发，所有的消费者得到同样的队列信息

        channel.queueDeclare("queueName",false,false,false,null);
        channel.queueBind("queueName", EXCHANGE_NAME, "routingKey");

        channel.queueDeclare("queueName1",false,false,false,null);
        channel.queueBind("queueName1", EXCHANGE_NAME, "routingKey1");

        for (int i = 0; i < 5; i++) {
            String message = "Hello World" + i;
            channel.basicPublish(EXCHANGE_NAME, "routingKey", null, message.getBytes());
            System.out.println("EmitLog Sent '" + message + "'");
        }
        channel.close();
        connection.close();

    }
}
