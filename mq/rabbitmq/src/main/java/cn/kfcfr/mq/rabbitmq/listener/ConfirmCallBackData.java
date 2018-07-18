package cn.kfcfr.mq.rabbitmq.listener;

import java.io.Serializable;

/**
 * Created by zhangqf77 on 2018/6/20.
 */
public class ConfirmCallBackData implements Serializable {
    private static final long serialVersionUID = 7886262356700678467L;
    private String messageId;
    private boolean ack;
    private String cause;

    public ConfirmCallBackData(String messageId, boolean ack, String cause) {
        this.messageId = messageId;
        this.ack = ack;
        this.cause = cause;
    }

    public String getMessageId() {
        return messageId;
    }

    public boolean isAck() {
        return ack;
    }

    public String getCause() {
        return cause;
    }
}
