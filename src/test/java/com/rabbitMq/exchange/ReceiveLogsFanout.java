package com.rabbitMq.exchange;

import com.rabbitmq.client.*;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveLogsFanout {
    private static final String EXCHANGE_NAME = "logs";
    private final static String QUEUE_NAME = "fanout_queue";

    public static void main(String[] args) throws IOException, TimeoutException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);
//        String queue = channel.queueDeclare().getQueue();
//        //产生一个随机的队列名称
//        channel.queueBind(queue, EXCHANGE_NAME, "");//对队列进行绑定

        System.out.println("ReceiveLogs1 Waiting for messages");

        //接收方式1
//        QueueingConsumer consumer = new QueueingConsumer(channel);
//        channel.basicConsume("queueName", true, consumer);//队列会自动删除
//        while (true) {
//            // Delivery : 封装了消息,消息的载体
//            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
//            String recieveMsg = new String(delivery.getBody());
//            System.out.println("接收到了消息: " + recieveMsg);
//        }

        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("接收到了消息" + message + ":" + envelope.getRoutingKey());

            }
        };
        channel.basicConsume("queueName1", true, consumer);
    }
}