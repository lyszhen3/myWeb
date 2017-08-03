package com.rabbitMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by pc on 2017-08-01.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class ProductTest {

    public final static String QUEUE_NAME = "rabbitMQ.test";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置相关信息
        factory.setHost("127.0.0.1");

        //创建一个新链接
        Connection connection = factory.newConnection();

        //创建一个新通道
        Channel channel = connection.createChannel();

        //声明一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String message = "hello rabbitMq";

        channel.basicPublish("",QUEUE_NAME,null,message.getBytes("UTF-8"));
        System.out.println("Producter send '"+message+"'");
        channel.close();
        connection.close();

    }

}
