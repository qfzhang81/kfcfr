import cn.kfcfr.mq.rabbitmq.MqFactory;
import cn.kfcfr.mq.rabbitmq.Publisher;
import cn.kfcfr.mq.rabbitmq.channel.ChannelFactory;
import cn.kfcfr.mq.rabbitmq.message.TopicMessage;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangqf77 on 2018/7/13.
 */
public class TestSender extends Thread {
    private final static String EXCHANGE_DELAY_WAIT = "test_topic_delay_wait";
    private final static String EXCHANGE_DELAY_REDIRECT = "test_topic_delay_redirect";
    private final static String EXCHANGE_NAME = "test_topic_hello";
    private final static String QUEUE_NAME_ALL = "test_topic_hello_world_all";
    private final static String QUEUE_NAME_369 = "test_topic_hello_world_369";

    public void run() {
        ConnectionFactory factory = MqFactory.createFactory("27.115.67.203", 40066, "ittestuser", "1qaz@WSX", "it");
        try {
            //创建交换机和队列
            MqFactory.createDirectExchange(factory, EXCHANGE_DELAY_WAIT);
            MqFactory.createDirectExchange(factory, EXCHANGE_DELAY_REDIRECT);
            MqFactory.createTopicExchange(factory, EXCHANGE_NAME);
            MqFactory.createQueue(factory, QUEUE_NAME_ALL);
            MqFactory.createQueueBind(factory, EXCHANGE_NAME, QUEUE_NAME_ALL, "hello.#");
            MqFactory.createQueue(factory, QUEUE_NAME_369, EXCHANGE_DELAY_WAIT, EXCHANGE_DELAY_REDIRECT, 300);
            MqFactory.createQueueBind(factory, EXCHANGE_NAME, QUEUE_NAME_369, "hello.#.3.#");
            MqFactory.createQueueBind(factory, EXCHANGE_NAME, QUEUE_NAME_369, "hello.#.6.#");
            MqFactory.createQueueBind(factory, EXCHANGE_NAME, QUEUE_NAME_369, "hello.#.9.#");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

//        Random randomIntNumber = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            Publisher<TopicMessage> channel = ChannelFactory.createPersistentPublisher(factory, EXCHANGE_NAME, Charset.forName("UTF-8"), new TestPublishConfirmListener(), new TestPublishReturnListener(), new TestPublishFailedListener());
            int num = 0;
            System.out.println("Test send single.");
            while (true) {
                num++;
                String routingKey = "hello." + num % 10;
                TopicMessage message = new TopicMessage(MessageFormat.format("Single, Hello World! Thread ID: {0}, Num: {1}", this.currentThread().getId(), num), routingKey);
                channel.publishSingle(message);
                System.out.println(MessageFormat.format("{0}[{3}] Sent seq = {1} with routing key = {2}", sdf.format(new Date()), num, routingKey, this.getId()));
                if (num >= 100) break;
                try {
                    if (num % 30 == 0) {
                        Thread.sleep(2000);
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Thread.sleep(10000);
            System.out.println("Test send batch.");
            num = 0;
            List<TopicMessage> list = new ArrayList<>();
            while (true) {
                num++;
                String routingKey = "hello." + num % 10;
                TopicMessage message = new TopicMessage(MessageFormat.format("Batch, Hello World! Thread ID: {0}, Num: {1}", this.currentThread().getId(), num), routingKey);
                list.add(message);
                if (num >= 200) break;
            }
            channel.publishBatch(list);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
