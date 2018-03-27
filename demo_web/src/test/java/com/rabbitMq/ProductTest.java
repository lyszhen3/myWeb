package com.rabbitMq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

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
    public final static String EXCHANGE_NAME = "rabbitmq_exchange";

    /**
     *exchange type 是 direct
     * @throws IOException
     * @throws TimeoutException
     */
    @Test
    public void exchangeDirect() throws IOException, TimeoutException {
        //创建工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置相关信息
        factory.setHost("127.0.0.1");

        //创建一个新链接
        Connection connection = factory.newConnection();

        //创建一个新通道
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
        String routingkey = "rabbitmq_routingke";
        channel.queueDeclare(QUEUE_NAME, true, false, false, null).getQueue();
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingkey);
//        channel.exchangeDeclare("我的天","direct");
//        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String message = "hello rabbitMq";
        try {

            channel.txSelect();
            channel.basicPublish(EXCHANGE_NAME, routingkey, null, message.getBytes("UTF-8"));
            System.out.println("Producter send '" + message + "'");
            int result=1/0;
            channel.txCommit();
        } catch (IOException e) {
            e.printStackTrace();
            channel.txRollback();
        }
        channel.close();
        connection.close();
    }

    /**
     *一种比事务吞吐量大的模式
     */
    @Test
    public void exchangeDirectConfirm(){

    }



}
