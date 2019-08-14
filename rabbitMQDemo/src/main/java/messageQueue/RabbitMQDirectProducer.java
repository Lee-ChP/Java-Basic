package messageQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import utils.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *  消息模式 ： direct ： 分食模式 ： 消费者瓜分消息 —— 副作用：消费者想要的消息不一定拿得到，消费者拿到的消息不一定是自己想要的
 */
public class RabbitMQDirectProducer {
    public static final String QUEUE_NAME = "direct_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        RabbitMQUtil.checkServer();

        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        for (int i = 0; i < 100; i++) {
            String directMsg = "direact 消息 " + i;
            channel.basicPublish("",QUEUE_NAME, null, directMsg.getBytes("utf-8"));
            System.out.println("发送消息： " + directMsg);
        }

        channel.close();
        connection.close();
    }
}
