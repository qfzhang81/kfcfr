package cn.kfcfr.mq.rabbitmq.message;

import java.io.Serializable;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
public class SingleMessage<T extends AbstractMessage> implements Serializable {
    private static final long serialVersionUID = 102580559169492950L;
    private T message;

    public SingleMessage(T message) {
        this.message = message;
    }

    public T getMessage() {
        return message;
    }
}
