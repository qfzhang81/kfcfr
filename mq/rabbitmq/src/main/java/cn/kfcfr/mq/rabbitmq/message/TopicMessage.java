package cn.kfcfr.mq.rabbitmq.message;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class TopicMessage extends AbstractMessage {
    private static final long serialVersionUID = 3256228585550306666L;

    public TopicMessage(String body, String routingKey) {
        this(body, routingKey, null);
    }

    public TopicMessage(String body, String routingKey, String id) {
        super(body, routingKey, id);
        if (StringUtils.isEmpty(routingKey)) {
            throw new IllegalArgumentException("routingKey cannot be empty.");
        }
    }
}
