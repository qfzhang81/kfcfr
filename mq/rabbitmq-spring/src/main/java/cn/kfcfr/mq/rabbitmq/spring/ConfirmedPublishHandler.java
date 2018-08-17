package cn.kfcfr.mq.rabbitmq.spring;

import cn.kfcfr.core.convert.JsonConvert;
import cn.kfcfr.mq.rabbitmq.Publisher;
import cn.kfcfr.mq.rabbitmq.helper.RabbitMessageHelper;
import cn.kfcfr.mq.rabbitmq.listener.*;
import cn.kfcfr.mq.rabbitmq.message.AbstractMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangqf77 on 2018/6/19.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused", "all"})
public class ConfirmedPublishHandler<T extends AbstractMessage> extends AbstractMqHandler implements Publisher<T> {
    protected long waitForConfirmMillisecond;//确认时默认超时时间，毫秒
    protected boolean mandatory;
    protected String exchangeName;
    protected Charset charset;
    protected ConfirmCallBackListener confirmCallBackListener;
    protected ReturnCallBackListener returnCallBackListener;
    protected PublisherFailedListener failedListener;

    public ConfirmedPublishHandler(ConnectionFactory connectionFactory, String exchangeName, Charset charset, ConfirmCallBackListener confirmCallBackListener, ReturnCallBackListener returnCallBackListener, PublisherFailedListener failedListener) {
        this.connectionFactory = connectionFactory;
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
        mandatory = true;
        this.waitForConfirmMillisecond = 10000;
    }

    protected RabbitTemplate createTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMandatory(mandatory);
        template.setExchange(exchangeName);
        return template;
    }

    public boolean publishSingle(T message) {
        return publishSingle(message, waitForConfirmMillisecond);
    }

    public boolean publishSingle(T message, long confirmTimeoutMillisecond) {
        if (message == null) {
            throw new NullPointerException();
        }
        boolean rst = false;
        RabbitTemplate template = null;
        boolean isPublished = false;
        final SortedMap<String, T> unconfirmedMap = new TreeMap<>();//已发送未确认列表
        try {
            template = createTemplate();
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            addReturnListener(template);
            template.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
                @Override
                public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                    logger.debug(MessageFormat.format("ConfirmCallback for confirm ''{1}'' when push ''{0}'', cause:{2}.", correlationData, ack, cause));
                    confirmToMap(unconfirmedMap, countDownLatch, correlationData, ack, cause);
                }
            });
            //发送
            byte[] bodyByte = RabbitMessageHelper.convertBodyToBytes(message.getBody(), charset);
            CorrelationData correlationData = new CorrelationData(message.getMessageId());
            String json = JsonConvert.toJsonString(message);
            logger.debug(MessageFormat.format("Ready to push ''{0}''", json));
            template.convertAndSend(message.getRoutingKey(), bodyByte, correlationData);
            unconfirmedMap.put(correlationData.getId(), message);
            isPublished = true;
            //等待完成
            rst = countDownLatch.await(confirmTimeoutMillisecond, TimeUnit.MILLISECONDS);
            logger.debug(MessageFormat.format("The await of countDownLatch returns ''{0}''.", rst));
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        finally {
            template = null;
        }
        if (!isPublished) {
            unpublishedMessage(message);
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
        RabbitTemplate template = null;
        final SortedMap<String, T> unconfirmedMap = new TreeMap<>();//已发送未确认列表
        try {
            template = createTemplate();
            final CountDownLatch countDownLatch = new CountDownLatch(messageList.size());
            addReturnListener(template);
            template.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
                @Override
                public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                    logger.debug(MessageFormat.format("ConfirmCallback for confirm ''{1}'' when push ''{0}'', cause:{2}.", correlationData, ack, cause));
                    confirmToMap(unconfirmedMap, countDownLatch, correlationData, ack, cause);
                }
            });
            //逐条发布，并放入未确认map
            for (T message : messageList) {
                byte[] bodyByte = RabbitMessageHelper.convertBodyToBytes(message.getBody(), charset);
                CorrelationData correlationData = new CorrelationData(message.getMessageId());
                String json = JsonConvert.toJsonString(message);
                logger.debug(MessageFormat.format("Ready to push ''{0}''", json));
                template.convertAndSend(message.getRoutingKey(), bodyByte, correlationData);
                unconfirmedMap.put(message.getMessageId(), message);
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
            template = null;
        }
        //检查是否完成
        if (unpublishedMap.size() > 0) {
            //有未发送的
            for (T message : unpublishedMap.values()) {
                unpublishedMessage(message);
            }
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

    protected void addReturnListener(RabbitTemplate template) {
        if (returnCallBackListener != null) {
            template.setReturnCallback(new RabbitTemplate.ReturnCallback() {
                public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                    logger.debug(MessageFormat.format("ReturnCallback for text:{0}. code:{1}. exchange:{2}. routingKey:{3}. message:{4}." + routingKey, replyText, replyCode, exchange, routingKey, message));
                    String content = RabbitMessageHelper.convertBodyToString(message.getBody(), charset);
                    returnCallBackListener.returnedMessage(new ReturnCallBackData(content, replyCode, replyText, exchange, routingKey));
                }
            });
        }
    }

    protected void confirmToMap(SortedMap<String, T> unconfirmedMap, CountDownLatch countDownLatch, CorrelationData correlationData, boolean ack, String cause) {
        if (confirmCallBackListener != null) {
            confirmCallBackListener.confirm(new ConfirmCallBackData(correlationData.getId(), ack, cause));
        }
        unconfirmedMap.remove(correlationData.getId());
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    protected void unpublishedMessage(T message) {
        logger.warn(MessageFormat.format("This is unpublished, message is ''{0}''.", JsonConvert.toJsonString(message)));
        if (failedListener != null) {
            failedListener.fail(new PublisherFailedData(message.getMessageId(), "unpublished"));
        }
    }

    protected void failFromMap(SortedMap<String, T> unconfirmedMap) {
        for (String id : unconfirmedMap.keySet()) {
            T message = unconfirmedMap.get(id);
            logger.warn(MessageFormat.format("The message is unconfirmed, ''{0}''.", JsonConvert.toJsonString(message)));
            if (failedListener != null) {
                failedListener.fail(new PublisherFailedData(message.getMessageId(), "unconfirmed"));
            }
        }
    }
}
