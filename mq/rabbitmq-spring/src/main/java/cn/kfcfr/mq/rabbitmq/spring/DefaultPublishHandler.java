package cn.kfcfr.mq.rabbitmq.spring;

import cn.kfcfr.core.convert.JsonConvert;
import cn.kfcfr.mq.rabbitmq.helper.RabbitMessageHelper;
import cn.kfcfr.mq.rabbitmq.listener.ConfirmCallBackData;
import cn.kfcfr.mq.rabbitmq.listener.ConfirmCallBackListener;
import cn.kfcfr.mq.rabbitmq.listener.ReturnCallBackData;
import cn.kfcfr.mq.rabbitmq.listener.ReturnCallBackListener;
import cn.kfcfr.mq.rabbitmq.message.AbstractMessage;
import cn.kfcfr.mq.rabbitmq.message.BatchMessage;
import cn.kfcfr.mq.rabbitmq.message.SingleMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

import java.nio.charset.Charset;
import java.text.MessageFormat;

/**
 * Created by zhangqf77 on 2018/6/19.
 */
public class DefaultPublishHandler<T extends AbstractMessage> extends AbstractMqHandler {
    protected String exchangeName;
    protected boolean mandatory;
    protected ConfirmCallBackListener confirmCallBackListener;
    protected ReturnCallBackListener returnCallBackListener;
    protected Charset charset;

    protected RabbitTemplate template;

    public DefaultPublishHandler(ConnectionFactory connectionFactory, String exchangeName, Charset charset, ConfirmCallBackListener confirmCallBackListener, ReturnCallBackListener returnCallBackListener) {
        this.connectionFactory = connectionFactory;
        this.exchangeName = exchangeName;
        this.charset = charset;
        this.confirmCallBackListener = confirmCallBackListener;
        this.returnCallBackListener = returnCallBackListener;
        init();
        createTemplate();
    }

    protected void init() {
        mandatory = true;
    }

    protected void createTemplate() {
        template = new RabbitTemplate(connectionFactory);
        template.setMandatory(mandatory);
        template.setExchange(exchangeName);
        if (confirmCallBackListener != null) {
            template.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
                public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                    logger.debug(MessageFormat.format("ConfirmCallback for confirm '{1}' when push '{0}', cause:{2}.", correlationData, ack, cause));
                    confirmCallBackListener.confirm(new ConfirmCallBackData(correlationData.getId(), ack, cause));
                }
            });
        }
        if (returnCallBackListener != null) {
            template.setReturnCallback(new RabbitTemplate.ReturnCallback() {
                public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                    logger.debug(MessageFormat.format("ReturnCallback for text:{0}. code:{1}. exchange:{2}. routingKey:{3}. message:{4}." + routingKey, replyText, replyCode, exchange, routingKey, message));
                    String content = RabbitMessageHelper.convertBodyToString(message.getBody(), charset, DEFAULT_CHARSET);
                    returnCallBackListener.returnedMessage(new ReturnCallBackData(content, replyCode, replyText, exchange, routingKey));
                }
            });
        }
    }

    public boolean publishSingle(SingleMessage<T> singleMessage) {
        boolean rst = false;
        try {
            AbstractMessage message = singleMessage.getMessage();
            byte[] bodyByte = RabbitMessageHelper.convertBodyToBytes(message.getBody(), charset, DEFAULT_CHARSET);
            CorrelationData correlationData = null;
            if (!StringUtils.isBlank(message.getId())) {
                correlationData = new CorrelationData(message.getId());
            }
            String json = JsonConvert.toJsonString(message);
            logger.debug(MessageFormat.format("Ready to push '{0}'", json));
            template.convertAndSend(message.getRoutingKey(), bodyByte, correlationData);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return rst;
    }

    public boolean publishBatch(BatchMessage<T> batchMessage) {
        return false;
    }
}
