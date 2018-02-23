package cn.kfcfr.core.pojo.response;

import cn.kfcfr.core.pojo.result.ListReturnResult;

import java.util.List;

/**
 * Created by zhangqf77 on 2018/2/23.
 */
public class ListResponseTransfer<T> extends AbstractResponseTransfer {
    private static final long serialVersionUID = -9162538455472062295L;
    private ListReturnResult<T> result;

    public ListResponseTransfer(List<T> result) {
        super(ResponseStatus.Success);
        this.result = new ListReturnResult<>(result);
    }

    @Override
    public ListReturnResult<T> getResult() {
        return result;
    }

    public void setResult(ListReturnResult<T> result) {
        this.result = result;
    }
}
