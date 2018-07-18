import cn.kfcfr.mq.rabbitmq.RabbitMqFactory;
import cn.kfcfr.mq.rabbitmq.channel.DefaultConsumerChannel;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by zhangqf77 on 2018/7/13.
 */
public class TestReceiverAll extends Thread {
    private final static String QUEUE_NAME_ALL = "test_topic_hello_world_all";

    public void run() {
        ConnectionFactory factory = RabbitMqFactory.createFactory("27.115.67.203", 40066, "ittestuser", "1qaz@WSX", "it");
        try {
            DefaultConsumerChannel channel = new DefaultConsumerChannel(factory, null, new TestConsumeHelloListener(QUEUE_NAME_ALL), QUEUE_NAME_ALL);
            channel.start();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
