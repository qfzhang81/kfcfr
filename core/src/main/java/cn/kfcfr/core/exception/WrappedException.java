package cn.kfcfr.core.exception;

/**
 * Created by zhangqf77 on 2018/5/21.
 * 包装用的异常，具体异常见内部的cause
 */
public class WrappedException extends RuntimeException {
    private static final long serialVersionUID = -7336205207601213308L;

    public WrappedException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrappedException(Throwable cause) {
        super(cause);
    }
}
