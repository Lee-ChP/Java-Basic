package messageQueue;

import com.rabbitmq.client.*;
import utils.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQFanoutConsumer {


    public static final String EXCHANGE_NAME = "FANOUT";

    public static void main(String[] args) throws IOException, TimeoutException {
        //消费者名称
        final String name = "Tester2";
        //判断服务是否启动
        RabbitMQUtil.checkServer();
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //工厂创建连接
        Connection connection = factory.newConnection();
        //连接创建通道
        Channel channel = connection.createChannel();
        //交换机声明: 名称 类型
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //获取一个临时队列
        String queueName = channel.queueDeclare().getQueue();
        //队列与交换机绑定 : 队列 交换机 routingkey 忽略
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(name + " 等待接收消息： ");
        //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(name + " 接收到消息 '" + message + "'");
            }
        };
        //自动回复队列应答 —— rabbitMQ的消息确认机制
        channel.basicConsume(queueName, true, consumer);

    }
}
