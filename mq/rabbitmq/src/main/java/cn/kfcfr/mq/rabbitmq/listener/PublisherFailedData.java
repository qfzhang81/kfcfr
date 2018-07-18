package cn.kfcfr.mq.rabbitmq.listener;

import java.io.Serializable;

/**
 * Created by zhangqf77 on 2018/7/17.
 */
public class PublisherFailedData implements Serializable {
    private static final long serialVersionUID = -6806807877165761078L;
    private String messageId;
    private Long deliveryTag;
    private String cause;

    public PublisherFailedData(String messageId, String cause) {
        this.messageId = messageId;
        this.cause = cause;
    }

    public PublisherFailedData(String messageId, Long deliveryTag, String cause) {
        this.messageId = messageId;
        this.deliveryTag = deliveryTag;
        this.cause = cause;
    }

    public String getMessageId() {
        return messageId;
    }

    public Long getDeliveryTag() {
        return deliveryTag;
    }

    public String getCause() {
        return cause;
    }
}
