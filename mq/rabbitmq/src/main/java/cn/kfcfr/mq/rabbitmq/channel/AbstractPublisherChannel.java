package cn.kfcfr.mq.rabbitmq.channel;

import cn.kfcfr.core.convert.JsonConvert;
import cn.kfcfr.mq.rabbitmq.Publisher;
import cn.kfcfr.mq.rabbitmq.helper.RabbitMessageHelper;
import cn.kfcfr.mq.rabbitmq.listener.*;
import cn.kfcfr.mq.rabbitmq.message.AbstractMessage;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused", "all"})
public abstract class AbstractPublisherChannel<T extends AbstractMessage> extends AbstractChannel implements Publisher<T> {
    protected long waitForConfirmMillisecond;//确认时默认超时时间，毫秒
    protected boolean mandatory;
    protected boolean immediate;
    //    protected boolean durable;
    protected AMQP.BasicProperties basicProperties;

    protected String exchangeName;
    protected Charset charset;
    protected ConfirmCallBackListener confirmCallBackListener;
    protected ReturnCallBackListener returnCallBackListener;
    protected PublisherFailedListener failedListener;

    public AbstractPublisherChannel(ConnectionFactory factory, String exchangeName, Charset charset, ConfirmCallBackListener confirmCallBackListener, ReturnCallBackListener returnCallBackListener, PublisherFailedListener failedListener) {
        this.factory = factory;
        this.exchangeName = exchangeName;
        if (charset == null) {
            this.charset = DEFAULT_CHARSET;
        }
        else {
            this.charset = charset;
        }
        this.confirmCallBackListener = confirmCallBackListener;
        this.returnCallBackListener = returnCallBackListener;
        this.failedListener = failedListener;
        init();
    }

    protected void init() {
        this.mandatory = true;
        this.immediate = false;
        this.waitForConfirmMillisecond = 10000;
    }

    public boolean publishSingle(T message) {
        return publishSingle(message, waitForConfirmMillisecond);
    }

    public boolean publishSingle(T message, long confirmTimeoutMillisecond) {
        if (message == null) {
            throw new NullPointerException();
        }
        boolean rst = false;
        Channel channel = null;
        boolean isPublished = false;
        final SortedMap<Long, T> unconfirmedMap = new TreeMap<>();//已发送未确认列表
        try {
            channel = getChannel();
            channel.confirmSelect();
            addReturnListener(channel);
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    logger.debug(MessageFormat.format("Server return ack, deliveryTag is ''{0}'', multiple is ''{1}''.", deliveryTag, multiple));
                    confirmToMap(unconfirmedMap, deliveryTag, multiple, null, true);
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    logger.debug(MessageFormat.format("Server return nack, deliveryTag is ''{0}'', multiple is ''{1}''.", deliveryTag, multiple));
                    confirmToMap(unconfirmedMap, deliveryTag, multiple, null, false);
                }
            });
            byte[] bodyByte = RabbitMessageHelper.convertBodyToBytes(message.getBody(), charset);
            //Delivery mode: non-persistent (1) or persistent (2)
            String json = JsonConvert.toJsonString(message);
            long nextSeqNo = channel.getNextPublishSeqNo();
            logger.debug(MessageFormat.format("Ready to push ''{0}'' with deliveryTag ''{1}''", json, nextSeqNo));
            channel.basicPublish(exchangeName, message.getRoutingKey(), mandatory, immediate, basicProperties, bodyByte);
            unconfirmedMap.put(nextSeqNo, message);
            isPublished = true;
            rst = channel.waitForConfirms(confirmTimeoutMillisecond);
            logger.debug(MessageFormat.format("Return ''{1}'' for confirm when push ''{0}''.", json, rst));
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        finally {
            closeChannel(channel);
            channel = null;
        }
        if (!isPublished) {
            logger.warn(MessageFormat.format("This is unpublished, message is ''{0}''.", JsonConvert.toJsonString(message)));
            if (failedListener != null) {
                failedListener.fail(new PublisherFailedData(message.getMessageId(), "unpublished"));
            }
            rst = false;
        }
        if (unconfirmedMap.size() > 0) {
            failFromMap(unconfirmedMap);
            rst = false;
        }
        return rst;
    }

    public boolean publishBatch(List<T> messageList) {
        return publishBatch(messageList, waitForConfirmMillisecond);
    }

    public boolean publishBatch(List<T> messageList, long confirmTimeoutMillisecond) {
        if (messageList == null) {
            throw new NullPointerException();
        }
        Map<String, T> unpublishedMap = new HashMap<>();//未发送列表
        for (T message : messageList) {
            if (message == null) {
                throw new NullPointerException();
            }
            unpublishedMap.put(message.getMessageId(), message);
        }
        boolean rst = false;
        Channel channel = null;
        final SortedMap<Long, T> unconfirmedMap = new TreeMap<>();//已发送未确认列表
        try {
            channel = getChannel();
            channel.confirmSelect();
            final CountDownLatch countDownLatch = new CountDownLatch(messageList.size());
            addReturnListener(channel);
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    //确认收到
                    logger.debug(MessageFormat.format("Server return ack, deliveryTag is ''{0}'', multiple is ''{1}''.", deliveryTag, multiple));
                    synchronized (unconfirmedMap) {
                        confirmToMap(unconfirmedMap, deliveryTag, multiple, countDownLatch, true);
                    }
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    //没有收到
                    logger.debug(MessageFormat.format("Server return nack, deliveryTag is ''{0}'', multiple is ''{1}''.", deliveryTag, multiple));
                    synchronized (unconfirmedMap) {
                        confirmToMap(unconfirmedMap, deliveryTag, multiple, countDownLatch, false);
                    }
                }
            });
            //逐条发布，并放入未确认map
            for (T message : messageList) {
                byte[] bodyByte = RabbitMessageHelper.convertBodyToBytes(message.getBody(), charset);
                String json = JsonConvert.toJsonString(message);
                long nextSeqNo = channel.getNextPublishSeqNo();
                logger.debug(MessageFormat.format("Ready to push ''{0}'' with deliveryTag ''{1}''", json, nextSeqNo));
                channel.basicPublish(exchangeName, message.getRoutingKey(), mandatory, immediate, basicProperties, bodyByte);
                unconfirmedMap.put(nextSeqNo, message);
                unpublishedMap.remove(message.getMessageId());
            }
            //等待完成
            rst = countDownLatch.await(confirmTimeoutMillisecond, TimeUnit.MILLISECONDS);
            logger.debug(MessageFormat.format("The await of countDownLatch returns ''{0}''.", rst));
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        finally {
            closeChannel(channel);
            channel = null;
        }
        //检查是否完成
        if (unpublishedMap.size() > 0) {
            //有未发送的
            unpublishedFromMap(unpublishedMap);
            if (rst) rst = false;
        }
        if (unconfirmedMap.size() > 0) {
            //有未确认的
            failFromMap(unconfirmedMap);
            if (rst) rst = false;
        }
        if (rst) {
            logger.debug("All are confirmed when push a batch list.");
        }
        return rst;
    }

    private void addReturnListener(Channel channel) {
        if (returnCallBackListener != null) {
            channel.addReturnListener(new ReturnListener() {
                @Override
                public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String content = RabbitMessageHelper.convertBodyToString(body, charset);
                    logger.debug(MessageFormat.format("ReturnCallback for text:{0}. code:{1}. exchange:{2}. routingKey:{3}. message:{4}." + routingKey, replyText, replyCode, exchange, routingKey, content));
                    returnCallBackListener.returnedMessage(new ReturnCallBackData(content, replyCode, replyText, exchange, routingKey));
                }
            });
        }
    }

    protected void confirmToMap(SortedMap<Long, T> unconfirmedMap, long deliveryTag, boolean multiple, CountDownLatch countDownLatch, boolean ack) {
        Set<Long> keySet;
        if (multiple) {
            keySet = unconfirmedMap.headMap(deliveryTag + 1).keySet();
        }
        else {
            keySet = new TreeSet<>();
            keySet.add(deliveryTag);
        }
        for (Long seqNo : keySet) {
            T message = unconfirmedMap.get(seqNo);
            logger.debug(MessageFormat.format("The ack of deliveryTag ''{0}'' is ''{1}'', the message is ''{2}''.", seqNo, ack, JsonConvert.toJsonString(message)));
            if (confirmCallBackListener != null) {
                confirmCallBackListener.confirm(new ConfirmCallBackData(message.getMessageId(), ack, ""));
            }
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
        if (multiple) {
            unconfirmedMap.headMap(deliveryTag + 1).clear();
        }
        else {
            unconfirmedMap.remove(deliveryTag);
        }
    }

    protected void unpublishedFromMap(Map<String, T> unpublishedMap) {
        for (T message : unpublishedMap.values()) {
            logger.warn(MessageFormat.format("This is unpublished, message is ''{0}''.", JsonConvert.toJsonString(message)));
            if (failedListener != null) {
                failedListener.fail(new PublisherFailedData(message.getMessageId(), "unpublished"));
            }
        }
    }

    protected void failFromMap(SortedMap<Long, T> unconfirmedMap) {
        for (Long seqNo : unconfirmedMap.keySet()) {
            T message = unconfirmedMap.get(seqNo);
            logger.warn(MessageFormat.format("The deliveryTag of ''{0}'' is unconfirmed, message is ''{1}''.", seqNo, JsonConvert.toJsonString(message)));
            if (failedListener != null) {
                failedListener.fail(new PublisherFailedData(message.getMessageId(), seqNo, "unconfirmed"));
            }
        }
    }
}
