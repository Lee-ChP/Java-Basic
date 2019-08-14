package messageQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import utils.RabbitMQUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;


/**
 * 广播模式: fanout —— 所有消费者都可以获得所有消息
 */
public class RabbitMQFanoutProducer {

    public final static  String EXCHANGE_NAME = "FANOUT";

    public static void main(String[] args) throws IOException, TimeoutException {
        //检查服务器是否开启
        RabbitMQUtil.checkServer();
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置工厂连接
        connectionFactory.setHost("localhost");
        //创建连接
        Connection connection = connectionFactory.newConnection();
        //为工厂创建通道
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        for (int i = 0; i < 100; i++) {
            String testMsg = "fanout message: " + i ;
            //发送消息到消息队列
            channel.basicPublish(EXCHANGE_NAME, "", null, testMsg.getBytes("UTF-8"));
            System.out.println("发送消息： " + testMsg);
        }

        //关闭通道
        channel.close();
        //关闭连接
        connection.close();
    }
}
