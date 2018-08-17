import cn.kfcfr.mq.rabbitmq.Publisher;
import cn.kfcfr.mq.rabbitmq.message.TopicMessage;
import cn.kfcfr.mq.rabbitmq.spring.HandlerFactory;
import cn.kfcfr.mq.rabbitmq.spring.MqSpringFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangqf77 on 2018/7/13.
 */
public class TestSender extends Thread {
    private final static String EXCHANGE_NAME = "test_topic_hello";
    private final static String QUEUE_NAME_ALL = "test_topic_hello_world_all";
    private final static String QUEUE_NAME_369 = "test_topic_hello_world_369";

    public void run() {
        ConnectionFactory factory = MqSpringFactory.createFactory("27.115.67.203", 40066, "ittestuser", "1qaz@WSX", "it");
//        try {
//            //创建交换机和队列
//            RabbitAdmin admin = new RabbitAdmin(factory);
//            MqSpringFactory.createTopicExchange(admin, EXCHANGE_NAME);
//            MqSpringFactory.createQueue(admin, QUEUE_NAME_ALL);
//            MqSpringFactory.createQueueBind(admin, EXCHANGE_NAME, QUEUE_NAME_ALL, "hello.#");
//            MqSpringFactory.createQueue(admin, QUEUE_NAME_369);
//            MqSpringFactory.createQueueBind(admin, EXCHANGE_NAME, QUEUE_NAME_369, "hello.#.3.#");
//            MqSpringFactory.createQueueBind(admin, EXCHANGE_NAME, QUEUE_NAME_369, "hello.#.6.#");
//            MqSpringFactory.createQueueBind(admin, EXCHANGE_NAME, QUEUE_NAME_369, "hello.#.9.#");
//        }
//        catch (Exception e) {
//            System.out.println(e.getMessage());
//            return;
//        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            Publisher<TopicMessage> handler = HandlerFactory.createConfirmedPublisher(factory, EXCHANGE_NAME, Charset.forName("UTF-8"), new TestPublishConfirmListener(), new TestPublishReturnListener(), new TestPublishFailedListener());
            int num = 0;
            System.out.println("Test send single.");
            while (true) {
                num++;
                String routingKey = "hello." + num % 10;
                TopicMessage message = new TopicMessage(MessageFormat.format("Single, Hello World! Num: {0}", num), routingKey);
                handler.publishSingle(message);
                System.out.println(MessageFormat.format("{0}[{3}] Sent seq = {1} with routing key = {2}", sdf.format(new Date()), num, routingKey, this.getId()));
                if (num >= 3) break;
                try {
                    if (num % 30 == 0) {
                        Thread.sleep(2000);
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//            Thread.sleep(10000);
//            System.out.println("Test send batch.");
//            num = 0;
//            List<TopicMessage> list = new ArrayList<>();
//            while (true) {
//                num++;
//                String routingKey = "hello." + num % 10;
//                TopicMessage message = new TopicMessage(MessageFormat.format("Batch, Hello World! Num: {1}", num), routingKey);
//                list.add(message);
//                if (num >= 200) break;
//            }
//            handler.publishBatch(list);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
