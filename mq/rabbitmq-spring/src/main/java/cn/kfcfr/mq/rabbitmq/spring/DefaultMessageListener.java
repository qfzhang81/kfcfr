package cn.kfcfr.mq.rabbitmq.spring;

import cn.kfcfr.mq.rabbitmq.helper.RabbitMessageHelper;
import cn.kfcfr.mq.rabbitmq.listener.ConsumerDeliveryData;
import cn.kfcfr.mq.rabbitmq.listener.ConsumerDeliveryListener;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import java.nio.charset.Charset;
import java.text.MessageFormat;

/**
 * Created by zhangqf77 on 2018/6/21.
 */
public class DefaultMessageListener implements ChannelAwareMessageListener {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ConsumerDeliveryListener listener;
    private Charset defaultCharset;

    public DefaultMessageListener(ConsumerDeliveryListener listener, Charset defaultCharset) {
        this.listener = listener;
        this.defaultCharset = defaultCharset;
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String body = RabbitMessageHelper.convertBodyToString(message.getBody(), defaultCharset);
        logger.debug(MessageFormat.format("Begin consume '{0}'.", body));
        boolean rst;
        try {
            MessageProperties messageProperties = message.getMessageProperties();
            rst = listener.handle(new ConsumerDeliveryData(messageProperties.getConsumerTag(), messageProperties.getCorrelationId(), body, messageProperties.getDeliveryTag(), messageProperties.getReceivedRoutingKey()));
            logger.debug(MessageFormat.format("Return '{1}' when consume '{0}'.", message, rst));
        }
        catch (Exception ex) {
            rst = false;
            logger.error(MessageFormat.format("An exception occurred when consume '{0}'.", message), ex);
        }
        if (rst) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            logger.debug(MessageFormat.format("Confirm ok when consume '{0}'.", message));
        }
        else {
            final boolean reQueue = false;
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), reQueue);
            logger.debug(MessageFormat.format("Reject and re queue={1} when consume '{0}'.", message, reQueue));
        }
    }

    public ConsumerDeliveryListener getListener() {
        return listener;
    }

    public Charset getDefaultCharset() {
        return defaultCharset;
    }
}
