package cn.kfcfr.mq.rabbitmq.message;

import java.io.Serializable;
import java.nio.charset.Charset;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
public abstract class AbstractMessage implements Serializable {
    private static final long serialVersionUID = 8204603327071509051L;
    protected String body;
    protected String routingKey;
    protected String id;
    protected Charset charset;

    public AbstractMessage(String body, String routingKey, String id, Charset charset) {
        this.body = body;
        this.routingKey = routingKey;
        this.id = id;
        this.charset = charset;
    }

    public String getBody() {
        return body;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public String getId() {
        return id;
    }

    public Charset getCharset() {
        return charset;
    }
}
