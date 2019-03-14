package net.abadguy.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import net.abadguy.simplemq.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName:
 * @Description:
 * @author: liujiwei
 * @date: 2019/3/1320:50
 */
public class Send {

    private static final String EXCHAGE_NAME="testexchage_direct";
    private static final String EXCHAGE_TYPE="direct";
    private static final String routingKey="error";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //exchage
        channel.exchangeDeclare(EXCHAGE_NAME,EXCHAGE_TYPE);

        String msg="test exchage direct";

        channel.basicPublish(EXCHAGE_NAME,routingKey,null,msg.getBytes());

        channel.close();
        connection.close();
    }
}
