package net.abadguy.workmq;

import com.rabbitmq.client.*;
import net.abadguy.simplemq.util.ConnectionUtil;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName:
 * @Description:
 * @author: liujiwei
 * @date: 2019/3/1221:53
 */
public class Recv2 {
    static String queue="test_work_queue";
    static boolean durable=false;
    static boolean exclusive=false;
    static boolean autoDelete=false;
    static Map<String, Object> arguments=null;

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        //队列声明
        channel.queueDeclare(queue,durable,exclusive,autoDelete,arguments);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"utf-8");
                System.out.println("[2]接收到的数据：" + message);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[2]  down");
                }
            }
        };
        //监听队列
        channel.basicConsume(queue,true,consumer);
    }
}
