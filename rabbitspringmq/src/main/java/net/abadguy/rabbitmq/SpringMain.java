package net.abadguy.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName:
 * @Description:
 * @author: liujiwei
 * @date: 2019/3/1421:54
 */
public class SpringMain {
    public static void main(String[] args) throws InterruptedException {
        AbstractApplicationContext ctx=new ClassPathXmlApplicationContext("classpath:spring.xml");

        //RabbitMQ模板
        RabbitTemplate template = ctx.getBean(RabbitTemplate.class);

        //发送消息
        template.convertAndSend("hello rabbit-spring");
        Thread.sleep(1000);
        ctx.destroy();

    }
}
