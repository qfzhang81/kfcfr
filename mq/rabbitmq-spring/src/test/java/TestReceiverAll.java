import cn.kfcfr.mq.rabbitmq.spring.DefaultConsumeHandler;
import cn.kfcfr.mq.rabbitmq.spring.MqSpringFactory;

/**
 * Created by zhangqf77 on 2018/7/13.
 */
public class TestReceiverAll extends Thread {
    private final static String QUEUE_NAME_ALL = "test_topic_hello_world_all";

    public void run() {
        org.springframework.amqp.rabbit.connection.ConnectionFactory factory = MqSpringFactory.createFactory("27.115.67.203", 40066, "ittestuser", "1qaz@WSX", "it");
        try {
            DefaultConsumeHandler handler = new DefaultConsumeHandler(factory, new TestConsumeHelloListener(QUEUE_NAME_ALL), QUEUE_NAME_ALL);
            handler.start();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
