package cn.kfcfr.core.transfer;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Restful API返回结果格式
 * status 1-成功 0-失败
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
@ToString
@EqualsAndHashCode
public class BaseResponseTransfer implements Serializable {
    private static final long serialVersionUID = 4018769625055307628L;
    private int status;
    private List<BusinessMessage> messages;//主要存放业务逻辑方面的错误信息
    private Exception exception;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<BusinessMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<BusinessMessage> messages) {
        this.messages = messages;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public BaseResponseTransfer() {
        this.status = 1;
        this.messages = null;
        this.exception = null;
    }

    public BaseResponseTransfer(int status) {
        this.status = status;
        this.messages = null;
        this.exception = null;
    }

    public BaseResponseTransfer(BusinessMessage message) {
        this.status = 0;
        this.messages = new ArrayList<>();
        addMessage(message);
        this.exception = null;
    }

    public BaseResponseTransfer(Exception ex) {
        this.status = 0;
        this.messages = new ArrayList<>();
        this.exception = ex;
    }

    public BaseResponseTransfer(int status, BusinessMessage message, Exception ex) {
        this.status = status;
        this.messages = new ArrayList<>();
        addMessage(message);
        this.exception = ex;
    }

    public void addMessage(BusinessMessage message) {
        this.messages.add(message);
    }
}
