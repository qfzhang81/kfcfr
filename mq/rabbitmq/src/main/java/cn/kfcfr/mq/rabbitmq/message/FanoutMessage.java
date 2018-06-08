package cn.kfcfr.mq.rabbitmq.message;

import java.nio.charset.Charset;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
public class FanoutMessage extends AbstractMessage {
    private static final long serialVersionUID = -5072307457242061345L;

    public FanoutMessage(String body, String id) {
        super(body, null, id, null);
    }

    public FanoutMessage(String body, String id, Charset charset) {
        super(body, null, id, charset);
    }
}
