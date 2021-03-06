import cn.kfcfr.mq.rabbitmq.MqFactory;
import cn.kfcfr.mq.rabbitmq.Receiver;
import cn.kfcfr.mq.rabbitmq.channel.DefaultConsumerChannel;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by zhangqf77 on 2018/7/13.
 */
public class TestReceiver369 extends Thread {
    private final static String QUEUE_NAME_369 = "test_topic_hello_world_369";

    public void run() {
        ConnectionFactory factory = MqFactory.createFactory("27.115.67.203", 40066, "ittestuser", "1qaz@WSX", "it");
        try {
            Receiver channel = new DefaultConsumerChannel(factory, null, new TestConsumeHelloListener(QUEUE_NAME_369), QUEUE_NAME_369);
            channel.start();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
