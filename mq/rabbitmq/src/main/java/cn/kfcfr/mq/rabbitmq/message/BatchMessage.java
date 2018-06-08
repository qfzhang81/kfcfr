package cn.kfcfr.mq.rabbitmq.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
public class BatchMessage<T extends AbstractMessage> implements Serializable {
    private static final long serialVersionUID = 7613056963850782467L;
    private List<T> messages;

    public BatchMessage() {
        this.messages = new ArrayList<>();
    }

    public BatchMessage(List<T> messages) {
        this.messages = messages;
    }

    public void AddMessage(T message) {
        messages.add(message);
    }

    public List<T> getMessages() {
        return messages;
    }
}
