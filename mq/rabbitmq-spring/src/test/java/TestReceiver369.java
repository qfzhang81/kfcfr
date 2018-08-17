import cn.kfcfr.mq.rabbitmq.spring.DefaultConsumeHandler;
import cn.kfcfr.mq.rabbitmq.spring.MqSpringFactory;

/**
 * Created by zhangqf77 on 2018/7/13.
 */
public class TestReceiver369 extends Thread {
    private final static String QUEUE_NAME_369 = "test_topic_hello_world_369";

    public void run() {
        org.springframework.amqp.rabbit.connection.ConnectionFactory factory = MqSpringFactory.createFactory("27.115.67.203", 40066, "ittestuser", "1qaz@WSX", "it");
        try {
            DefaultConsumeHandler handler = new DefaultConsumeHandler(factory, new TestConsumeHelloListener(QUEUE_NAME_369), QUEUE_NAME_369);
            handler.start();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}