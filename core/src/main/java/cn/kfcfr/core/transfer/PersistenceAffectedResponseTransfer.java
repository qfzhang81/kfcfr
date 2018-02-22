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
public class PersistenceAffectedResponseTransfer<T> extends BaseResponseTransfer {
    private static final long serialVersionUID = 7746417951111583132L;
    private PersistenceAffectedResult<T> result;

    public PersistenceAffectedResult<T> getResult() {
        return result;
    }

    public void setResult(PersistenceAffectedResult<T> result) {
        this.result = result;
    }

    public PersistenceAffectedResponseTransfer() {
        super();
        this.result = null;
    }

    public PersistenceAffectedResponseTransfer(PersistenceAffectedResult<T> result) {
        super();
        this.result = result;
    }

    public PersistenceAffectedResponseTransfer(int status, PersistenceAffectedResult<T> result) {
        super(status);
        this.result = result;
    }

    public PersistenceAffectedResponseTransfer(BusinessMessage message) {
        super(message);
        this.result = null;
    }

    public PersistenceAffectedResponseTransfer(Exception ex) {
        super(ex);
        this.result = null;
    }

    public PersistenceAffectedResponseTransfer(int status, BusinessMessage message, Exception ex) {
        super(status, message, ex);
        this.result = null;
    }
}
