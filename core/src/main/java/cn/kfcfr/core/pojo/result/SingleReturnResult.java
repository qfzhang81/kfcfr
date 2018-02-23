package cn.kfcfr.core.pojo.result;

/**
 * Created by zhangqf77 on 2018/2/23.
 */
public class SingleReturnResult<T> implements IReturnResult<T> {
    private static final long serialVersionUID = -8293393403469631229L;
    private T data;

    public SingleReturnResult(T data) {
        this.data = data;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public void setData(T data) {
        this.data = data;
    }
}
