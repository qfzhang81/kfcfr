package cn.kfcfr.core.pojo.response;

import cn.kfcfr.core.pojo.result.SingleReturnResult;

/**
 * Restful API返回结果格式
 * status 1-成功 0-失败
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class SingleResponseTransfer<T> extends AbstractResponseTransfer {
    private static final long serialVersionUID = -890144153789868904L;
    private SingleReturnResult<T> result;

    public SingleResponseTransfer(T result) {
        super(ResponseStatus.Success);
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
