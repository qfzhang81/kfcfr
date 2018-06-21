package cn.kfcfr.mq.rabbitmq.channel;

import cn.kfcfr.mq.rabbitmq.listener.ConsumerDeliveryListener;
import cn.kfcfr.mq.rabbitmq.message.AbstractMessage;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.Charset;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
public class ChannelFactory {
    public static <T extends AbstractMessage> PersistentPublisherChannel<T> createPersistentPublisher(ConnectionFactory connectionFactory, String exchangeName, Charset charset) {
        return new PersistentPublisherChannel<>(connectionFactory, exchangeName, charset);
    }

    public static DefaultConsumerChannel createDefaultConsumer(ConnectionFactory connectionFactory, ConsumerDeliveryListener listener, String... queueNames) {
        return new DefaultConsumerChannel(connectionFactory, listener, queueNames);
    }
}
