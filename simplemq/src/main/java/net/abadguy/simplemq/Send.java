package net.abadguy.simplemq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import net.abadguy.simplemq.util.ConnectionUtil;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName:
 * @Description:
 * @author: liujiwei
 * @date: 2019/3/1221:27
 */
public class Send {

    //队列名称
    static String queue="test_simple_queue";
    //是否持久化消息
    static boolean durable=false;
    static boolean exclusive=false;
    static boolean autoDelete=false;
    static Map<String, Object> arguments=null;

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //队列声明
        channel.queueDeclare(queue,durable,exclusive,autoDelete,arguments);

        String msg="hello simple";

        channel.basicPublish("",queue,null,msg.getBytes());

        System.out.println("发送的msg="+msg);
        channel.close();
        connection.close();
}
}
