package cn.kfcfr.mq.rabbitmq.message;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class DirectMessage extends AbstractMessage {
    private static final long serialVersionUID = -2039399246632567813L;

    public DirectMessage(String body, String routingKey) {
        this(body, routingKey, null);
    }

    public DirectMessage(String body, String routingKey, String id) {
        super(body, routingKey, id);
        if (StringUtils.isEmpty(routingKey)) {
            throw new IllegalArgumentException("routingKey cannot be empty.");
        }
    }
}
