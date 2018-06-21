package cn.kfcfr.mq.rabbitmq.message;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
public class DirectMessage extends AbstractMessage {
    private static final long serialVersionUID = -2039399246632567813L;

    public DirectMessage(String body, String routingKey) {
        super(body, routingKey, null);
    }

    public DirectMessage(String body, String routingKey, String id) {
        super(body, routingKey, id);
    }
}
