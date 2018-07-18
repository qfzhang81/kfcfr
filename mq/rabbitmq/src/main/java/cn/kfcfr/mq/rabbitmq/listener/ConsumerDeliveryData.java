package cn.kfcfr.mq.rabbitmq.listener;

import java.io.Serializable;

/**
 * Created by zhangqf77 on 2018/6/20.
 */
public class ConsumerDeliveryData implements Serializable {
    private static final long serialVersionUID = 1436462030414070030L;
    private String consumerTag;
    private String messageId;
    private String message;
    private long deliveryTag;
    private String routingKey;

    public ConsumerDeliveryData(String consumerTag, String messageId, String message, long deliveryTag, String routingKey) {
        this.consumerTag = consumerTag;
        this.messageId = messageId;
        this.message = message;
        this.deliveryTag = deliveryTag;
        this.routingKey = routingKey;
    }

    public String getConsumerTag() {
        return consumerTag;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getMessage() {
        return message;
    }

    public long getDeliveryTag() {
        return deliveryTag;
    }

    public String getRoutingKey() {
        return routingKey;
    }
}
