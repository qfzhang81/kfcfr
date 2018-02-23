package cn.kfcfr.core.pojo.response;

import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.core.pojo.result.PagedReturnResult;

/**
 * Created by zhangqf77 on 2018/2/23.
 */
public class PagedResponseTransfer<T> extends AbstractResponseTransfer {
    private static final long serialVersionUID = -7599020137891393066L;
    private PagedReturnResult<T> result;

    public PagedResponseTransfer(PagedList<T> result) {
        super(ResponseStatus.Success);
        this.result = new PagedReturnResult<>(result);
    }

    @Override
    public PagedReturnResult<T> getResult() {
        return result;
    }

    public void setResult(PagedReturnResult<T> result) {
        this.result = result;
    }
}
