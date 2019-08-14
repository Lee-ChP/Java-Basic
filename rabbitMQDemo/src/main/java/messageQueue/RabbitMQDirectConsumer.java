package messageQueue;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class RabbitMQDirectConsumer {
    public static final String QUEUE_NAME = "direct_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        final String name = "direct consumer tester2-";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //关注的队列
        channel.queueDeclare(QUEUE_NAME, false, false, true, null);
        System.out.println(name + " 正在等待消息");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(name + " 接收到消息 '" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);


    }
}
