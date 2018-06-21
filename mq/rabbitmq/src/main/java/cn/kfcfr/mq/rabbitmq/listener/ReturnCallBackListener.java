package cn.kfcfr.mq.rabbitmq.listener;

import java.io.Serializable;

/**
 * Created by zhangqf77 on 2018/6/19.
 */
public interface ReturnCallBackListener extends Serializable {
    void returnedMessage(ReturnCallBackData data);
}
