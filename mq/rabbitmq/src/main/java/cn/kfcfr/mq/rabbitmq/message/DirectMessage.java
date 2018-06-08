package cn.kfcfr.mq.rabbitmq.message;

import java.nio.charset.Charset;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
public class DirectMessage extends AbstractMessage {
    private static final long serialVersionUID = -2039399246632567813L;

    public DirectMessage(String body, String routingKey, String id) {
        super(body, routingKey, id, null);
    }

    public DirectMessage(String body, String routingKey, String id, Charset charset) {
        super(body, routingKey, id, charset);
    }
}
