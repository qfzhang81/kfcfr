package cn.kfcfr.core.exception;

import cn.kfcfr.core.pojo.BusinessMessage;

import java.util.List;

/**
 * Created by zhangqf77 on 2018/2/23.
 */
public class MessageListBusinessException extends AbstractBusinessException {
    private static final long serialVersionUID = -2322633658193743858L;
    private List<BusinessMessage> messageList;

    public MessageListBusinessException(List<BusinessMessage> messageList) {
        super();
        this.messageList = messageList;

    }

    public MessageListBusinessException(List<BusinessMessage> messageList, Throwable cause) {
        super(cause);
        this.messageList = messageList;
    }

    public List<BusinessMessage> getMessageList() {
        return messageList;
    }
}
