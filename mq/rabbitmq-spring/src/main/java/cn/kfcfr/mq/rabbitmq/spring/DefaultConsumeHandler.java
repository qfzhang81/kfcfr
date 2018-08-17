package cn.kfcfr.mq.rabbitmq.spring;

import cn.kfcfr.mq.rabbitmq.Receiver;
import cn.kfcfr.mq.rabbitmq.helper.RabbitMessageHelper;
import cn.kfcfr.mq.rabbitmq.listener.ConsumerDeliveryData;
import cn.kfcfr.mq.rabbitmq.listener.ConsumerDeliveryListener;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import java.nio.charset.Charset;
import java.text.MessageFormat;

/**
 * Created by zhangqf77 on 2018/6/20.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class DefaultConsumeHandler extends AbstractMqHandler implements Receiver {
    protected String[] queueNames;
    protected boolean exposeListenerChannel;
    protected int maxConcurrentConsumers;
    protected int concurrentConsumers;
    protected AcknowledgeMode acknowledgeMode;
    protected ConsumerDeliveryListener listener;
    protected Charset charset;

    protected SimpleMessageListenerContainer container;

    public DefaultConsumeHandler(ConnectionFactory factory, Charset charset, ConsumerDeliveryListener listener, String... queueNames) {
        this.connectionFactory = factory;
        this.listener = listener;
        this.queueNames = queueNames;
        if (charset == null) {
            this.charset = DEFAULT_CHARSET;
        }
        else {
            this.charset = charset;
        }
        init();
        createConsumer();
    }

    protected void init() {
        exposeListenerChannel = true;
        maxConcurrentConsumers = 1;
        concurrentConsumers = 1;
        acknowledgeMode = AcknowledgeMode.MANUAL; //设置确认模式手工确认
    }

    protected void createConsumer() {
        container = new SimpleMessageListenerContainer(connectionFactory);
        container.addQueueNames(queueNames);
        container.setExposeListenerChannel(exposeListenerChannel);
        container.setMaxConcurrentConsumers(maxConcurrentConsumers);
        container.setConcurrentConsumers(concurrentConsumers);
        container.setAcknowledgeMode(acknowledgeMode);
        container.setAutoStartup(false);
        final ChannelAwareMessageListener consumer = new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                String body = RabbitMessageHelper.convertBodyToString(message.getBody(), charset);
                logger.debug(MessageFormat.format("Begin consume ''{0}''.", body));
                boolean rst;
                try {
                    MessageProperties messageProperties = message.getMessageProperties();
                    rst = listener.handle(new ConsumerDeliveryData(messageProperties.getConsumerTag(), messageProperties.getCorrelationIdString(), body, messageProperties.getDeliveryTag(), messageProperties.getReceivedRoutingKey()));
                    logger.debug(MessageFormat.format("Return '{1}' when handling '{0}'.", body, rst));
                }
                catch (Exception ex) {
                    rst = false;
                    logger.error(MessageFormat.format("An exception occurred when handling '{0}'.", body), ex);
                }
                if (rst) {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    logger.debug(MessageFormat.format("Confirm ok when consume '{0}'.", body));
                }
                else {
                    final boolean reQueue = false;
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), reQueue);
                    logger.debug(MessageFormat.format("Reject and re queue={1} when consume '{0}'.", body, reQueue));
                }
            }
        };
        container.setMessageListener(consumer);
    }

    public void start() {
        container.start();
    }

    public void stop() {
        container.stop();
    }

}
