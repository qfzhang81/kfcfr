package cn.kfcfr.mq.rabbitmq.channel;

import cn.kfcfr.mq.rabbitmq.listener.ConsumerDeliveryData;
import cn.kfcfr.mq.rabbitmq.listener.ConsumerDeliveryListener;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.MessageFormat;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
public class DefaultConsumerChannel extends AbstractChannel {
    protected final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    protected boolean autoAck;
    protected ConsumerDeliveryListener listener;
    protected Charset charset;

    protected String queueName;
    protected Channel channel;

    public DefaultConsumerChannel(ConnectionFactory factory, String queueName, ConsumerDeliveryListener listener) {
        this.factory = factory;
        this.queueName = queueName;
        this.listener = listener;
        init();
    }

    protected void init() {
        this.autoAck = false;
    }

    public void start() {
        try {
            channel = getChannel();
            channel.basicQos(1);
            charset = listener.getCharset();
            if (charset == null) {
                charset = DEFAULT_CHARSET;
            }
            // 创建队列消费者
            final Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, charset);
                    logger.debug(MessageFormat.format("Begin consume '{0}'.", message));
                    boolean rst;
                    try {
                        rst = listener.handle(new ConsumerDeliveryData(consumerTag, properties.getCorrelationId(), message, envelope.getDeliveryTag(), envelope.getRoutingKey()));
                        logger.debug(MessageFormat.format("Return '{1}' when consume '{0}'.", message, rst));
                    }
                    catch (Exception ex) {
                        rst = false;
                        logger.error(MessageFormat.format("An exception occurred when consume '{0}'.", message), ex);
                    }
                    if (autoAck) {
                        logger.debug(MessageFormat.format("Auto ack when consume '{0}'.", message));
                    }
                    else {
                        if (rst) {
                            channel.basicAck(envelope.getDeliveryTag(), false);
                            logger.debug(MessageFormat.format("Confirm ok when consume '{0}'.", message));
                        }
                        else {
                            final boolean reQueue = false;
                            channel.basicReject(envelope.getDeliveryTag(), reQueue);
                            logger.debug(MessageFormat.format("Reject and re queue={1} when consume '{0}'.", message, reQueue));
                        }
                    }
                }
            };
            channel.basicConsume(queueName, autoAck, consumer);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void stop() {
        closeChannel(channel);
    }

}
