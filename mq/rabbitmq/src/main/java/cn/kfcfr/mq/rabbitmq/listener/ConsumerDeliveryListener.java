package cn.kfcfr.mq.rabbitmq.listener;

import java.io.Serializable;
import java.nio.charset.Charset;

/**
 * Created by zhangqf77 on 2018/6/8.
 */
public interface ConsumerDeliveryListener extends Serializable {
    boolean handle(ConsumerDeliveryData data);

    Charset getCharset();
}
