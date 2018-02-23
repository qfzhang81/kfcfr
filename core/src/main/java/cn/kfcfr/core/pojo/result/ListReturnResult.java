package cn.kfcfr.core.pojo.result;

import java.util.List;

/**
 * Created by zhangqf77 on 2018/2/23.
 */
public class ListReturnResult<T> implements IReturnResult<List<T>> {
    private static final long serialVersionUID = 87523330292179156L;
    private List<T> data;

    public ListReturnResult(List<T> data) {
        this.data = data;
    }

    @Override
    public List<T> getData() {
        return data;
    }

    @Override
    public void setData(List<T> data) {
        this.data = data;
    }
}
