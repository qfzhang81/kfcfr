package cn.kfcfr.mq.rabbitmq.message;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class FanoutMessage extends AbstractMessage {
    private static final long serialVersionUID = -5072307457242061345L;

    public FanoutMessage(String body) {
        this(body, null);
    }

    public FanoutMessage(String body, String id) {
        super(body, null, id);
    }
}
