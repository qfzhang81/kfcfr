package cn.kfcfr.mq.rabbitmq.channel;

import cn.kfcfr.mq.rabbitmq.message.AbstractMessage;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
public class PersistentPublisherChannel<T extends AbstractMessage> extends AbstractPublisherChannel<T> {

    public PersistentPublisherChannel(ConnectionFactory factory, String exchangeName) {
        super(factory, exchangeName);
    }

    public PersistentPublisherChannel(ConnectionFactory factory, String exchangeName, boolean confirms, boolean returns, long waitForConfirmMillisecond, boolean mandatory, boolean immediate) {
        super(factory, exchangeName, confirms, returns, waitForConfirmMillisecond, mandatory, immediate);
    }

    @Override
    protected void init() {
        this.durable = true;
        this.basicProperties = MessageProperties.PERSISTENT_TEXT_PLAIN;
    }
}