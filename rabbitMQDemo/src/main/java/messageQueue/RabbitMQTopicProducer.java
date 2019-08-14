package messageQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * topic ： 订阅模式
 */
public class RabbitMQTopicProducer {
    public static final String EXCHANGE_NAME= "topics_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        String[] routeKeys = new String[] {"usa.news", "usa.weather", "europe.news", "europe.weather"};

        String[] topicMsgs = new String[] {"美利坚新闻", "美利坚天气", "圣母国新闻", "圣母国天气"};

        for (int i = 0 ; i < routeKeys.length; i++) {
            String routeKey = routeKeys[i];
            String topicMsg = topicMsgs[i];

            channel.basicPublish(EXCHANGE_NAME, routeKey,null, topicMsg.getBytes("utf-8"));
            System.out.printf("发送消息到路由：%s, 内容是: %s%n ", routeKey,topicMsg);
        }
        channel.close();
        connection.close();
    }
}
