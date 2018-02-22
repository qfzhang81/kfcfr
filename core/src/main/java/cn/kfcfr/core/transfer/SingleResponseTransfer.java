package cn.kfcfr.core.transfer;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Restful API返回结果格式
 * status 1-成功 0-失败
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
@ToString
@EqualsAndHashCode
public class SingleResponseTransfer<T> extends BaseResponseTransfer {
    private static final long serialVersionUID = -890144153789868904L;
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public SingleResponseTransfer() {
        super();
        this.result = null;
    }

    public SingleResponseTransfer(T result) {
        super();
        this.result = result;
    }

    public SingleResponseTransfer(int status, T result) {
        super(status);
        this.result = result;
    }

    public SingleResponseTransfer(BusinessMessage message) {
        super(message);
        this.result = null;
    }

    public SingleResponseTransfer(Exception ex) {
        super(ex);
        this.result = null;
    }

    public SingleResponseTransfer(int status, BusinessMessage message, Exception ex) {
        super(status, message, ex);
        this.result = null;
    }
}
