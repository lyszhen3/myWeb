package com.rabbitMq.exchange;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveLogsDirect1 {
    // 交换器名称
    private static final String EXCHANGE_NAME = "direct_logs";
    // 路由关键字
    private static final String[] routingKeys = new String[]{"info","error", "warning"};

    private final static String QUEUE_NAME = "direct_queue";

    public static void main(String[] args) throws IOException, TimeoutException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct",true);
        //获取匿名队列名称
//        String queueName = channel.queueDeclare().getQueue();

        //根据路由关键字进行绑定
//        for (String routingKey : routingKeys) {
//            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingKey);
//            System.out.println("ReceiveLogsDirect1 exchange:" + EXCHANGE_NAME + "," +
//                    " queue:" + QUEUE_NAME + ", BindRoutingKey:" + routingKey);
//        }
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"info");
        System.out.println("ReceiveLogsDirect1  Waiting for messages");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("ReceiveLogsDirect1 Received '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}