package cn.kfcfr.core.pojo.response;

import cn.kfcfr.core.pojo.result.MessageReturnResult;

import java.util.List;

/**
 * Created by zhangqf77 on 2018/2/23.
 */
public class MessageResponseTransfer<T> extends AbstractResponseTransfer {
    private static final long serialVersionUID = -3111669130780643122L;
    private MessageReturnResult<T> result;

    public MessageResponseTransfer(List<T> result) {
        super(ResponseStatus.Warning);
        this.result = new MessageReturnResult<>(result);
    }

    @Override
    public MessageReturnResult<T> getResult() {
        return result;
    }

    public void setResult(MessageReturnResult<T> result) {
        this.result = result;
    }
}
