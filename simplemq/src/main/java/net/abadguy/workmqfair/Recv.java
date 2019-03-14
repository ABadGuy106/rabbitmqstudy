package net.abadguy.workmqfair;

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
public class Recv {
    static String queue="test_work_queue";
    static boolean durable=false;
    static boolean exclusive=false;
    static boolean autoDelete=false;
    static Map<String, Object> arguments=null;

    //自动应答
    static boolean autoAck=false;

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        final Channel channel = connection.createChannel();

        //队列声明
        channel.queueDeclare(queue,durable,exclusive,autoDelete,arguments);

        //每个消费者发送确认消息之前，消息队列不发生下一消息到消费者，一次只处理一个消息
        //限制发送给同一个消费者不得超过一条数据
        int prefetchCount=1;
        channel.basicQos(prefetchCount);


        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"utf-8");
                System.out.println("[1]接收到的数据：" + message);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[1]  down");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        //监听队列
        channel.basicConsume(queue,autoAck,consumer);
    }
}
