package cn.kfcfr.core.exception;

/**
 * Created by zhangqf77 on 2018/2/23.
 */
public abstract class AbstractBusinessException extends RuntimeException {
    private static final long serialVersionUID = -4130191731009509769L;

    public AbstractBusinessException() {
        super();
    }

    public AbstractBusinessException(String message) {
        super(message);
    }

    public AbstractBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractBusinessException(Throwable cause) {
        super(cause);
    }
}
