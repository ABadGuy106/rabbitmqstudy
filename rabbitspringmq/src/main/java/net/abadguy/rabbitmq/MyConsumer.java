package net.abadguy.rabbitmq;

/**
 * @ClassName:
 * @Description:
 * @author: liujiwei
 * @date: 2019/3/1421:50
 */
public class MyConsumer {

    //具体执行业务的方法
    public void listen(String foo){
        System.out.println("消费者 ："+foo);
    }
}
