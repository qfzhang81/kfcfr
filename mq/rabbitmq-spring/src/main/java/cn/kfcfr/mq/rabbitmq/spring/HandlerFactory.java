package cn.kfcfr.mq.rabbitmq.spring;

import cn.kfcfr.mq.rabbitmq.Publisher;
import cn.kfcfr.mq.rabbitmq.Receiver;
import cn.kfcfr.mq.rabbitmq.listener.ConfirmCallBackListener;
import cn.kfcfr.mq.rabbitmq.listener.ConsumerDeliveryListener;
import cn.kfcfr.mq.rabbitmq.listener.PublisherFailedListener;
import cn.kfcfr.mq.rabbitmq.listener.ReturnCallBackListener;
import cn.kfcfr.mq.rabbitmq.message.AbstractMessage;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import java.nio.charset.Charset;

/**
 * Created by zhangqf77 on 2018/6/21.
 */
public class HandlerFactory {
    public static <T extends AbstractMessage> Publisher<T> createConfirmedPublisher(ConnectionFactory connectionFactory, String exchangeName, Charset charset) {
        return new ConfirmedPublishHandler<>(connectionFactory, exchangeName, charset, null, null, null);
    }

    public static <T extends AbstractMessage> Publisher<T> createConfirmedPublisher(ConnectionFactory connectionFactory, String exchangeName, Charset charset, ConfirmCallBackListener confirmCallBackListener, ReturnCallBackListener returnCallBackListener, PublisherFailedListener failedListener) {
        return new ConfirmedPublishHandler<>(connectionFactory, exchangeName, charset, confirmCallBackListener, returnCallBackListener, failedListener);
    }

    public static Receiver createDefaultConsumer(ConnectionFactory connectionFactory, ConsumerDeliveryListener listener, String... queueNames) {
        return new DefaultConsumeHandler(connectionFactory, listener, queueNames);
    }
}
