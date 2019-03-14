package net.abadguy.topic;

import com.rabbitmq.client.*;
import net.abadguy.simplemq.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName:
 * @Description:
 * @author: liujiwei
 * @date: 2019/3/1420:46
 */
public class Recv2 {

    private static final String QUEUE_NAEM = "topic_test02";
    private static final String EXCHAGE_NAME = "test_exchage_topic";
    private static final String routingKey="goods.#";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAEM,false,false,false,null);

        channel.queueBind(QUEUE_NAEM,EXCHAGE_NAME,routingKey);

        channel.basicQos(1);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg=new String(body,"utf-8");
                System.out.println("Recv2 收到的信息："+msg+routingKey);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[2] done");
                }
            }
        };

        channel.basicConsume(QUEUE_NAEM,false,consumer);

    }
}
