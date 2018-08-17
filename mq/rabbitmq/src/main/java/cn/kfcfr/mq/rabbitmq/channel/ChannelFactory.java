package cn.kfcfr.mq.rabbitmq.channel;

import cn.kfcfr.mq.rabbitmq.Publisher;
import cn.kfcfr.mq.rabbitmq.Receiver;
import cn.kfcfr.mq.rabbitmq.listener.ConfirmCallBackListener;
import cn.kfcfr.mq.rabbitmq.listener.ConsumerDeliveryListener;
import cn.kfcfr.mq.rabbitmq.listener.PublisherFailedListener;
import cn.kfcfr.mq.rabbitmq.listener.ReturnCallBackListener;
import cn.kfcfr.mq.rabbitmq.message.AbstractMessage;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.Charset;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
public class ChannelFactory {
    public static <T extends AbstractMessage> Publisher<T> createPersistentPublisher(ConnectionFactory connectionFactory, String exchangeName, Charset charset) {
        return new PersistentPublisherChannel<>(connectionFactory, exchangeName, charset);
    }

    public static <T extends AbstractMessage> Publisher<T> createPersistentPublisher(ConnectionFactory connectionFactory, String exchangeName, Charset charset, ConfirmCallBackListener confirmCallBackListener, ReturnCallBackListener returnCallBackListener, PublisherFailedListener failedListener) {
        return new PersistentPublisherChannel<>(connectionFactory, exchangeName, charset, confirmCallBackListener, returnCallBackListener, failedListener);
    }

    public static Receiver createDefaultConsumer(ConnectionFactory connectionFactory, Charset charset, ConsumerDeliveryListener listener, String... queueNames) {
        return new DefaultConsumerChannel(connectionFactory, charset, listener, queueNames);
    }
}
