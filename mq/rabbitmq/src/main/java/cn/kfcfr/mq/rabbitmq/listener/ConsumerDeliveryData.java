package cn.kfcfr.mq.rabbitmq.listener;

import java.io.Serializable;

/**
 * Created by zhangqf77 on 2018/6/20.
 */
public class ConsumerDeliveryData implements Serializable {
    private static final long serialVersionUID = 1436462030414070030L;
    private String consumerTag;
    private String correlationDataId;
    private String message;
    private long deliveryTag;
    private String routingKey;

    public ConsumerDeliveryData(String consumerTag, String correlationDataId, String message, long deliveryTag, String routingKey) {
        this.consumerTag = consumerTag;
        this.correlationDataId = correlationDataId;
        this.message = message;
        this.deliveryTag = deliveryTag;
        this.routingKey = routingKey;
    }

    public String getConsumerTag() {
        return consumerTag;
    }

    public String getCorrelationDataId() {
        return correlationDataId;
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
