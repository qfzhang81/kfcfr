package cn.kfcfr.core.pojo.response;

import cn.kfcfr.core.pojo.result.SingleReturnResult;

/**
 * Created by zhangqf77 on 2018/2/24.
 */
public class ExceptionResponseTransfer<T> extends AbstractResponseTransfer {
    private static final long serialVersionUID = -7962640223116247487L;
    private SingleReturnResult<T> result;

    public ExceptionResponseTransfer(T result) {
        super(ResponseStatus.Fatal);
        this.result = new SingleReturnResult<>(result);
    }

    @Override
    public SingleReturnResult<T> getResult() {
        return result;
    }

    public void setResult(SingleReturnResult<T> result) {
        this.result = result;
    }
}
