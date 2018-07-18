package cn.kfcfr.mq.rabbitmq.listener;

import java.io.Serializable;

/**
 * Created by zhangqf77 on 2018/7/17.
 */
public interface PublisherFailedListener extends Serializable {
    void fail(PublisherFailedData data);
}
