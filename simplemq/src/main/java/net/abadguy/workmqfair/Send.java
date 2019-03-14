package net.abadguy.workmqfair;

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
 * @date: 2019/3/1222:08
 */
public class Send {

    static String queue="test_work_queue";
    static boolean durable=false;
    static boolean exclusive=false;
    static boolean autoDelete=false;
    static Map<String, Object> arguments=null;

    //自动应答
    static boolean autoAck=false;

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtil.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //队列声明
        channel.queueDeclare(queue,durable,exclusive,autoDelete,arguments);

        //每个消费者发送确认消息之前，消息队列不发生下一消息到消费者，一次只处理一个消息
        //限制发送给同一个消费者不得超过一条数据
        int prefetchCount=1;
        channel.basicQos(prefetchCount);


       for(int i=0;i<50;i++){
           String msg="hello "+i;
           channel.basicPublish("",queue,null,msg.getBytes());
           Thread.sleep(i*20);
       }
        channel.close();
        connection.close();
        System.out.println("发送完成");
    }
}
