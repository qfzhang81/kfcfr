package cn.kfcfr.mq.rabbitmq.spring;

import cn.kfcfr.mq.rabbitmq.listener.ConsumerDeliveryListener;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

/**
 * Created by zhangqf77 on 2018/6/20.
 */
public class DefaultConsumeHandler extends AbstractMqHandler {
    protected String[] queueNames;
    protected boolean exposeListenerChannel;
    protected int maxConcurrentConsumers;
    protected int concurrentConsumers;
    protected AcknowledgeMode acknowledgeMode;
    protected ConsumerDeliveryListener listener;

    protected SimpleMessageListenerContainer container;

    public DefaultConsumeHandler(ConnectionFactory factory, ConsumerDeliveryListener listener, String... queueNames) {
        this.connectionFactory = factory;
        this.listener = listener;
        this.queueNames = queueNames;
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
        container.setMessageListener(new DefaultMessageListener(listener, DEFAULT_CHARSET));
    }

    public void start() {
        container.start();
    }

    public void stop() {
        container.stop();
    }

}
