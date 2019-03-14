package net.abadguy.routing;

import com.rabbitmq.client.*;
import net.abadguy.simplemq.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName:
 * @Description:
 * @author: liujiwei
 * @date: 2019/3/1320:56
 */
public class Recv2 {

    private static final String QUEUE_NAME="test_driect_queue_02";
    private static final String EXCHAGE_NAME="testexchage_direct";
    private static final String routingKey="error";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        channel.basicQos(1);

        channel.queueBind(QUEUE_NAME,EXCHAGE_NAME,"error");
        channel.queueBind(QUEUE_NAME,EXCHAGE_NAME,"info");
        channel.queueBind(QUEUE_NAME,EXCHAGE_NAME,"warning");

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
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        //监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);

    }
}
