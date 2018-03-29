package com.rabbitMq;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
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
    public final static String RPC_QUEUE_NAME="rpc_queue";

    /**
     * exchange type 是 direct
     *
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
            int result = 1 / 0;
            channel.txCommit();
        } catch (IOException e) {
            e.printStackTrace();
            channel.txRollback();
        }
        channel.close();
        connection.close();
    }

    /**
     * 一种比事务吞吐量大 product 和 broker之间的消息确认模式
     */
    @Test
    public void exchangeDirectConfirm(String message) throws IOException, TimeoutException, InterruptedException {
        //创建工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置相关信息
        factory.setHost("127.0.0.1");

        //创建一个新链接
        Connection connection = factory.newConnection();

        //创建一个新通道
        Channel channel = connection.createChannel();
        channel.confirmSelect();


        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
        String routingkey = "rabbitmq_routingke";
        channel.queueDeclare(QUEUE_NAME, true, false, false, null).getQueue();
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingkey);
//        channel.exchangeDeclare("我的天","direct");
//        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        if (message == null || message.equals("")) {
            message = "hello rabbitMq";
        }

         /*
                deliveryTag 消息id
                multiple 是否批量
                        如果是true，就意味着，小于等于deliveryTag的消息都处理成功了
                        如果是false，只是成功了deliveryTag这一条消息
         */
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("==========deliveryTag==========");
                System.out.println("deliveryTag:" + deliveryTag);
                System.out.println("multiple:" + multiple);
                if (multiple) {
                    System.out.println("处理批量:<=" + deliveryTag);
                } else {
                    System.out.println("处理单个:" + deliveryTag);
                }


            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("===========handleNack===========");
                System.out.println("deliveryTag: " + deliveryTag);
                System.out.println("multiple: " + multiple);
            }
        });


        channel.basicPublish(EXCHANGE_NAME, routingkey, null, message.getBytes("UTF-8"));


        System.out.println("Producter send '" + message + "'");

        if (channel.waitForConfirms()) {
            System.out.println("message is deal");
        }
        channel.close();
        connection.close();

    }


    public static void main(String[] args) throws InterruptedException, TimeoutException, IOException {
        ProductTest test = new ProductTest();
        test.exchangeDirectConfirm("one.");
        test.exchangeDirectConfirm("two..");
        test.exchangeDirectConfirm("tree...");
        test.exchangeDirectConfirm("four....");
    }

}
