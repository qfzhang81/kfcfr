package cn.kfcfr.mq.rabbitmq.channel;

import cn.kfcfr.core.convert.JsonConvert;
import cn.kfcfr.mq.rabbitmq.message.AbstractMessage;
import cn.kfcfr.mq.rabbitmq.message.BatchMessage;
import cn.kfcfr.mq.rabbitmq.message.SingleMessage;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.Charset;
import java.text.MessageFormat;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
public abstract class AbstractPublisherChannel<T extends AbstractMessage> extends AbstractChannel {
    protected final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    protected boolean confirms;
    protected boolean returns;
    protected long waitForConfirmMillisecond;
    protected boolean mandatory;
    protected boolean immediate;
    protected boolean durable;
    protected AMQP.BasicProperties basicProperties;

    protected String exchangeName;
    protected ConnectionFactory factory;
    protected Connection connection;

    public AbstractPublisherChannel(ConnectionFactory factory, String exchangeName) {
        this(factory, exchangeName, true, true, 10000, true, false);
    }

    public AbstractPublisherChannel(ConnectionFactory factory, String exchangeName, boolean confirms, boolean returns, long waitForConfirmMillisecond, boolean mandatory, boolean immediate) {
        this.factory = factory;
        this.exchangeName = exchangeName;
        this.waitForConfirmMillisecond = waitForConfirmMillisecond;
        this.confirms = confirms;
        this.returns = returns;
        this.mandatory = mandatory;
        this.immediate = immediate;
        init();
    }

    protected abstract void init();

    public boolean publishSingle(SingleMessage<T> singleMessage) {
        Channel channel = null;
        boolean rst = false;
        try {
            channel = getChannel();
            AbstractMessage message = singleMessage.getMessage();
            byte[] bodyByte;
            if (message.getCharset() != null) {
                bodyByte = message.getBody().getBytes(message.getCharset());
            }
            else {
                bodyByte = message.getBody().getBytes(DEFAULT_CHARSET);
            }
            //Delivery mode: non-persistent (1) or persistent (2)
            String json = JsonConvert.toJsonString(message);
            logger.debug(MessageFormat.format("Ready to push '{0}'", json));
            if (confirms) {
                channel.confirmSelect();
            }
            channel.basicPublish(exchangeName, message.getRoutingKey(), mandatory, immediate, basicProperties, bodyByte);
            if (confirms) {
                rst = channel.waitForConfirms(waitForConfirmMillisecond);
                logger.debug(MessageFormat.format("Return '{1}' for confirm when push '{0}'.", json, rst));
            }
            else {
                rst = true;
                logger.debug(MessageFormat.format("No need to wait for confirm when push '{0}'.", json));
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        finally {
            closeChannel(channel);
            channel = null;
        }
        return rst;
    }

    public boolean publishBatch(BatchMessage<T> batchMessage) {
        return false;
    }

}