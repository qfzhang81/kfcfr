package cn.kfcfr.mq.rabbitmq.message;

import java.nio.charset.Charset;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
public class TopicMessage extends AbstractMessage {
    public TopicMessage(String body, String routingKey, String id) {
        super(body, routingKey, id, null);
    }

    public TopicMessage(String body, String routingKey, String id, Charset charset) {
        super(body, routingKey, id, charset);
    }
}
