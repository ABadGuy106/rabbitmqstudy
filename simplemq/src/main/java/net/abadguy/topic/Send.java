package net.abadguy.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import net.abadguy.simplemq.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName:
 * @Description:
 * @author: liujiwei
 * @date: 2019/3/1420:39
 */
public class Send {

    private static final String EXCHAGE_NAME ="test_exchage_topic";
    private static final String EXCHAGE_YTPE = "topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();
        
        channel.exchangeDeclare(EXCHAGE_NAME,EXCHAGE_YTPE);

        String msg="商品。。。。。。。。。。。。。。";

        channel.basicPublish(EXCHAGE_NAME,"goods.add",null,msg.getBytes());
        System.out.println("-------send--------"+msg);

        channel.close();
        connection.close();
    }
}
