package cn.kfcfr.mq.rabbitmq.listener;

import java.io.Serializable;
import java.nio.charset.Charset;

/**
 * Created by zhangqf77 on 2018/6/8.
 */
public abstract class AbstractConsumerListener implements Serializable {
    private static final long serialVersionUID = -740449858990415080L;
    protected Charset charset;

    public abstract boolean handle(String consumerTag, String message, long deliveryTag);

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}
