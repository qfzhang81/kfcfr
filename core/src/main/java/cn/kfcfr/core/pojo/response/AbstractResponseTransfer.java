package cn.kfcfr.core.pojo.response;

import java.io.Serializable;

/**
 * Restful API返回结果格式
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public abstract class AbstractResponseTransfer implements Serializable, IResponseTransfer {
    private static final long serialVersionUID = 4018769625055307628L;
    private ResponseStatus status;

    public AbstractResponseTransfer(ResponseStatus status) {
        this.status = status;
    }

    public ResponseStatus getStatus() {
        return status;
    }

}
