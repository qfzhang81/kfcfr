package cn.kfcfr.mq.rabbitmq.channel;

import cn.kfcfr.core.convert.JsonConvert;
import cn.kfcfr.mq.rabbitmq.helper.RabbitMessageHelper;
import cn.kfcfr.mq.rabbitmq.message.AbstractMessage;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
public abstract class AbstractPublisherChannel<T extends AbstractMessage> extends AbstractChannel {
    //    protected boolean confirms;
    protected boolean returns;
    protected long waitForConfirmMillisecond;
    protected boolean mandatory;
    protected boolean immediate;
    protected boolean durable;
    protected AMQP.BasicProperties basicProperties;

    protected String exchangeName;
    protected Charset charset;

    public AbstractPublisherChannel(ConnectionFactory factory, String exchangeName, Charset charset, boolean returns, long waitForConfirmMillisecond, boolean mandatory, boolean immediate) {
        this.factory = factory;
        this.exchangeName = exchangeName;
        this.charset = charset;
        this.waitForConfirmMillisecond = waitForConfirmMillisecond;
//        this.confirms = true;
        this.returns = returns;
        this.mandatory = mandatory;
        this.immediate = immediate;
        init();
    }

    protected abstract void init();

    public boolean publishSingle(T message) {
        Channel channel = null;
        boolean rst = false;
        try {
            channel = getChannel();
            channel.confirmSelect();
            byte[] bodyByte = RabbitMessageHelper.convertBodyToBytes(message.getBody(), charset, DEFAULT_CHARSET);
            //Delivery mode: non-persistent (1) or persistent (2)
            String json = JsonConvert.toJsonString(message);
            logger.debug(MessageFormat.format("Ready to push ''{0}''", json));
            channel.basicPublish(exchangeName, message.getRoutingKey(), mandatory, immediate, basicProperties, bodyByte);
            rst = channel.waitForConfirms(waitForConfirmMillisecond);
            logger.debug(MessageFormat.format("Return ''{1}'' for confirm when push ''{0}''.", json, rst));
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

    public boolean publishBatch(List<T> messageList) {
        Channel channel = null;
        boolean rst = false;
        try {
            channel = getChannel();
            channel.confirmSelect();
            final List<T> failedList = new ArrayList<>();
            final SortedMap<Long, T> unConfirmedMap = new TreeMap<>();
            final CountDownLatch countDownLatch = new CountDownLatch(messageList.size());
            channel.addConfirmListener(new ConfirmListener() {
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    //确认收到
                    logger.info(MessageFormat.format("Server return Ack, deliveryTag is ''{0}'', multiple is ''{1}''.", deliveryTag, multiple));
                    int cnt = 0;
                    if (multiple) {
                        cnt = unConfirmedMap.headMap(deliveryTag + 1).keySet().size();
                        unConfirmedMap.headMap(deliveryTag + 1).clear();
                    }
                    else {
                        cnt = 1;
                        unConfirmedMap.remove(deliveryTag);
                    }
                    logger.debug(MessageFormat.format("Confirm ack {0} total.", cnt));
                    for (int i = 0; i < cnt; i++) {
                        countDownLatch.countDown();
                    }
                }

                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    //没有收到
                    logger.info(MessageFormat.format("Server return Nack, deliveryTag is ''{0}'', multiple is ''{1}''.", deliveryTag, multiple));
                    int cnt = 0;
                    if (multiple) {
                        cnt = unConfirmedMap.headMap(deliveryTag + 1).keySet().size();
                        failedList.addAll(unConfirmedMap.headMap(deliveryTag + 1).values());
                        unConfirmedMap.headMap(deliveryTag + 1).clear();
                    }
                    else {
                        cnt = 1;
                        failedList.add(unConfirmedMap.get(deliveryTag));
                        unConfirmedMap.remove(deliveryTag);
                    }
                    logger.debug(MessageFormat.format("Nack {0} total.", cnt));
                    for (int i = 0; i < cnt; i++) {
                        countDownLatch.countDown();
                    }
                }
            });
            //逐条发布，并放入未确认map
            for (T message : messageList) {
                send(channel, message, unConfirmedMap);
            }
            //等待完成
            rst = countDownLatch.await(waitForConfirmMillisecond, TimeUnit.MILLISECONDS);
            if (unConfirmedMap.size() > 0) {
                //有未确认的
                logger.info(MessageFormat.format("The following are unconfirmed: {0}", JsonConvert.toJsonString(unConfirmedMap)));
                rst = false;
            }
            if (failedList.size() > 0) {
                //有失败的
                logger.info(MessageFormat.format("The following are failed to push: {0}", JsonConvert.toJsonString(failedList)));
                rst = false;
            }
            if (rst) {
                logger.debug("All are confirmed when push a batch list.");
            }
            else {
                logger.debug("Return false when push a batch list.");
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

    private void send(Channel channel, T message, SortedMap<Long, T> unConfirmedMap) throws IOException {
        byte[] bodyByte = RabbitMessageHelper.convertBodyToBytes(message.getBody(), charset, DEFAULT_CHARSET);
        String json = JsonConvert.toJsonString(message);
        long nextSeqNo = channel.getNextPublishSeqNo();
        logger.debug(MessageFormat.format("Ready to push ''{0}'' with SeqNo ''{1}''", json, nextSeqNo));
        channel.basicPublish(exchangeName, message.getRoutingKey(), mandatory, immediate, basicProperties, bodyByte);
        if (unConfirmedMap != null) {
            unConfirmedMap.put(nextSeqNo, message);
        }
    }

//    protected class PublishThread extends Thread {
//        private Channel channel;
//        T message;
//        long nextSeqNo;
//        SortedMap<Long, T> unConfirmedMap;
//
//        public PublishThread(Channel channel, T message, long nextSeqNo, SortedMap<Long, T> unConfirmedMap) {
//            this.channel = channel;
//            this.message = message;
//            this.nextSeqNo = nextSeqNo;
//            this.unConfirmedMap = unConfirmedMap;
//        }
//
//        @Override
//        public void run() {
//            byte[] bodyByte = RabbitMessageHelper.convertBodyToBytes(message.getBody(), charset, DEFAULT_CHARSET);
//            String json = JsonConvert.toJsonString(message);
//            logger.debug(MessageFormat.format("Ready to publish '{0}' with SeqNo '{1}'", json, nextSeqNo));
//            try {
//                channel.basicPublish(exchangeName, message.getRoutingKey(), mandatory, immediate, basicProperties, bodyByte);
//                if (confirms && unConfirmedMap != null) {
//                    unConfirmedMap.put(nextSeqNo, message);
//                }
//            }
//            catch (Exception ex) {
//                logger.error(ex.getMessage(), ex);
//            }
//        }
//    }


}
