package cn.kfcfr.mq.rabbitmq.spring;

import cn.kfcfr.mq.rabbitmq.listener.ConsumerDeliveryListener;
import cn.kfcfr.mq.rabbitmq.message.AbstractMessage;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import java.nio.charset.Charset;

/**
 * Created by zhangqf77 on 2018/6/21.
 */
public class HandlerFactory {
    public static <T extends AbstractMessage> DefaultPublishHandler<T> createDefaultPublisher(ConnectionFactory connectionFactory, String exchangeName, Charset charset) {
        return new DefaultPublishHandler<>(connectionFactory, exchangeName, charset, null, null);
    }

    public static DefaultConsumeHandler createDefaultConsumer(ConnectionFactory connectionFactory, ConsumerDeliveryListener listener, String... queueNames) {
        return new DefaultConsumeHandler(connectionFactory, listener, queueNames);
    }
}
