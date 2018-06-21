package cn.kfcfr.mq.rabbitmq.message;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
public class TopicMessage extends AbstractMessage {
    private static final long serialVersionUID = 3256228585550306666L;

    public TopicMessage(String body, String routingKey) {
        super(body, routingKey, null);
    }

    public TopicMessage(String body, String routingKey, String id) {
        super(body, routingKey, id);
    }
}
