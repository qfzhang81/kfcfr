package cn.kfcfr.core.transfer;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * Restful API返回结果格式
 * status 1-成功 0-失败
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
@ToString
@EqualsAndHashCode
public class ListResponseTransfer<T> extends BaseResponseTransfer {
    private static final long serialVersionUID = -1143476970544939159L;
    private List<T> result;

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public ListResponseTransfer() {
        super();
        this.result = null;
    }

    public ListResponseTransfer(List<T> result) {
        super();
        this.result = result;
    }

    public ListResponseTransfer(int status, List<T> result) {
        super(status);
        this.result = result;
    }

    public ListResponseTransfer(BusinessMessage message) {
        super(message);
        this.result = null;
    }

    public ListResponseTransfer(Exception ex) {
        super(ex);
        this.result = null;
    }

    public ListResponseTransfer(int status, BusinessMessage message, Exception ex) {
        super(status, message, ex);
        this.result = null;
    }
}
