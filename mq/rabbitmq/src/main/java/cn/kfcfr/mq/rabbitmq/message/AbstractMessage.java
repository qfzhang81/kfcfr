package cn.kfcfr.mq.rabbitmq.message;

import cn.kfcfr.core.constant.DateTimeConstant;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public abstract class AbstractMessage implements Serializable {
    private static final long serialVersionUID = 8204603327071509051L;
    private static final SimpleDateFormat sdf = new SimpleDateFormat(DateTimeConstant.DATETIME_12_FORMAT);
    protected String body;
    protected String routingKey;
    protected String messageId;

    public AbstractMessage(String body, String routingKey, String messageId) {
        if (body == null) {
            throw new NullPointerException("body cannot be null.");
        }
        this.body = body;
        this.routingKey = routingKey;
        if (StringUtils.isBlank(messageId)) {
            this.messageId = createId();
        }
        else {
            this.messageId = messageId;
        }
    }

    public String getBody() {
        return body;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public String getMessageId() {
        return messageId;
    }

    protected String createId() {
        UUID uuid = UUID.randomUUID();
        return sdf.format(new Date()) + uuid.toString().replace("-", "");
    }
}
