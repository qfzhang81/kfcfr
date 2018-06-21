package cn.kfcfr.mq.rabbitmq.channel;

import cn.kfcfr.mq.rabbitmq.listener.ConsumerDeliveryListener;
import cn.kfcfr.mq.rabbitmq.message.AbstractMessage;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
public class ChannelFactory {
    public static <T extends AbstractMessage> PersistentPublisherChannel<T> createPersistentPublisher(ConnectionFactory connectionFactory, String exchangeName) {
        return new PersistentPublisherChannel<>(connectionFactory, exchangeName);
    }

    public static DefaultConsumerChannel createDefaultConsumer(ConnectionFactory connectionFactory, String queueName, ConsumerDeliveryListener listener) {
        return new DefaultConsumerChannel(connectionFactory, queueName, listener);
    }
}
