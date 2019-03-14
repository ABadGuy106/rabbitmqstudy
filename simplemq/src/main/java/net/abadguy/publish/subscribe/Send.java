package net.abadguy.publish.subscribe;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import net.abadguy.simplemq.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName:
 * @Description:
 * @author: liujiwei
 * @date: 2019/3/1320:15
 */
public class Send {

        //交换机名称
        private static String exchange="test_exchage";
        //分发类型
        private static String type="fanout";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机
       channel.exchangeDeclare(exchange, type);
       //发送消息
        String msg="hello exchage";

        channel.basicPublish(exchange,"",null,msg.getBytes());

        System.out.println("发送的消息："+msg);

        channel.close();
        connection.close();
    }
}
