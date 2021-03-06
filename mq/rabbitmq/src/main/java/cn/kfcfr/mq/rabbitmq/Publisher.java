package cn.kfcfr.mq.rabbitmq;

import cn.kfcfr.mq.rabbitmq.message.AbstractMessage;

import java.util.List;

/**
 * Created by zhangqf77 on 2018/7/19.
 */
public interface Publisher<T extends AbstractMessage> {
    boolean publishSingle(T message);

    boolean publishSingle(T message, long confirmTimeoutMillisecond);

    boolean publishBatch(List<T> messageList);

    boolean publishBatch(List<T> messageList, long confirmTimeoutMillisecond);
}
