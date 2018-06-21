package cn.kfcfr.mq.rabbitmq.listener;

import java.nio.charset.Charset;

/**
 * Created by zhangqf77 on 2018/6/8.
 */
public abstract class AbstractConsumerListener implements ConsumerDeliveryListener {
    private static final long serialVersionUID = -740449858990415080L;
    protected Charset charset;

    public AbstractConsumerListener() {
    }

    public AbstractConsumerListener(Charset charset) {
        this.charset = charset;
    }

    @Override
    public Charset getCharset() {
        return charset;
    }
}
